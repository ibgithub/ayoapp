package com.ib.web.rest;

import com.ib.AyoappApp;

import com.ib.domain.Harga;
import com.ib.repository.HargaRepository;
import com.ib.service.HargaService;
import com.ib.repository.search.HargaSearchRepository;
import com.ib.web.rest.errors.ExceptionTranslator;
import com.ib.service.dto.HargaCriteria;
import com.ib.service.HargaQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.ib.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HargaResource REST controller.
 *
 * @see HargaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AyoappApp.class)
public class HargaResourceIntTest {

    private static final Integer DEFAULT_ID_HARGA = 1;
    private static final Integer UPDATED_ID_HARGA = 2;

    private static final Integer DEFAULT_ID_PRODUK = 1;
    private static final Integer UPDATED_ID_PRODUK = 2;

    private static final String DEFAULT_ID_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_ID_MEMBER = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_HARGA_JUAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_HARGA_JUAL = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_TGL_INPUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TGL_INPUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_USER_INPUT = "AAAAAAAAAA";
    private static final String UPDATED_USER_INPUT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_HARGA_BEFORE = new BigDecimal(1);
    private static final BigDecimal UPDATED_HARGA_BEFORE = new BigDecimal(2);

    @Autowired
    private HargaRepository hargaRepository;

    @Autowired
    private HargaService hargaService;

    @Autowired
    private HargaSearchRepository hargaSearchRepository;

    @Autowired
    private HargaQueryService hargaQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHargaMockMvc;

    private Harga harga;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HargaResource hargaResource = new HargaResource(hargaService, hargaQueryService);
        this.restHargaMockMvc = MockMvcBuilders.standaloneSetup(hargaResource)
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
    public static Harga createEntity(EntityManager em) {
        Harga harga = new Harga()
            .idHarga(DEFAULT_ID_HARGA)
            .idProduk(DEFAULT_ID_PRODUK)
            .idMember(DEFAULT_ID_MEMBER)
            .hargaJual(DEFAULT_HARGA_JUAL)
            .tglInput(DEFAULT_TGL_INPUT)
            .userInput(DEFAULT_USER_INPUT)
            .hargaBefore(DEFAULT_HARGA_BEFORE);
        return harga;
    }

    @Before
    public void initTest() {
        hargaSearchRepository.deleteAll();
        harga = createEntity(em);
    }

    @Test
    @Transactional
    public void createHarga() throws Exception {
        int databaseSizeBeforeCreate = hargaRepository.findAll().size();

        // Create the Harga
        restHargaMockMvc.perform(post("/api/hargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(harga)))
            .andExpect(status().isCreated());

        // Validate the Harga in the database
        List<Harga> hargaList = hargaRepository.findAll();
        assertThat(hargaList).hasSize(databaseSizeBeforeCreate + 1);
        Harga testHarga = hargaList.get(hargaList.size() - 1);
        assertThat(testHarga.getIdHarga()).isEqualTo(DEFAULT_ID_HARGA);
        assertThat(testHarga.getIdProduk()).isEqualTo(DEFAULT_ID_PRODUK);
        assertThat(testHarga.getIdMember()).isEqualTo(DEFAULT_ID_MEMBER);
        assertThat(testHarga.getHargaJual()).isEqualTo(DEFAULT_HARGA_JUAL);
        assertThat(testHarga.getTglInput()).isEqualTo(DEFAULT_TGL_INPUT);
        assertThat(testHarga.getUserInput()).isEqualTo(DEFAULT_USER_INPUT);
        assertThat(testHarga.getHargaBefore()).isEqualTo(DEFAULT_HARGA_BEFORE);

        // Validate the Harga in Elasticsearch
        Harga hargaEs = hargaSearchRepository.findOne(testHarga.getId());
        assertThat(hargaEs).isEqualToComparingFieldByField(testHarga);
    }

    @Test
    @Transactional
    public void createHargaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hargaRepository.findAll().size();

        // Create the Harga with an existing ID
        harga.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHargaMockMvc.perform(post("/api/hargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(harga)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Harga> hargaList = hargaRepository.findAll();
        assertThat(hargaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHargas() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList
        restHargaMockMvc.perform(get("/api/hargas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(harga.getId().intValue())))
            .andExpect(jsonPath("$.[*].idHarga").value(hasItem(DEFAULT_ID_HARGA)))
            .andExpect(jsonPath("$.[*].idProduk").value(hasItem(DEFAULT_ID_PRODUK)))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].hargaJual").value(hasItem(DEFAULT_HARGA_JUAL.intValue())))
            .andExpect(jsonPath("$.[*].tglInput").value(hasItem(sameInstant(DEFAULT_TGL_INPUT))))
            .andExpect(jsonPath("$.[*].userInput").value(hasItem(DEFAULT_USER_INPUT.toString())))
            .andExpect(jsonPath("$.[*].hargaBefore").value(hasItem(DEFAULT_HARGA_BEFORE.intValue())));
    }

    @Test
    @Transactional
    public void getHarga() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get the harga
        restHargaMockMvc.perform(get("/api/hargas/{id}", harga.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(harga.getId().intValue()))
            .andExpect(jsonPath("$.idHarga").value(DEFAULT_ID_HARGA))
            .andExpect(jsonPath("$.idProduk").value(DEFAULT_ID_PRODUK))
            .andExpect(jsonPath("$.idMember").value(DEFAULT_ID_MEMBER.toString()))
            .andExpect(jsonPath("$.hargaJual").value(DEFAULT_HARGA_JUAL.intValue()))
            .andExpect(jsonPath("$.tglInput").value(sameInstant(DEFAULT_TGL_INPUT)))
            .andExpect(jsonPath("$.userInput").value(DEFAULT_USER_INPUT.toString()))
            .andExpect(jsonPath("$.hargaBefore").value(DEFAULT_HARGA_BEFORE.intValue()));
    }

    @Test
    @Transactional
    public void getAllHargasByIdHargaIsEqualToSomething() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where idHarga equals to DEFAULT_ID_HARGA
        defaultHargaShouldBeFound("idHarga.equals=" + DEFAULT_ID_HARGA);

        // Get all the hargaList where idHarga equals to UPDATED_ID_HARGA
        defaultHargaShouldNotBeFound("idHarga.equals=" + UPDATED_ID_HARGA);
    }

    @Test
    @Transactional
    public void getAllHargasByIdHargaIsInShouldWork() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where idHarga in DEFAULT_ID_HARGA or UPDATED_ID_HARGA
        defaultHargaShouldBeFound("idHarga.in=" + DEFAULT_ID_HARGA + "," + UPDATED_ID_HARGA);

        // Get all the hargaList where idHarga equals to UPDATED_ID_HARGA
        defaultHargaShouldNotBeFound("idHarga.in=" + UPDATED_ID_HARGA);
    }

    @Test
    @Transactional
    public void getAllHargasByIdHargaIsNullOrNotNull() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where idHarga is not null
        defaultHargaShouldBeFound("idHarga.specified=true");

        // Get all the hargaList where idHarga is null
        defaultHargaShouldNotBeFound("idHarga.specified=false");
    }

    @Test
    @Transactional
    public void getAllHargasByIdHargaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where idHarga greater than or equals to DEFAULT_ID_HARGA
        defaultHargaShouldBeFound("idHarga.greaterOrEqualThan=" + DEFAULT_ID_HARGA);

        // Get all the hargaList where idHarga greater than or equals to UPDATED_ID_HARGA
        defaultHargaShouldNotBeFound("idHarga.greaterOrEqualThan=" + UPDATED_ID_HARGA);
    }

    @Test
    @Transactional
    public void getAllHargasByIdHargaIsLessThanSomething() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where idHarga less than or equals to DEFAULT_ID_HARGA
        defaultHargaShouldNotBeFound("idHarga.lessThan=" + DEFAULT_ID_HARGA);

        // Get all the hargaList where idHarga less than or equals to UPDATED_ID_HARGA
        defaultHargaShouldBeFound("idHarga.lessThan=" + UPDATED_ID_HARGA);
    }


    @Test
    @Transactional
    public void getAllHargasByIdProdukIsEqualToSomething() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where idProduk equals to DEFAULT_ID_PRODUK
        defaultHargaShouldBeFound("idProduk.equals=" + DEFAULT_ID_PRODUK);

        // Get all the hargaList where idProduk equals to UPDATED_ID_PRODUK
        defaultHargaShouldNotBeFound("idProduk.equals=" + UPDATED_ID_PRODUK);
    }

    @Test
    @Transactional
    public void getAllHargasByIdProdukIsInShouldWork() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where idProduk in DEFAULT_ID_PRODUK or UPDATED_ID_PRODUK
        defaultHargaShouldBeFound("idProduk.in=" + DEFAULT_ID_PRODUK + "," + UPDATED_ID_PRODUK);

        // Get all the hargaList where idProduk equals to UPDATED_ID_PRODUK
        defaultHargaShouldNotBeFound("idProduk.in=" + UPDATED_ID_PRODUK);
    }

    @Test
    @Transactional
    public void getAllHargasByIdProdukIsNullOrNotNull() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where idProduk is not null
        defaultHargaShouldBeFound("idProduk.specified=true");

        // Get all the hargaList where idProduk is null
        defaultHargaShouldNotBeFound("idProduk.specified=false");
    }

    @Test
    @Transactional
    public void getAllHargasByIdProdukIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where idProduk greater than or equals to DEFAULT_ID_PRODUK
        defaultHargaShouldBeFound("idProduk.greaterOrEqualThan=" + DEFAULT_ID_PRODUK);

        // Get all the hargaList where idProduk greater than or equals to UPDATED_ID_PRODUK
        defaultHargaShouldNotBeFound("idProduk.greaterOrEqualThan=" + UPDATED_ID_PRODUK);
    }

    @Test
    @Transactional
    public void getAllHargasByIdProdukIsLessThanSomething() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where idProduk less than or equals to DEFAULT_ID_PRODUK
        defaultHargaShouldNotBeFound("idProduk.lessThan=" + DEFAULT_ID_PRODUK);

        // Get all the hargaList where idProduk less than or equals to UPDATED_ID_PRODUK
        defaultHargaShouldBeFound("idProduk.lessThan=" + UPDATED_ID_PRODUK);
    }


    @Test
    @Transactional
    public void getAllHargasByIdMemberIsEqualToSomething() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where idMember equals to DEFAULT_ID_MEMBER
        defaultHargaShouldBeFound("idMember.equals=" + DEFAULT_ID_MEMBER);

        // Get all the hargaList where idMember equals to UPDATED_ID_MEMBER
        defaultHargaShouldNotBeFound("idMember.equals=" + UPDATED_ID_MEMBER);
    }

    @Test
    @Transactional
    public void getAllHargasByIdMemberIsInShouldWork() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where idMember in DEFAULT_ID_MEMBER or UPDATED_ID_MEMBER
        defaultHargaShouldBeFound("idMember.in=" + DEFAULT_ID_MEMBER + "," + UPDATED_ID_MEMBER);

        // Get all the hargaList where idMember equals to UPDATED_ID_MEMBER
        defaultHargaShouldNotBeFound("idMember.in=" + UPDATED_ID_MEMBER);
    }

    @Test
    @Transactional
    public void getAllHargasByIdMemberIsNullOrNotNull() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where idMember is not null
        defaultHargaShouldBeFound("idMember.specified=true");

        // Get all the hargaList where idMember is null
        defaultHargaShouldNotBeFound("idMember.specified=false");
    }

    @Test
    @Transactional
    public void getAllHargasByHargaJualIsEqualToSomething() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where hargaJual equals to DEFAULT_HARGA_JUAL
        defaultHargaShouldBeFound("hargaJual.equals=" + DEFAULT_HARGA_JUAL);

        // Get all the hargaList where hargaJual equals to UPDATED_HARGA_JUAL
        defaultHargaShouldNotBeFound("hargaJual.equals=" + UPDATED_HARGA_JUAL);
    }

    @Test
    @Transactional
    public void getAllHargasByHargaJualIsInShouldWork() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where hargaJual in DEFAULT_HARGA_JUAL or UPDATED_HARGA_JUAL
        defaultHargaShouldBeFound("hargaJual.in=" + DEFAULT_HARGA_JUAL + "," + UPDATED_HARGA_JUAL);

        // Get all the hargaList where hargaJual equals to UPDATED_HARGA_JUAL
        defaultHargaShouldNotBeFound("hargaJual.in=" + UPDATED_HARGA_JUAL);
    }

    @Test
    @Transactional
    public void getAllHargasByHargaJualIsNullOrNotNull() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where hargaJual is not null
        defaultHargaShouldBeFound("hargaJual.specified=true");

        // Get all the hargaList where hargaJual is null
        defaultHargaShouldNotBeFound("hargaJual.specified=false");
    }

    @Test
    @Transactional
    public void getAllHargasByTglInputIsEqualToSomething() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where tglInput equals to DEFAULT_TGL_INPUT
        defaultHargaShouldBeFound("tglInput.equals=" + DEFAULT_TGL_INPUT);

        // Get all the hargaList where tglInput equals to UPDATED_TGL_INPUT
        defaultHargaShouldNotBeFound("tglInput.equals=" + UPDATED_TGL_INPUT);
    }

    @Test
    @Transactional
    public void getAllHargasByTglInputIsInShouldWork() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where tglInput in DEFAULT_TGL_INPUT or UPDATED_TGL_INPUT
        defaultHargaShouldBeFound("tglInput.in=" + DEFAULT_TGL_INPUT + "," + UPDATED_TGL_INPUT);

        // Get all the hargaList where tglInput equals to UPDATED_TGL_INPUT
        defaultHargaShouldNotBeFound("tglInput.in=" + UPDATED_TGL_INPUT);
    }

    @Test
    @Transactional
    public void getAllHargasByTglInputIsNullOrNotNull() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where tglInput is not null
        defaultHargaShouldBeFound("tglInput.specified=true");

        // Get all the hargaList where tglInput is null
        defaultHargaShouldNotBeFound("tglInput.specified=false");
    }

    @Test
    @Transactional
    public void getAllHargasByTglInputIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where tglInput greater than or equals to DEFAULT_TGL_INPUT
        defaultHargaShouldBeFound("tglInput.greaterOrEqualThan=" + DEFAULT_TGL_INPUT);

        // Get all the hargaList where tglInput greater than or equals to UPDATED_TGL_INPUT
        defaultHargaShouldNotBeFound("tglInput.greaterOrEqualThan=" + UPDATED_TGL_INPUT);
    }

    @Test
    @Transactional
    public void getAllHargasByTglInputIsLessThanSomething() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where tglInput less than or equals to DEFAULT_TGL_INPUT
        defaultHargaShouldNotBeFound("tglInput.lessThan=" + DEFAULT_TGL_INPUT);

        // Get all the hargaList where tglInput less than or equals to UPDATED_TGL_INPUT
        defaultHargaShouldBeFound("tglInput.lessThan=" + UPDATED_TGL_INPUT);
    }


    @Test
    @Transactional
    public void getAllHargasByUserInputIsEqualToSomething() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where userInput equals to DEFAULT_USER_INPUT
        defaultHargaShouldBeFound("userInput.equals=" + DEFAULT_USER_INPUT);

        // Get all the hargaList where userInput equals to UPDATED_USER_INPUT
        defaultHargaShouldNotBeFound("userInput.equals=" + UPDATED_USER_INPUT);
    }

    @Test
    @Transactional
    public void getAllHargasByUserInputIsInShouldWork() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where userInput in DEFAULT_USER_INPUT or UPDATED_USER_INPUT
        defaultHargaShouldBeFound("userInput.in=" + DEFAULT_USER_INPUT + "," + UPDATED_USER_INPUT);

        // Get all the hargaList where userInput equals to UPDATED_USER_INPUT
        defaultHargaShouldNotBeFound("userInput.in=" + UPDATED_USER_INPUT);
    }

    @Test
    @Transactional
    public void getAllHargasByUserInputIsNullOrNotNull() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where userInput is not null
        defaultHargaShouldBeFound("userInput.specified=true");

        // Get all the hargaList where userInput is null
        defaultHargaShouldNotBeFound("userInput.specified=false");
    }

    @Test
    @Transactional
    public void getAllHargasByHargaBeforeIsEqualToSomething() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where hargaBefore equals to DEFAULT_HARGA_BEFORE
        defaultHargaShouldBeFound("hargaBefore.equals=" + DEFAULT_HARGA_BEFORE);

        // Get all the hargaList where hargaBefore equals to UPDATED_HARGA_BEFORE
        defaultHargaShouldNotBeFound("hargaBefore.equals=" + UPDATED_HARGA_BEFORE);
    }

    @Test
    @Transactional
    public void getAllHargasByHargaBeforeIsInShouldWork() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where hargaBefore in DEFAULT_HARGA_BEFORE or UPDATED_HARGA_BEFORE
        defaultHargaShouldBeFound("hargaBefore.in=" + DEFAULT_HARGA_BEFORE + "," + UPDATED_HARGA_BEFORE);

        // Get all the hargaList where hargaBefore equals to UPDATED_HARGA_BEFORE
        defaultHargaShouldNotBeFound("hargaBefore.in=" + UPDATED_HARGA_BEFORE);
    }

    @Test
    @Transactional
    public void getAllHargasByHargaBeforeIsNullOrNotNull() throws Exception {
        // Initialize the database
        hargaRepository.saveAndFlush(harga);

        // Get all the hargaList where hargaBefore is not null
        defaultHargaShouldBeFound("hargaBefore.specified=true");

        // Get all the hargaList where hargaBefore is null
        defaultHargaShouldNotBeFound("hargaBefore.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultHargaShouldBeFound(String filter) throws Exception {
        restHargaMockMvc.perform(get("/api/hargas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(harga.getId().intValue())))
            .andExpect(jsonPath("$.[*].idHarga").value(hasItem(DEFAULT_ID_HARGA)))
            .andExpect(jsonPath("$.[*].idProduk").value(hasItem(DEFAULT_ID_PRODUK)))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].hargaJual").value(hasItem(DEFAULT_HARGA_JUAL.intValue())))
            .andExpect(jsonPath("$.[*].tglInput").value(hasItem(sameInstant(DEFAULT_TGL_INPUT))))
            .andExpect(jsonPath("$.[*].userInput").value(hasItem(DEFAULT_USER_INPUT.toString())))
            .andExpect(jsonPath("$.[*].hargaBefore").value(hasItem(DEFAULT_HARGA_BEFORE.intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultHargaShouldNotBeFound(String filter) throws Exception {
        restHargaMockMvc.perform(get("/api/hargas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingHarga() throws Exception {
        // Get the harga
        restHargaMockMvc.perform(get("/api/hargas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHarga() throws Exception {
        // Initialize the database
        hargaService.save(harga);

        int databaseSizeBeforeUpdate = hargaRepository.findAll().size();

        // Update the harga
        Harga updatedHarga = hargaRepository.findOne(harga.getId());
        updatedHarga
            .idHarga(UPDATED_ID_HARGA)
            .idProduk(UPDATED_ID_PRODUK)
            .idMember(UPDATED_ID_MEMBER)
            .hargaJual(UPDATED_HARGA_JUAL)
            .tglInput(UPDATED_TGL_INPUT)
            .userInput(UPDATED_USER_INPUT)
            .hargaBefore(UPDATED_HARGA_BEFORE);

        restHargaMockMvc.perform(put("/api/hargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHarga)))
            .andExpect(status().isOk());

        // Validate the Harga in the database
        List<Harga> hargaList = hargaRepository.findAll();
        assertThat(hargaList).hasSize(databaseSizeBeforeUpdate);
        Harga testHarga = hargaList.get(hargaList.size() - 1);
        assertThat(testHarga.getIdHarga()).isEqualTo(UPDATED_ID_HARGA);
        assertThat(testHarga.getIdProduk()).isEqualTo(UPDATED_ID_PRODUK);
        assertThat(testHarga.getIdMember()).isEqualTo(UPDATED_ID_MEMBER);
        assertThat(testHarga.getHargaJual()).isEqualTo(UPDATED_HARGA_JUAL);
        assertThat(testHarga.getTglInput()).isEqualTo(UPDATED_TGL_INPUT);
        assertThat(testHarga.getUserInput()).isEqualTo(UPDATED_USER_INPUT);
        assertThat(testHarga.getHargaBefore()).isEqualTo(UPDATED_HARGA_BEFORE);

        // Validate the Harga in Elasticsearch
        Harga hargaEs = hargaSearchRepository.findOne(testHarga.getId());
        assertThat(hargaEs).isEqualToComparingFieldByField(testHarga);
    }

    @Test
    @Transactional
    public void updateNonExistingHarga() throws Exception {
        int databaseSizeBeforeUpdate = hargaRepository.findAll().size();

        // Create the Harga

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHargaMockMvc.perform(put("/api/hargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(harga)))
            .andExpect(status().isCreated());

        // Validate the Harga in the database
        List<Harga> hargaList = hargaRepository.findAll();
        assertThat(hargaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHarga() throws Exception {
        // Initialize the database
        hargaService.save(harga);

        int databaseSizeBeforeDelete = hargaRepository.findAll().size();

        // Get the harga
        restHargaMockMvc.perform(delete("/api/hargas/{id}", harga.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hargaExistsInEs = hargaSearchRepository.exists(harga.getId());
        assertThat(hargaExistsInEs).isFalse();

        // Validate the database is empty
        List<Harga> hargaList = hargaRepository.findAll();
        assertThat(hargaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHarga() throws Exception {
        // Initialize the database
        hargaService.save(harga);

        // Search the harga
        restHargaMockMvc.perform(get("/api/_search/hargas?query=id:" + harga.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(harga.getId().intValue())))
            .andExpect(jsonPath("$.[*].idHarga").value(hasItem(DEFAULT_ID_HARGA)))
            .andExpect(jsonPath("$.[*].idProduk").value(hasItem(DEFAULT_ID_PRODUK)))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].hargaJual").value(hasItem(DEFAULT_HARGA_JUAL.intValue())))
            .andExpect(jsonPath("$.[*].tglInput").value(hasItem(sameInstant(DEFAULT_TGL_INPUT))))
            .andExpect(jsonPath("$.[*].userInput").value(hasItem(DEFAULT_USER_INPUT.toString())))
            .andExpect(jsonPath("$.[*].hargaBefore").value(hasItem(DEFAULT_HARGA_BEFORE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Harga.class);
        Harga harga1 = new Harga();
        harga1.setId(1L);
        Harga harga2 = new Harga();
        harga2.setId(harga1.getId());
        assertThat(harga1).isEqualTo(harga2);
        harga2.setId(2L);
        assertThat(harga1).isNotEqualTo(harga2);
        harga1.setId(null);
        assertThat(harga1).isNotEqualTo(harga2);
    }
}
