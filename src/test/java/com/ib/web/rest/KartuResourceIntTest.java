package com.ib.web.rest;

import com.ib.AyoappApp;

import com.ib.domain.Kartu;
import com.ib.repository.KartuRepository;
import com.ib.service.KartuService;
import com.ib.repository.search.KartuSearchRepository;
import com.ib.web.rest.errors.ExceptionTranslator;
import com.ib.service.dto.KartuCriteria;
import com.ib.service.KartuQueryService;

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
 * Test class for the KartuResource REST controller.
 *
 * @see KartuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AyoappApp.class)
public class KartuResourceIntTest {

    private static final Integer DEFAULT_ID_KARTU = 1;
    private static final Integer UPDATED_ID_KARTU = 2;

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_OPERATOR = 1;
    private static final Integer UPDATED_ID_OPERATOR = 2;

    private static final Integer DEFAULT_CEK_HLR = 1;
    private static final Integer UPDATED_CEK_HLR = 2;

    private static final String DEFAULT_KETKARTU = "AAAAAAAAAA";
    private static final String UPDATED_KETKARTU = "BBBBBBBBBB";

    @Autowired
    private KartuRepository kartuRepository;

    @Autowired
    private KartuService kartuService;

    @Autowired
    private KartuSearchRepository kartuSearchRepository;

    @Autowired
    private KartuQueryService kartuQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKartuMockMvc;

    private Kartu kartu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KartuResource kartuResource = new KartuResource(kartuService, kartuQueryService);
        this.restKartuMockMvc = MockMvcBuilders.standaloneSetup(kartuResource)
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
    public static Kartu createEntity(EntityManager em) {
        Kartu kartu = new Kartu()
            .idKartu(DEFAULT_ID_KARTU)
            .nama(DEFAULT_NAMA)
            .idOperator(DEFAULT_ID_OPERATOR)
            .cekHlr(DEFAULT_CEK_HLR)
            .ketkartu(DEFAULT_KETKARTU);
        return kartu;
    }

    @Before
    public void initTest() {
        kartuSearchRepository.deleteAll();
        kartu = createEntity(em);
    }

    @Test
    @Transactional
    public void createKartu() throws Exception {
        int databaseSizeBeforeCreate = kartuRepository.findAll().size();

        // Create the Kartu
        restKartuMockMvc.perform(post("/api/kartus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kartu)))
            .andExpect(status().isCreated());

        // Validate the Kartu in the database
        List<Kartu> kartuList = kartuRepository.findAll();
        assertThat(kartuList).hasSize(databaseSizeBeforeCreate + 1);
        Kartu testKartu = kartuList.get(kartuList.size() - 1);
        assertThat(testKartu.getIdKartu()).isEqualTo(DEFAULT_ID_KARTU);
        assertThat(testKartu.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testKartu.getIdOperator()).isEqualTo(DEFAULT_ID_OPERATOR);
        assertThat(testKartu.getCekHlr()).isEqualTo(DEFAULT_CEK_HLR);
        assertThat(testKartu.getKetkartu()).isEqualTo(DEFAULT_KETKARTU);

        // Validate the Kartu in Elasticsearch
        Kartu kartuEs = kartuSearchRepository.findOne(testKartu.getId());
        assertThat(kartuEs).isEqualToComparingFieldByField(testKartu);
    }

    @Test
    @Transactional
    public void createKartuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kartuRepository.findAll().size();

        // Create the Kartu with an existing ID
        kartu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKartuMockMvc.perform(post("/api/kartus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kartu)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Kartu> kartuList = kartuRepository.findAll();
        assertThat(kartuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllKartus() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList
        restKartuMockMvc.perform(get("/api/kartus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kartu.getId().intValue())))
            .andExpect(jsonPath("$.[*].idKartu").value(hasItem(DEFAULT_ID_KARTU)))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].idOperator").value(hasItem(DEFAULT_ID_OPERATOR)))
            .andExpect(jsonPath("$.[*].cekHlr").value(hasItem(DEFAULT_CEK_HLR)))
            .andExpect(jsonPath("$.[*].ketkartu").value(hasItem(DEFAULT_KETKARTU.toString())));
    }

    @Test
    @Transactional
    public void getKartu() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get the kartu
        restKartuMockMvc.perform(get("/api/kartus/{id}", kartu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kartu.getId().intValue()))
            .andExpect(jsonPath("$.idKartu").value(DEFAULT_ID_KARTU))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.idOperator").value(DEFAULT_ID_OPERATOR))
            .andExpect(jsonPath("$.cekHlr").value(DEFAULT_CEK_HLR))
            .andExpect(jsonPath("$.ketkartu").value(DEFAULT_KETKARTU.toString()));
    }

    @Test
    @Transactional
    public void getAllKartusByIdKartuIsEqualToSomething() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where idKartu equals to DEFAULT_ID_KARTU
        defaultKartuShouldBeFound("idKartu.equals=" + DEFAULT_ID_KARTU);

        // Get all the kartuList where idKartu equals to UPDATED_ID_KARTU
        defaultKartuShouldNotBeFound("idKartu.equals=" + UPDATED_ID_KARTU);
    }

    @Test
    @Transactional
    public void getAllKartusByIdKartuIsInShouldWork() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where idKartu in DEFAULT_ID_KARTU or UPDATED_ID_KARTU
        defaultKartuShouldBeFound("idKartu.in=" + DEFAULT_ID_KARTU + "," + UPDATED_ID_KARTU);

        // Get all the kartuList where idKartu equals to UPDATED_ID_KARTU
        defaultKartuShouldNotBeFound("idKartu.in=" + UPDATED_ID_KARTU);
    }

    @Test
    @Transactional
    public void getAllKartusByIdKartuIsNullOrNotNull() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where idKartu is not null
        defaultKartuShouldBeFound("idKartu.specified=true");

        // Get all the kartuList where idKartu is null
        defaultKartuShouldNotBeFound("idKartu.specified=false");
    }

    @Test
    @Transactional
    public void getAllKartusByIdKartuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where idKartu greater than or equals to DEFAULT_ID_KARTU
        defaultKartuShouldBeFound("idKartu.greaterOrEqualThan=" + DEFAULT_ID_KARTU);

        // Get all the kartuList where idKartu greater than or equals to UPDATED_ID_KARTU
        defaultKartuShouldNotBeFound("idKartu.greaterOrEqualThan=" + UPDATED_ID_KARTU);
    }

    @Test
    @Transactional
    public void getAllKartusByIdKartuIsLessThanSomething() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where idKartu less than or equals to DEFAULT_ID_KARTU
        defaultKartuShouldNotBeFound("idKartu.lessThan=" + DEFAULT_ID_KARTU);

        // Get all the kartuList where idKartu less than or equals to UPDATED_ID_KARTU
        defaultKartuShouldBeFound("idKartu.lessThan=" + UPDATED_ID_KARTU);
    }


    @Test
    @Transactional
    public void getAllKartusByNamaIsEqualToSomething() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where nama equals to DEFAULT_NAMA
        defaultKartuShouldBeFound("nama.equals=" + DEFAULT_NAMA);

        // Get all the kartuList where nama equals to UPDATED_NAMA
        defaultKartuShouldNotBeFound("nama.equals=" + UPDATED_NAMA);
    }

    @Test
    @Transactional
    public void getAllKartusByNamaIsInShouldWork() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where nama in DEFAULT_NAMA or UPDATED_NAMA
        defaultKartuShouldBeFound("nama.in=" + DEFAULT_NAMA + "," + UPDATED_NAMA);

        // Get all the kartuList where nama equals to UPDATED_NAMA
        defaultKartuShouldNotBeFound("nama.in=" + UPDATED_NAMA);
    }

    @Test
    @Transactional
    public void getAllKartusByNamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where nama is not null
        defaultKartuShouldBeFound("nama.specified=true");

        // Get all the kartuList where nama is null
        defaultKartuShouldNotBeFound("nama.specified=false");
    }

    @Test
    @Transactional
    public void getAllKartusByIdOperatorIsEqualToSomething() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where idOperator equals to DEFAULT_ID_OPERATOR
        defaultKartuShouldBeFound("idOperator.equals=" + DEFAULT_ID_OPERATOR);

        // Get all the kartuList where idOperator equals to UPDATED_ID_OPERATOR
        defaultKartuShouldNotBeFound("idOperator.equals=" + UPDATED_ID_OPERATOR);
    }

    @Test
    @Transactional
    public void getAllKartusByIdOperatorIsInShouldWork() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where idOperator in DEFAULT_ID_OPERATOR or UPDATED_ID_OPERATOR
        defaultKartuShouldBeFound("idOperator.in=" + DEFAULT_ID_OPERATOR + "," + UPDATED_ID_OPERATOR);

        // Get all the kartuList where idOperator equals to UPDATED_ID_OPERATOR
        defaultKartuShouldNotBeFound("idOperator.in=" + UPDATED_ID_OPERATOR);
    }

    @Test
    @Transactional
    public void getAllKartusByIdOperatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where idOperator is not null
        defaultKartuShouldBeFound("idOperator.specified=true");

        // Get all the kartuList where idOperator is null
        defaultKartuShouldNotBeFound("idOperator.specified=false");
    }

    @Test
    @Transactional
    public void getAllKartusByIdOperatorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where idOperator greater than or equals to DEFAULT_ID_OPERATOR
        defaultKartuShouldBeFound("idOperator.greaterOrEqualThan=" + DEFAULT_ID_OPERATOR);

        // Get all the kartuList where idOperator greater than or equals to UPDATED_ID_OPERATOR
        defaultKartuShouldNotBeFound("idOperator.greaterOrEqualThan=" + UPDATED_ID_OPERATOR);
    }

    @Test
    @Transactional
    public void getAllKartusByIdOperatorIsLessThanSomething() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where idOperator less than or equals to DEFAULT_ID_OPERATOR
        defaultKartuShouldNotBeFound("idOperator.lessThan=" + DEFAULT_ID_OPERATOR);

        // Get all the kartuList where idOperator less than or equals to UPDATED_ID_OPERATOR
        defaultKartuShouldBeFound("idOperator.lessThan=" + UPDATED_ID_OPERATOR);
    }


    @Test
    @Transactional
    public void getAllKartusByCekHlrIsEqualToSomething() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where cekHlr equals to DEFAULT_CEK_HLR
        defaultKartuShouldBeFound("cekHlr.equals=" + DEFAULT_CEK_HLR);

        // Get all the kartuList where cekHlr equals to UPDATED_CEK_HLR
        defaultKartuShouldNotBeFound("cekHlr.equals=" + UPDATED_CEK_HLR);
    }

    @Test
    @Transactional
    public void getAllKartusByCekHlrIsInShouldWork() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where cekHlr in DEFAULT_CEK_HLR or UPDATED_CEK_HLR
        defaultKartuShouldBeFound("cekHlr.in=" + DEFAULT_CEK_HLR + "," + UPDATED_CEK_HLR);

        // Get all the kartuList where cekHlr equals to UPDATED_CEK_HLR
        defaultKartuShouldNotBeFound("cekHlr.in=" + UPDATED_CEK_HLR);
    }

    @Test
    @Transactional
    public void getAllKartusByCekHlrIsNullOrNotNull() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where cekHlr is not null
        defaultKartuShouldBeFound("cekHlr.specified=true");

        // Get all the kartuList where cekHlr is null
        defaultKartuShouldNotBeFound("cekHlr.specified=false");
    }

    @Test
    @Transactional
    public void getAllKartusByCekHlrIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where cekHlr greater than or equals to DEFAULT_CEK_HLR
        defaultKartuShouldBeFound("cekHlr.greaterOrEqualThan=" + DEFAULT_CEK_HLR);

        // Get all the kartuList where cekHlr greater than or equals to UPDATED_CEK_HLR
        defaultKartuShouldNotBeFound("cekHlr.greaterOrEqualThan=" + UPDATED_CEK_HLR);
    }

    @Test
    @Transactional
    public void getAllKartusByCekHlrIsLessThanSomething() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where cekHlr less than or equals to DEFAULT_CEK_HLR
        defaultKartuShouldNotBeFound("cekHlr.lessThan=" + DEFAULT_CEK_HLR);

        // Get all the kartuList where cekHlr less than or equals to UPDATED_CEK_HLR
        defaultKartuShouldBeFound("cekHlr.lessThan=" + UPDATED_CEK_HLR);
    }


    @Test
    @Transactional
    public void getAllKartusByKetkartuIsEqualToSomething() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where ketkartu equals to DEFAULT_KETKARTU
        defaultKartuShouldBeFound("ketkartu.equals=" + DEFAULT_KETKARTU);

        // Get all the kartuList where ketkartu equals to UPDATED_KETKARTU
        defaultKartuShouldNotBeFound("ketkartu.equals=" + UPDATED_KETKARTU);
    }

    @Test
    @Transactional
    public void getAllKartusByKetkartuIsInShouldWork() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where ketkartu in DEFAULT_KETKARTU or UPDATED_KETKARTU
        defaultKartuShouldBeFound("ketkartu.in=" + DEFAULT_KETKARTU + "," + UPDATED_KETKARTU);

        // Get all the kartuList where ketkartu equals to UPDATED_KETKARTU
        defaultKartuShouldNotBeFound("ketkartu.in=" + UPDATED_KETKARTU);
    }

    @Test
    @Transactional
    public void getAllKartusByKetkartuIsNullOrNotNull() throws Exception {
        // Initialize the database
        kartuRepository.saveAndFlush(kartu);

        // Get all the kartuList where ketkartu is not null
        defaultKartuShouldBeFound("ketkartu.specified=true");

        // Get all the kartuList where ketkartu is null
        defaultKartuShouldNotBeFound("ketkartu.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultKartuShouldBeFound(String filter) throws Exception {
        restKartuMockMvc.perform(get("/api/kartus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kartu.getId().intValue())))
            .andExpect(jsonPath("$.[*].idKartu").value(hasItem(DEFAULT_ID_KARTU)))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].idOperator").value(hasItem(DEFAULT_ID_OPERATOR)))
            .andExpect(jsonPath("$.[*].cekHlr").value(hasItem(DEFAULT_CEK_HLR)))
            .andExpect(jsonPath("$.[*].ketkartu").value(hasItem(DEFAULT_KETKARTU.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultKartuShouldNotBeFound(String filter) throws Exception {
        restKartuMockMvc.perform(get("/api/kartus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingKartu() throws Exception {
        // Get the kartu
        restKartuMockMvc.perform(get("/api/kartus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKartu() throws Exception {
        // Initialize the database
        kartuService.save(kartu);

        int databaseSizeBeforeUpdate = kartuRepository.findAll().size();

        // Update the kartu
        Kartu updatedKartu = kartuRepository.findOne(kartu.getId());
        updatedKartu
            .idKartu(UPDATED_ID_KARTU)
            .nama(UPDATED_NAMA)
            .idOperator(UPDATED_ID_OPERATOR)
            .cekHlr(UPDATED_CEK_HLR)
            .ketkartu(UPDATED_KETKARTU);

        restKartuMockMvc.perform(put("/api/kartus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKartu)))
            .andExpect(status().isOk());

        // Validate the Kartu in the database
        List<Kartu> kartuList = kartuRepository.findAll();
        assertThat(kartuList).hasSize(databaseSizeBeforeUpdate);
        Kartu testKartu = kartuList.get(kartuList.size() - 1);
        assertThat(testKartu.getIdKartu()).isEqualTo(UPDATED_ID_KARTU);
        assertThat(testKartu.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testKartu.getIdOperator()).isEqualTo(UPDATED_ID_OPERATOR);
        assertThat(testKartu.getCekHlr()).isEqualTo(UPDATED_CEK_HLR);
        assertThat(testKartu.getKetkartu()).isEqualTo(UPDATED_KETKARTU);

        // Validate the Kartu in Elasticsearch
        Kartu kartuEs = kartuSearchRepository.findOne(testKartu.getId());
        assertThat(kartuEs).isEqualToComparingFieldByField(testKartu);
    }

    @Test
    @Transactional
    public void updateNonExistingKartu() throws Exception {
        int databaseSizeBeforeUpdate = kartuRepository.findAll().size();

        // Create the Kartu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKartuMockMvc.perform(put("/api/kartus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kartu)))
            .andExpect(status().isCreated());

        // Validate the Kartu in the database
        List<Kartu> kartuList = kartuRepository.findAll();
        assertThat(kartuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKartu() throws Exception {
        // Initialize the database
        kartuService.save(kartu);

        int databaseSizeBeforeDelete = kartuRepository.findAll().size();

        // Get the kartu
        restKartuMockMvc.perform(delete("/api/kartus/{id}", kartu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean kartuExistsInEs = kartuSearchRepository.exists(kartu.getId());
        assertThat(kartuExistsInEs).isFalse();

        // Validate the database is empty
        List<Kartu> kartuList = kartuRepository.findAll();
        assertThat(kartuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchKartu() throws Exception {
        // Initialize the database
        kartuService.save(kartu);

        // Search the kartu
        restKartuMockMvc.perform(get("/api/_search/kartus?query=id:" + kartu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kartu.getId().intValue())))
            .andExpect(jsonPath("$.[*].idKartu").value(hasItem(DEFAULT_ID_KARTU)))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].idOperator").value(hasItem(DEFAULT_ID_OPERATOR)))
            .andExpect(jsonPath("$.[*].cekHlr").value(hasItem(DEFAULT_CEK_HLR)))
            .andExpect(jsonPath("$.[*].ketkartu").value(hasItem(DEFAULT_KETKARTU.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kartu.class);
        Kartu kartu1 = new Kartu();
        kartu1.setId(1L);
        Kartu kartu2 = new Kartu();
        kartu2.setId(kartu1.getId());
        assertThat(kartu1).isEqualTo(kartu2);
        kartu2.setId(2L);
        assertThat(kartu1).isNotEqualTo(kartu2);
        kartu1.setId(null);
        assertThat(kartu1).isNotEqualTo(kartu2);
    }
}
