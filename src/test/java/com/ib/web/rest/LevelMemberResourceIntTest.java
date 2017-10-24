package com.ib.web.rest;

import com.ib.AyoappApp;

import com.ib.domain.LevelMember;
import com.ib.repository.LevelMemberRepository;
import com.ib.service.LevelMemberService;
import com.ib.repository.search.LevelMemberSearchRepository;
import com.ib.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LevelMemberResource REST controller.
 *
 * @see LevelMemberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AyoappApp.class)
public class LevelMemberResourceIntTest {

    private static final String DEFAULT_KODE_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_KODE_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    @Autowired
    private LevelMemberRepository levelMemberRepository;

    @Autowired
    private LevelMemberService levelMemberService;

    @Autowired
    private LevelMemberSearchRepository levelMemberSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLevelMemberMockMvc;

    private LevelMember levelMember;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LevelMemberResource levelMemberResource = new LevelMemberResource(levelMemberService);
        this.restLevelMemberMockMvc = MockMvcBuilders.standaloneSetup(levelMemberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelMember createEntity(EntityManager em) {
        LevelMember levelMember = new LevelMember()
            .kodeLevel(DEFAULT_KODE_LEVEL)
            .nama(DEFAULT_NAMA);
        return levelMember;
    }

    @Before
    public void initTest() {
        levelMemberSearchRepository.deleteAll();
        levelMember = createEntity(em);
    }

    @Test
    @Transactional
    public void createLevelMember() throws Exception {
        int databaseSizeBeforeCreate = levelMemberRepository.findAll().size();

        // Create the LevelMember
        restLevelMemberMockMvc.perform(post("/api/level-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(levelMember)))
            .andExpect(status().isCreated());

        // Validate the LevelMember in the database
        List<LevelMember> levelMemberList = levelMemberRepository.findAll();
        assertThat(levelMemberList).hasSize(databaseSizeBeforeCreate + 1);
        LevelMember testLevelMember = levelMemberList.get(levelMemberList.size() - 1);
        assertThat(testLevelMember.getKodeLevel()).isEqualTo(DEFAULT_KODE_LEVEL);
        assertThat(testLevelMember.getNama()).isEqualTo(DEFAULT_NAMA);

        // Validate the LevelMember in Elasticsearch
        LevelMember levelMemberEs = levelMemberSearchRepository.findOne(testLevelMember.getId());
        assertThat(levelMemberEs).isEqualToComparingFieldByField(testLevelMember);
    }

    @Test
    @Transactional
    public void createLevelMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = levelMemberRepository.findAll().size();

        // Create the LevelMember with an existing ID
        levelMember.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLevelMemberMockMvc.perform(post("/api/level-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(levelMember)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LevelMember> levelMemberList = levelMemberRepository.findAll();
        assertThat(levelMemberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLevelMembers() throws Exception {
        // Initialize the database
        levelMemberRepository.saveAndFlush(levelMember);

        // Get all the levelMemberList
        restLevelMemberMockMvc.perform(get("/api/level-members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(levelMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].kodeLevel").value(hasItem(DEFAULT_KODE_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())));
    }

    @Test
    @Transactional
    public void getLevelMember() throws Exception {
        // Initialize the database
        levelMemberRepository.saveAndFlush(levelMember);

        // Get the levelMember
        restLevelMemberMockMvc.perform(get("/api/level-members/{id}", levelMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(levelMember.getId().intValue()))
            .andExpect(jsonPath("$.kodeLevel").value(DEFAULT_KODE_LEVEL.toString()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLevelMember() throws Exception {
        // Get the levelMember
        restLevelMemberMockMvc.perform(get("/api/level-members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLevelMember() throws Exception {
        // Initialize the database
        levelMemberService.save(levelMember);

        int databaseSizeBeforeUpdate = levelMemberRepository.findAll().size();

        // Update the levelMember
        LevelMember updatedLevelMember = levelMemberRepository.findOne(levelMember.getId());
        updatedLevelMember
            .kodeLevel(UPDATED_KODE_LEVEL)
            .nama(UPDATED_NAMA);

        restLevelMemberMockMvc.perform(put("/api/level-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLevelMember)))
            .andExpect(status().isOk());

        // Validate the LevelMember in the database
        List<LevelMember> levelMemberList = levelMemberRepository.findAll();
        assertThat(levelMemberList).hasSize(databaseSizeBeforeUpdate);
        LevelMember testLevelMember = levelMemberList.get(levelMemberList.size() - 1);
        assertThat(testLevelMember.getKodeLevel()).isEqualTo(UPDATED_KODE_LEVEL);
        assertThat(testLevelMember.getNama()).isEqualTo(UPDATED_NAMA);

        // Validate the LevelMember in Elasticsearch
        LevelMember levelMemberEs = levelMemberSearchRepository.findOne(testLevelMember.getId());
        assertThat(levelMemberEs).isEqualToComparingFieldByField(testLevelMember);
    }

    @Test
    @Transactional
    public void updateNonExistingLevelMember() throws Exception {
        int databaseSizeBeforeUpdate = levelMemberRepository.findAll().size();

        // Create the LevelMember

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLevelMemberMockMvc.perform(put("/api/level-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(levelMember)))
            .andExpect(status().isCreated());

        // Validate the LevelMember in the database
        List<LevelMember> levelMemberList = levelMemberRepository.findAll();
        assertThat(levelMemberList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLevelMember() throws Exception {
        // Initialize the database
        levelMemberService.save(levelMember);

        int databaseSizeBeforeDelete = levelMemberRepository.findAll().size();

        // Get the levelMember
        restLevelMemberMockMvc.perform(delete("/api/level-members/{id}", levelMember.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean levelMemberExistsInEs = levelMemberSearchRepository.exists(levelMember.getId());
        assertThat(levelMemberExistsInEs).isFalse();

        // Validate the database is empty
        List<LevelMember> levelMemberList = levelMemberRepository.findAll();
        assertThat(levelMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLevelMember() throws Exception {
        // Initialize the database
        levelMemberService.save(levelMember);

        // Search the levelMember
        restLevelMemberMockMvc.perform(get("/api/_search/level-members?query=id:" + levelMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(levelMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].kodeLevel").value(hasItem(DEFAULT_KODE_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LevelMember.class);
        LevelMember levelMember1 = new LevelMember();
        levelMember1.setId(1L);
        LevelMember levelMember2 = new LevelMember();
        levelMember2.setId(levelMember1.getId());
        assertThat(levelMember1).isEqualTo(levelMember2);
        levelMember2.setId(2L);
        assertThat(levelMember1).isNotEqualTo(levelMember2);
        levelMember1.setId(null);
        assertThat(levelMember1).isNotEqualTo(levelMember2);
    }
}
