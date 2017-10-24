package com.ib.web.rest;

import com.ib.AyoappApp;

import com.ib.domain.Denom;
import com.ib.repository.DenomRepository;
import com.ib.service.DenomService;
import com.ib.repository.search.DenomSearchRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DenomResource REST controller.
 *
 * @see DenomResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AyoappApp.class)
public class DenomResourceIntTest {

    private static final BigDecimal DEFAULT_JML_DENOM = new BigDecimal(0);
    private static final BigDecimal UPDATED_JML_DENOM = new BigDecimal(1);

    @Autowired
    private DenomRepository denomRepository;

    @Autowired
    private DenomService denomService;

    @Autowired
    private DenomSearchRepository denomSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDenomMockMvc;

    private Denom denom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DenomResource denomResource = new DenomResource(denomService);
        this.restDenomMockMvc = MockMvcBuilders.standaloneSetup(denomResource)
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
    public static Denom createEntity(EntityManager em) {
        Denom denom = new Denom()
            .jmlDenom(DEFAULT_JML_DENOM);
        return denom;
    }

    @Before
    public void initTest() {
        denomSearchRepository.deleteAll();
        denom = createEntity(em);
    }

    @Test
    @Transactional
    public void createDenom() throws Exception {
        int databaseSizeBeforeCreate = denomRepository.findAll().size();

        // Create the Denom
        restDenomMockMvc.perform(post("/api/denoms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(denom)))
            .andExpect(status().isCreated());

        // Validate the Denom in the database
        List<Denom> denomList = denomRepository.findAll();
        assertThat(denomList).hasSize(databaseSizeBeforeCreate + 1);
        Denom testDenom = denomList.get(denomList.size() - 1);
        assertThat(testDenom.getJmlDenom()).isEqualTo(DEFAULT_JML_DENOM);

        // Validate the Denom in Elasticsearch
        Denom denomEs = denomSearchRepository.findOne(testDenom.getId());
        assertThat(denomEs).isEqualToComparingFieldByField(testDenom);
    }

    @Test
    @Transactional
    public void createDenomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = denomRepository.findAll().size();

        // Create the Denom with an existing ID
        denom.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDenomMockMvc.perform(post("/api/denoms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(denom)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Denom> denomList = denomRepository.findAll();
        assertThat(denomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkJmlDenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = denomRepository.findAll().size();
        // set the field null
        denom.setJmlDenom(null);

        // Create the Denom, which fails.

        restDenomMockMvc.perform(post("/api/denoms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(denom)))
            .andExpect(status().isBadRequest());

        List<Denom> denomList = denomRepository.findAll();
        assertThat(denomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDenoms() throws Exception {
        // Initialize the database
        denomRepository.saveAndFlush(denom);

        // Get all the denomList
        restDenomMockMvc.perform(get("/api/denoms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(denom.getId().intValue())))
            .andExpect(jsonPath("$.[*].jmlDenom").value(hasItem(DEFAULT_JML_DENOM.intValue())));
    }

    @Test
    @Transactional
    public void getDenom() throws Exception {
        // Initialize the database
        denomRepository.saveAndFlush(denom);

        // Get the denom
        restDenomMockMvc.perform(get("/api/denoms/{id}", denom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(denom.getId().intValue()))
            .andExpect(jsonPath("$.jmlDenom").value(DEFAULT_JML_DENOM.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDenom() throws Exception {
        // Get the denom
        restDenomMockMvc.perform(get("/api/denoms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDenom() throws Exception {
        // Initialize the database
        denomService.save(denom);

        int databaseSizeBeforeUpdate = denomRepository.findAll().size();

        // Update the denom
        Denom updatedDenom = denomRepository.findOne(denom.getId());
        updatedDenom
            .jmlDenom(UPDATED_JML_DENOM);

        restDenomMockMvc.perform(put("/api/denoms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDenom)))
            .andExpect(status().isOk());

        // Validate the Denom in the database
        List<Denom> denomList = denomRepository.findAll();
        assertThat(denomList).hasSize(databaseSizeBeforeUpdate);
        Denom testDenom = denomList.get(denomList.size() - 1);
        assertThat(testDenom.getJmlDenom()).isEqualTo(UPDATED_JML_DENOM);

        // Validate the Denom in Elasticsearch
        Denom denomEs = denomSearchRepository.findOne(testDenom.getId());
        assertThat(denomEs).isEqualToComparingFieldByField(testDenom);
    }

    @Test
    @Transactional
    public void updateNonExistingDenom() throws Exception {
        int databaseSizeBeforeUpdate = denomRepository.findAll().size();

        // Create the Denom

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDenomMockMvc.perform(put("/api/denoms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(denom)))
            .andExpect(status().isCreated());

        // Validate the Denom in the database
        List<Denom> denomList = denomRepository.findAll();
        assertThat(denomList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDenom() throws Exception {
        // Initialize the database
        denomService.save(denom);

        int databaseSizeBeforeDelete = denomRepository.findAll().size();

        // Get the denom
        restDenomMockMvc.perform(delete("/api/denoms/{id}", denom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean denomExistsInEs = denomSearchRepository.exists(denom.getId());
        assertThat(denomExistsInEs).isFalse();

        // Validate the database is empty
        List<Denom> denomList = denomRepository.findAll();
        assertThat(denomList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDenom() throws Exception {
        // Initialize the database
        denomService.save(denom);

        // Search the denom
        restDenomMockMvc.perform(get("/api/_search/denoms?query=id:" + denom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(denom.getId().intValue())))
            .andExpect(jsonPath("$.[*].jmlDenom").value(hasItem(DEFAULT_JML_DENOM.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Denom.class);
        Denom denom1 = new Denom();
        denom1.setId(1L);
        Denom denom2 = new Denom();
        denom2.setId(denom1.getId());
        assertThat(denom1).isEqualTo(denom2);
        denom2.setId(2L);
        assertThat(denom1).isNotEqualTo(denom2);
        denom1.setId(null);
        assertThat(denom1).isNotEqualTo(denom2);
    }
}
