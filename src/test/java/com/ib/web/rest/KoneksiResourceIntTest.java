package com.ib.web.rest;

import com.ib.AyoappApp;

import com.ib.domain.Koneksi;
import com.ib.repository.KoneksiRepository;
import com.ib.service.KoneksiService;
import com.ib.repository.search.KoneksiSearchRepository;
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
 * Test class for the KoneksiResource REST controller.
 *
 * @see KoneksiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AyoappApp.class)
public class KoneksiResourceIntTest {

    private static final Integer DEFAULT_METODE = 1;
    private static final Integer UPDATED_METODE = 2;

    private static final String DEFAULT_KET = "AAAAAAAAAA";
    private static final String UPDATED_KET = "BBBBBBBBBB";

    @Autowired
    private KoneksiRepository koneksiRepository;

    @Autowired
    private KoneksiService koneksiService;

    @Autowired
    private KoneksiSearchRepository koneksiSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKoneksiMockMvc;

    private Koneksi koneksi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KoneksiResource koneksiResource = new KoneksiResource(koneksiService);
        this.restKoneksiMockMvc = MockMvcBuilders.standaloneSetup(koneksiResource)
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
    public static Koneksi createEntity(EntityManager em) {
        Koneksi koneksi = new Koneksi()
            .metode(DEFAULT_METODE)
            .ket(DEFAULT_KET);
        return koneksi;
    }

    @Before
    public void initTest() {
        koneksiSearchRepository.deleteAll();
        koneksi = createEntity(em);
    }

    @Test
    @Transactional
    public void createKoneksi() throws Exception {
        int databaseSizeBeforeCreate = koneksiRepository.findAll().size();

        // Create the Koneksi
        restKoneksiMockMvc.perform(post("/api/koneksis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(koneksi)))
            .andExpect(status().isCreated());

        // Validate the Koneksi in the database
        List<Koneksi> koneksiList = koneksiRepository.findAll();
        assertThat(koneksiList).hasSize(databaseSizeBeforeCreate + 1);
        Koneksi testKoneksi = koneksiList.get(koneksiList.size() - 1);
        assertThat(testKoneksi.getMetode()).isEqualTo(DEFAULT_METODE);
        assertThat(testKoneksi.getKet()).isEqualTo(DEFAULT_KET);

        // Validate the Koneksi in Elasticsearch
        Koneksi koneksiEs = koneksiSearchRepository.findOne(testKoneksi.getId());
        assertThat(koneksiEs).isEqualToComparingFieldByField(testKoneksi);
    }

    @Test
    @Transactional
    public void createKoneksiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = koneksiRepository.findAll().size();

        // Create the Koneksi with an existing ID
        koneksi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKoneksiMockMvc.perform(post("/api/koneksis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(koneksi)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Koneksi> koneksiList = koneksiRepository.findAll();
        assertThat(koneksiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllKoneksis() throws Exception {
        // Initialize the database
        koneksiRepository.saveAndFlush(koneksi);

        // Get all the koneksiList
        restKoneksiMockMvc.perform(get("/api/koneksis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(koneksi.getId().intValue())))
            .andExpect(jsonPath("$.[*].metode").value(hasItem(DEFAULT_METODE)))
            .andExpect(jsonPath("$.[*].ket").value(hasItem(DEFAULT_KET.toString())));
    }

    @Test
    @Transactional
    public void getKoneksi() throws Exception {
        // Initialize the database
        koneksiRepository.saveAndFlush(koneksi);

        // Get the koneksi
        restKoneksiMockMvc.perform(get("/api/koneksis/{id}", koneksi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(koneksi.getId().intValue()))
            .andExpect(jsonPath("$.metode").value(DEFAULT_METODE))
            .andExpect(jsonPath("$.ket").value(DEFAULT_KET.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKoneksi() throws Exception {
        // Get the koneksi
        restKoneksiMockMvc.perform(get("/api/koneksis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKoneksi() throws Exception {
        // Initialize the database
        koneksiService.save(koneksi);

        int databaseSizeBeforeUpdate = koneksiRepository.findAll().size();

        // Update the koneksi
        Koneksi updatedKoneksi = koneksiRepository.findOne(koneksi.getId());
        updatedKoneksi
            .metode(UPDATED_METODE)
            .ket(UPDATED_KET);

        restKoneksiMockMvc.perform(put("/api/koneksis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKoneksi)))
            .andExpect(status().isOk());

        // Validate the Koneksi in the database
        List<Koneksi> koneksiList = koneksiRepository.findAll();
        assertThat(koneksiList).hasSize(databaseSizeBeforeUpdate);
        Koneksi testKoneksi = koneksiList.get(koneksiList.size() - 1);
        assertThat(testKoneksi.getMetode()).isEqualTo(UPDATED_METODE);
        assertThat(testKoneksi.getKet()).isEqualTo(UPDATED_KET);

        // Validate the Koneksi in Elasticsearch
        Koneksi koneksiEs = koneksiSearchRepository.findOne(testKoneksi.getId());
        assertThat(koneksiEs).isEqualToComparingFieldByField(testKoneksi);
    }

    @Test
    @Transactional
    public void updateNonExistingKoneksi() throws Exception {
        int databaseSizeBeforeUpdate = koneksiRepository.findAll().size();

        // Create the Koneksi

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKoneksiMockMvc.perform(put("/api/koneksis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(koneksi)))
            .andExpect(status().isCreated());

        // Validate the Koneksi in the database
        List<Koneksi> koneksiList = koneksiRepository.findAll();
        assertThat(koneksiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKoneksi() throws Exception {
        // Initialize the database
        koneksiService.save(koneksi);

        int databaseSizeBeforeDelete = koneksiRepository.findAll().size();

        // Get the koneksi
        restKoneksiMockMvc.perform(delete("/api/koneksis/{id}", koneksi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean koneksiExistsInEs = koneksiSearchRepository.exists(koneksi.getId());
        assertThat(koneksiExistsInEs).isFalse();

        // Validate the database is empty
        List<Koneksi> koneksiList = koneksiRepository.findAll();
        assertThat(koneksiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchKoneksi() throws Exception {
        // Initialize the database
        koneksiService.save(koneksi);

        // Search the koneksi
        restKoneksiMockMvc.perform(get("/api/_search/koneksis?query=id:" + koneksi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(koneksi.getId().intValue())))
            .andExpect(jsonPath("$.[*].metode").value(hasItem(DEFAULT_METODE)))
            .andExpect(jsonPath("$.[*].ket").value(hasItem(DEFAULT_KET.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Koneksi.class);
        Koneksi koneksi1 = new Koneksi();
        koneksi1.setId(1L);
        Koneksi koneksi2 = new Koneksi();
        koneksi2.setId(koneksi1.getId());
        assertThat(koneksi1).isEqualTo(koneksi2);
        koneksi2.setId(2L);
        assertThat(koneksi1).isNotEqualTo(koneksi2);
        koneksi1.setId(null);
        assertThat(koneksi1).isNotEqualTo(koneksi2);
    }
}
