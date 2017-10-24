package com.ib.web.rest;

import com.ib.AyoappApp;

import com.ib.domain.MemberHp;
import com.ib.repository.MemberHpRepository;
import com.ib.service.MemberHpService;
import com.ib.repository.search.MemberHpSearchRepository;
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
 * Test class for the MemberHpResource REST controller.
 *
 * @see MemberHpResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AyoappApp.class)
public class MemberHpResourceIntTest {

    private static final String DEFAULT_ID_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_ID_MEMBER = "BBBBBBBBBB";

    private static final String DEFAULT_HP = "AAAAAAAAAA";
    private static final String UPDATED_HP = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPEIM = 1;
    private static final Integer UPDATED_TYPEIM = 2;

    private static final Integer DEFAULT_ISTRX = 1;
    private static final Integer UPDATED_ISTRX = 2;

    private static final String DEFAULT_CRYPT = "AAAAAAAAAA";
    private static final String UPDATED_CRYPT = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPEMSG = 1;
    private static final Integer UPDATED_TYPEMSG = 2;

    @Autowired
    private MemberHpRepository memberHpRepository;

    @Autowired
    private MemberHpService memberHpService;

    @Autowired
    private MemberHpSearchRepository memberHpSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMemberHpMockMvc;

    private MemberHp memberHp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MemberHpResource memberHpResource = new MemberHpResource(memberHpService);
        this.restMemberHpMockMvc = MockMvcBuilders.standaloneSetup(memberHpResource)
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
    public static MemberHp createEntity(EntityManager em) {
        MemberHp memberHp = new MemberHp()
            .idMember(DEFAULT_ID_MEMBER)
            .hp(DEFAULT_HP)
            .typeim(DEFAULT_TYPEIM)
            .istrx(DEFAULT_ISTRX)
            .crypt(DEFAULT_CRYPT)
            .typemsg(DEFAULT_TYPEMSG);
        return memberHp;
    }

    @Before
    public void initTest() {
        memberHpSearchRepository.deleteAll();
        memberHp = createEntity(em);
    }

    @Test
    @Transactional
    public void createMemberHp() throws Exception {
        int databaseSizeBeforeCreate = memberHpRepository.findAll().size();

        // Create the MemberHp
        restMemberHpMockMvc.perform(post("/api/member-hps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberHp)))
            .andExpect(status().isCreated());

        // Validate the MemberHp in the database
        List<MemberHp> memberHpList = memberHpRepository.findAll();
        assertThat(memberHpList).hasSize(databaseSizeBeforeCreate + 1);
        MemberHp testMemberHp = memberHpList.get(memberHpList.size() - 1);
        assertThat(testMemberHp.getIdMember()).isEqualTo(DEFAULT_ID_MEMBER);
        assertThat(testMemberHp.getHp()).isEqualTo(DEFAULT_HP);
        assertThat(testMemberHp.getTypeim()).isEqualTo(DEFAULT_TYPEIM);
        assertThat(testMemberHp.getIstrx()).isEqualTo(DEFAULT_ISTRX);
        assertThat(testMemberHp.getCrypt()).isEqualTo(DEFAULT_CRYPT);
        assertThat(testMemberHp.getTypemsg()).isEqualTo(DEFAULT_TYPEMSG);

        // Validate the MemberHp in Elasticsearch
        MemberHp memberHpEs = memberHpSearchRepository.findOne(testMemberHp.getId());
        assertThat(memberHpEs).isEqualToComparingFieldByField(testMemberHp);
    }

    @Test
    @Transactional
    public void createMemberHpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memberHpRepository.findAll().size();

        // Create the MemberHp with an existing ID
        memberHp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberHpMockMvc.perform(post("/api/member-hps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberHp)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MemberHp> memberHpList = memberHpRepository.findAll();
        assertThat(memberHpList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdMemberIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberHpRepository.findAll().size();
        // set the field null
        memberHp.setIdMember(null);

        // Create the MemberHp, which fails.

        restMemberHpMockMvc.perform(post("/api/member-hps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberHp)))
            .andExpect(status().isBadRequest());

        List<MemberHp> memberHpList = memberHpRepository.findAll();
        assertThat(memberHpList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHpIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberHpRepository.findAll().size();
        // set the field null
        memberHp.setHp(null);

        // Create the MemberHp, which fails.

        restMemberHpMockMvc.perform(post("/api/member-hps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberHp)))
            .andExpect(status().isBadRequest());

        List<MemberHp> memberHpList = memberHpRepository.findAll();
        assertThat(memberHpList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMemberHps() throws Exception {
        // Initialize the database
        memberHpRepository.saveAndFlush(memberHp);

        // Get all the memberHpList
        restMemberHpMockMvc.perform(get("/api/member-hps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberHp.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].hp").value(hasItem(DEFAULT_HP.toString())))
            .andExpect(jsonPath("$.[*].typeim").value(hasItem(DEFAULT_TYPEIM)))
            .andExpect(jsonPath("$.[*].istrx").value(hasItem(DEFAULT_ISTRX)))
            .andExpect(jsonPath("$.[*].crypt").value(hasItem(DEFAULT_CRYPT.toString())))
            .andExpect(jsonPath("$.[*].typemsg").value(hasItem(DEFAULT_TYPEMSG)));
    }

    @Test
    @Transactional
    public void getMemberHp() throws Exception {
        // Initialize the database
        memberHpRepository.saveAndFlush(memberHp);

        // Get the memberHp
        restMemberHpMockMvc.perform(get("/api/member-hps/{id}", memberHp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(memberHp.getId().intValue()))
            .andExpect(jsonPath("$.idMember").value(DEFAULT_ID_MEMBER.toString()))
            .andExpect(jsonPath("$.hp").value(DEFAULT_HP.toString()))
            .andExpect(jsonPath("$.typeim").value(DEFAULT_TYPEIM))
            .andExpect(jsonPath("$.istrx").value(DEFAULT_ISTRX))
            .andExpect(jsonPath("$.crypt").value(DEFAULT_CRYPT.toString()))
            .andExpect(jsonPath("$.typemsg").value(DEFAULT_TYPEMSG));
    }

    @Test
    @Transactional
    public void getNonExistingMemberHp() throws Exception {
        // Get the memberHp
        restMemberHpMockMvc.perform(get("/api/member-hps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMemberHp() throws Exception {
        // Initialize the database
        memberHpService.save(memberHp);

        int databaseSizeBeforeUpdate = memberHpRepository.findAll().size();

        // Update the memberHp
        MemberHp updatedMemberHp = memberHpRepository.findOne(memberHp.getId());
        updatedMemberHp
            .idMember(UPDATED_ID_MEMBER)
            .hp(UPDATED_HP)
            .typeim(UPDATED_TYPEIM)
            .istrx(UPDATED_ISTRX)
            .crypt(UPDATED_CRYPT)
            .typemsg(UPDATED_TYPEMSG);

        restMemberHpMockMvc.perform(put("/api/member-hps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMemberHp)))
            .andExpect(status().isOk());

        // Validate the MemberHp in the database
        List<MemberHp> memberHpList = memberHpRepository.findAll();
        assertThat(memberHpList).hasSize(databaseSizeBeforeUpdate);
        MemberHp testMemberHp = memberHpList.get(memberHpList.size() - 1);
        assertThat(testMemberHp.getIdMember()).isEqualTo(UPDATED_ID_MEMBER);
        assertThat(testMemberHp.getHp()).isEqualTo(UPDATED_HP);
        assertThat(testMemberHp.getTypeim()).isEqualTo(UPDATED_TYPEIM);
        assertThat(testMemberHp.getIstrx()).isEqualTo(UPDATED_ISTRX);
        assertThat(testMemberHp.getCrypt()).isEqualTo(UPDATED_CRYPT);
        assertThat(testMemberHp.getTypemsg()).isEqualTo(UPDATED_TYPEMSG);

        // Validate the MemberHp in Elasticsearch
        MemberHp memberHpEs = memberHpSearchRepository.findOne(testMemberHp.getId());
        assertThat(memberHpEs).isEqualToComparingFieldByField(testMemberHp);
    }

    @Test
    @Transactional
    public void updateNonExistingMemberHp() throws Exception {
        int databaseSizeBeforeUpdate = memberHpRepository.findAll().size();

        // Create the MemberHp

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMemberHpMockMvc.perform(put("/api/member-hps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberHp)))
            .andExpect(status().isCreated());

        // Validate the MemberHp in the database
        List<MemberHp> memberHpList = memberHpRepository.findAll();
        assertThat(memberHpList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMemberHp() throws Exception {
        // Initialize the database
        memberHpService.save(memberHp);

        int databaseSizeBeforeDelete = memberHpRepository.findAll().size();

        // Get the memberHp
        restMemberHpMockMvc.perform(delete("/api/member-hps/{id}", memberHp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean memberHpExistsInEs = memberHpSearchRepository.exists(memberHp.getId());
        assertThat(memberHpExistsInEs).isFalse();

        // Validate the database is empty
        List<MemberHp> memberHpList = memberHpRepository.findAll();
        assertThat(memberHpList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMemberHp() throws Exception {
        // Initialize the database
        memberHpService.save(memberHp);

        // Search the memberHp
        restMemberHpMockMvc.perform(get("/api/_search/member-hps?query=id:" + memberHp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberHp.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].hp").value(hasItem(DEFAULT_HP.toString())))
            .andExpect(jsonPath("$.[*].typeim").value(hasItem(DEFAULT_TYPEIM)))
            .andExpect(jsonPath("$.[*].istrx").value(hasItem(DEFAULT_ISTRX)))
            .andExpect(jsonPath("$.[*].crypt").value(hasItem(DEFAULT_CRYPT.toString())))
            .andExpect(jsonPath("$.[*].typemsg").value(hasItem(DEFAULT_TYPEMSG)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberHp.class);
        MemberHp memberHp1 = new MemberHp();
        memberHp1.setId(1L);
        MemberHp memberHp2 = new MemberHp();
        memberHp2.setId(memberHp1.getId());
        assertThat(memberHp1).isEqualTo(memberHp2);
        memberHp2.setId(2L);
        assertThat(memberHp1).isNotEqualTo(memberHp2);
        memberHp1.setId(null);
        assertThat(memberHp1).isNotEqualTo(memberHp2);
    }
}
