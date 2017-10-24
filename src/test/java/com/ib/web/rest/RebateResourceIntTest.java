package com.ib.web.rest;

import com.ib.AyoappApp;

import com.ib.domain.Rebate;
import com.ib.repository.RebateRepository;
import com.ib.service.RebateService;
import com.ib.repository.search.RebateSearchRepository;
import com.ib.web.rest.errors.ExceptionTranslator;
import com.ib.service.dto.RebateCriteria;
import com.ib.service.RebateQueryService;

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
 * Test class for the RebateResource REST controller.
 *
 * @see RebateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AyoappApp.class)
public class RebateResourceIntTest {

    private static final Long DEFAULT_ID_TRANSAKSI = 1L;
    private static final Long UPDATED_ID_TRANSAKSI = 2L;

    private static final BigDecimal DEFAULT_JML = new BigDecimal(1);
    private static final BigDecimal UPDATED_JML = new BigDecimal(2);

    private static final BigDecimal DEFAULT_HARGA_PRODUK = new BigDecimal(1);
    private static final BigDecimal UPDATED_HARGA_PRODUK = new BigDecimal(2);

    private static final String DEFAULT_ID_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_ID_MEMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final Integer DEFAULT_BULAN = 1;
    private static final Integer UPDATED_BULAN = 2;

    private static final Integer DEFAULT_TAHUN = 1;
    private static final Integer UPDATED_TAHUN = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Autowired
    private RebateRepository rebateRepository;

    @Autowired
    private RebateService rebateService;

    @Autowired
    private RebateSearchRepository rebateSearchRepository;

    @Autowired
    private RebateQueryService rebateQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRebateMockMvc;

    private Rebate rebate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RebateResource rebateResource = new RebateResource(rebateService, rebateQueryService);
        this.restRebateMockMvc = MockMvcBuilders.standaloneSetup(rebateResource)
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
    public static Rebate createEntity(EntityManager em) {
        Rebate rebate = new Rebate()
            .idTransaksi(DEFAULT_ID_TRANSAKSI)
            .jml(DEFAULT_JML)
            .hargaProduk(DEFAULT_HARGA_PRODUK)
            .idMember(DEFAULT_ID_MEMBER)
            .level(DEFAULT_LEVEL)
            .bulan(DEFAULT_BULAN)
            .tahun(DEFAULT_TAHUN)
            .status(DEFAULT_STATUS);
        return rebate;
    }

    @Before
    public void initTest() {
        rebateSearchRepository.deleteAll();
        rebate = createEntity(em);
    }

    @Test
    @Transactional
    public void createRebate() throws Exception {
        int databaseSizeBeforeCreate = rebateRepository.findAll().size();

        // Create the Rebate
        restRebateMockMvc.perform(post("/api/rebates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rebate)))
            .andExpect(status().isCreated());

        // Validate the Rebate in the database
        List<Rebate> rebateList = rebateRepository.findAll();
        assertThat(rebateList).hasSize(databaseSizeBeforeCreate + 1);
        Rebate testRebate = rebateList.get(rebateList.size() - 1);
        assertThat(testRebate.getIdTransaksi()).isEqualTo(DEFAULT_ID_TRANSAKSI);
        assertThat(testRebate.getJml()).isEqualTo(DEFAULT_JML);
        assertThat(testRebate.getHargaProduk()).isEqualTo(DEFAULT_HARGA_PRODUK);
        assertThat(testRebate.getIdMember()).isEqualTo(DEFAULT_ID_MEMBER);
        assertThat(testRebate.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testRebate.getBulan()).isEqualTo(DEFAULT_BULAN);
        assertThat(testRebate.getTahun()).isEqualTo(DEFAULT_TAHUN);
        assertThat(testRebate.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Rebate in Elasticsearch
        Rebate rebateEs = rebateSearchRepository.findOne(testRebate.getId());
        assertThat(rebateEs).isEqualToComparingFieldByField(testRebate);
    }

    @Test
    @Transactional
    public void createRebateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rebateRepository.findAll().size();

        // Create the Rebate with an existing ID
        rebate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRebateMockMvc.perform(post("/api/rebates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rebate)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Rebate> rebateList = rebateRepository.findAll();
        assertThat(rebateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRebates() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList
        restRebateMockMvc.perform(get("/api/rebates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rebate.getId().intValue())))
            .andExpect(jsonPath("$.[*].idTransaksi").value(hasItem(DEFAULT_ID_TRANSAKSI.intValue())))
            .andExpect(jsonPath("$.[*].jml").value(hasItem(DEFAULT_JML.intValue())))
            .andExpect(jsonPath("$.[*].hargaProduk").value(hasItem(DEFAULT_HARGA_PRODUK.intValue())))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].bulan").value(hasItem(DEFAULT_BULAN)))
            .andExpect(jsonPath("$.[*].tahun").value(hasItem(DEFAULT_TAHUN)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getRebate() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get the rebate
        restRebateMockMvc.perform(get("/api/rebates/{id}", rebate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rebate.getId().intValue()))
            .andExpect(jsonPath("$.idTransaksi").value(DEFAULT_ID_TRANSAKSI.intValue()))
            .andExpect(jsonPath("$.jml").value(DEFAULT_JML.intValue()))
            .andExpect(jsonPath("$.hargaProduk").value(DEFAULT_HARGA_PRODUK.intValue()))
            .andExpect(jsonPath("$.idMember").value(DEFAULT_ID_MEMBER.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.bulan").value(DEFAULT_BULAN))
            .andExpect(jsonPath("$.tahun").value(DEFAULT_TAHUN))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getAllRebatesByIdTransaksiIsEqualToSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where idTransaksi equals to DEFAULT_ID_TRANSAKSI
        defaultRebateShouldBeFound("idTransaksi.equals=" + DEFAULT_ID_TRANSAKSI);

        // Get all the rebateList where idTransaksi equals to UPDATED_ID_TRANSAKSI
        defaultRebateShouldNotBeFound("idTransaksi.equals=" + UPDATED_ID_TRANSAKSI);
    }

    @Test
    @Transactional
    public void getAllRebatesByIdTransaksiIsInShouldWork() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where idTransaksi in DEFAULT_ID_TRANSAKSI or UPDATED_ID_TRANSAKSI
        defaultRebateShouldBeFound("idTransaksi.in=" + DEFAULT_ID_TRANSAKSI + "," + UPDATED_ID_TRANSAKSI);

        // Get all the rebateList where idTransaksi equals to UPDATED_ID_TRANSAKSI
        defaultRebateShouldNotBeFound("idTransaksi.in=" + UPDATED_ID_TRANSAKSI);
    }

    @Test
    @Transactional
    public void getAllRebatesByIdTransaksiIsNullOrNotNull() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where idTransaksi is not null
        defaultRebateShouldBeFound("idTransaksi.specified=true");

        // Get all the rebateList where idTransaksi is null
        defaultRebateShouldNotBeFound("idTransaksi.specified=false");
    }

    @Test
    @Transactional
    public void getAllRebatesByIdTransaksiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where idTransaksi greater than or equals to DEFAULT_ID_TRANSAKSI
        defaultRebateShouldBeFound("idTransaksi.greaterOrEqualThan=" + DEFAULT_ID_TRANSAKSI);

        // Get all the rebateList where idTransaksi greater than or equals to UPDATED_ID_TRANSAKSI
        defaultRebateShouldNotBeFound("idTransaksi.greaterOrEqualThan=" + UPDATED_ID_TRANSAKSI);
    }

    @Test
    @Transactional
    public void getAllRebatesByIdTransaksiIsLessThanSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where idTransaksi less than or equals to DEFAULT_ID_TRANSAKSI
        defaultRebateShouldNotBeFound("idTransaksi.lessThan=" + DEFAULT_ID_TRANSAKSI);

        // Get all the rebateList where idTransaksi less than or equals to UPDATED_ID_TRANSAKSI
        defaultRebateShouldBeFound("idTransaksi.lessThan=" + UPDATED_ID_TRANSAKSI);
    }


    @Test
    @Transactional
    public void getAllRebatesByJmlIsEqualToSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where jml equals to DEFAULT_JML
        defaultRebateShouldBeFound("jml.equals=" + DEFAULT_JML);

        // Get all the rebateList where jml equals to UPDATED_JML
        defaultRebateShouldNotBeFound("jml.equals=" + UPDATED_JML);
    }

    @Test
    @Transactional
    public void getAllRebatesByJmlIsInShouldWork() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where jml in DEFAULT_JML or UPDATED_JML
        defaultRebateShouldBeFound("jml.in=" + DEFAULT_JML + "," + UPDATED_JML);

        // Get all the rebateList where jml equals to UPDATED_JML
        defaultRebateShouldNotBeFound("jml.in=" + UPDATED_JML);
    }

    @Test
    @Transactional
    public void getAllRebatesByJmlIsNullOrNotNull() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where jml is not null
        defaultRebateShouldBeFound("jml.specified=true");

        // Get all the rebateList where jml is null
        defaultRebateShouldNotBeFound("jml.specified=false");
    }

    @Test
    @Transactional
    public void getAllRebatesByHargaProdukIsEqualToSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where hargaProduk equals to DEFAULT_HARGA_PRODUK
        defaultRebateShouldBeFound("hargaProduk.equals=" + DEFAULT_HARGA_PRODUK);

        // Get all the rebateList where hargaProduk equals to UPDATED_HARGA_PRODUK
        defaultRebateShouldNotBeFound("hargaProduk.equals=" + UPDATED_HARGA_PRODUK);
    }

    @Test
    @Transactional
    public void getAllRebatesByHargaProdukIsInShouldWork() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where hargaProduk in DEFAULT_HARGA_PRODUK or UPDATED_HARGA_PRODUK
        defaultRebateShouldBeFound("hargaProduk.in=" + DEFAULT_HARGA_PRODUK + "," + UPDATED_HARGA_PRODUK);

        // Get all the rebateList where hargaProduk equals to UPDATED_HARGA_PRODUK
        defaultRebateShouldNotBeFound("hargaProduk.in=" + UPDATED_HARGA_PRODUK);
    }

    @Test
    @Transactional
    public void getAllRebatesByHargaProdukIsNullOrNotNull() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where hargaProduk is not null
        defaultRebateShouldBeFound("hargaProduk.specified=true");

        // Get all the rebateList where hargaProduk is null
        defaultRebateShouldNotBeFound("hargaProduk.specified=false");
    }

    @Test
    @Transactional
    public void getAllRebatesByIdMemberIsEqualToSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where idMember equals to DEFAULT_ID_MEMBER
        defaultRebateShouldBeFound("idMember.equals=" + DEFAULT_ID_MEMBER);

        // Get all the rebateList where idMember equals to UPDATED_ID_MEMBER
        defaultRebateShouldNotBeFound("idMember.equals=" + UPDATED_ID_MEMBER);
    }

    @Test
    @Transactional
    public void getAllRebatesByIdMemberIsInShouldWork() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where idMember in DEFAULT_ID_MEMBER or UPDATED_ID_MEMBER
        defaultRebateShouldBeFound("idMember.in=" + DEFAULT_ID_MEMBER + "," + UPDATED_ID_MEMBER);

        // Get all the rebateList where idMember equals to UPDATED_ID_MEMBER
        defaultRebateShouldNotBeFound("idMember.in=" + UPDATED_ID_MEMBER);
    }

    @Test
    @Transactional
    public void getAllRebatesByIdMemberIsNullOrNotNull() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where idMember is not null
        defaultRebateShouldBeFound("idMember.specified=true");

        // Get all the rebateList where idMember is null
        defaultRebateShouldNotBeFound("idMember.specified=false");
    }

    @Test
    @Transactional
    public void getAllRebatesByLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where level equals to DEFAULT_LEVEL
        defaultRebateShouldBeFound("level.equals=" + DEFAULT_LEVEL);

        // Get all the rebateList where level equals to UPDATED_LEVEL
        defaultRebateShouldNotBeFound("level.equals=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllRebatesByLevelIsInShouldWork() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where level in DEFAULT_LEVEL or UPDATED_LEVEL
        defaultRebateShouldBeFound("level.in=" + DEFAULT_LEVEL + "," + UPDATED_LEVEL);

        // Get all the rebateList where level equals to UPDATED_LEVEL
        defaultRebateShouldNotBeFound("level.in=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllRebatesByLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where level is not null
        defaultRebateShouldBeFound("level.specified=true");

        // Get all the rebateList where level is null
        defaultRebateShouldNotBeFound("level.specified=false");
    }

    @Test
    @Transactional
    public void getAllRebatesByLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where level greater than or equals to DEFAULT_LEVEL
        defaultRebateShouldBeFound("level.greaterOrEqualThan=" + DEFAULT_LEVEL);

        // Get all the rebateList where level greater than or equals to UPDATED_LEVEL
        defaultRebateShouldNotBeFound("level.greaterOrEqualThan=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllRebatesByLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where level less than or equals to DEFAULT_LEVEL
        defaultRebateShouldNotBeFound("level.lessThan=" + DEFAULT_LEVEL);

        // Get all the rebateList where level less than or equals to UPDATED_LEVEL
        defaultRebateShouldBeFound("level.lessThan=" + UPDATED_LEVEL);
    }


    @Test
    @Transactional
    public void getAllRebatesByBulanIsEqualToSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where bulan equals to DEFAULT_BULAN
        defaultRebateShouldBeFound("bulan.equals=" + DEFAULT_BULAN);

        // Get all the rebateList where bulan equals to UPDATED_BULAN
        defaultRebateShouldNotBeFound("bulan.equals=" + UPDATED_BULAN);
    }

    @Test
    @Transactional
    public void getAllRebatesByBulanIsInShouldWork() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where bulan in DEFAULT_BULAN or UPDATED_BULAN
        defaultRebateShouldBeFound("bulan.in=" + DEFAULT_BULAN + "," + UPDATED_BULAN);

        // Get all the rebateList where bulan equals to UPDATED_BULAN
        defaultRebateShouldNotBeFound("bulan.in=" + UPDATED_BULAN);
    }

    @Test
    @Transactional
    public void getAllRebatesByBulanIsNullOrNotNull() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where bulan is not null
        defaultRebateShouldBeFound("bulan.specified=true");

        // Get all the rebateList where bulan is null
        defaultRebateShouldNotBeFound("bulan.specified=false");
    }

    @Test
    @Transactional
    public void getAllRebatesByBulanIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where bulan greater than or equals to DEFAULT_BULAN
        defaultRebateShouldBeFound("bulan.greaterOrEqualThan=" + DEFAULT_BULAN);

        // Get all the rebateList where bulan greater than or equals to UPDATED_BULAN
        defaultRebateShouldNotBeFound("bulan.greaterOrEqualThan=" + UPDATED_BULAN);
    }

    @Test
    @Transactional
    public void getAllRebatesByBulanIsLessThanSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where bulan less than or equals to DEFAULT_BULAN
        defaultRebateShouldNotBeFound("bulan.lessThan=" + DEFAULT_BULAN);

        // Get all the rebateList where bulan less than or equals to UPDATED_BULAN
        defaultRebateShouldBeFound("bulan.lessThan=" + UPDATED_BULAN);
    }


    @Test
    @Transactional
    public void getAllRebatesByTahunIsEqualToSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where tahun equals to DEFAULT_TAHUN
        defaultRebateShouldBeFound("tahun.equals=" + DEFAULT_TAHUN);

        // Get all the rebateList where tahun equals to UPDATED_TAHUN
        defaultRebateShouldNotBeFound("tahun.equals=" + UPDATED_TAHUN);
    }

    @Test
    @Transactional
    public void getAllRebatesByTahunIsInShouldWork() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where tahun in DEFAULT_TAHUN or UPDATED_TAHUN
        defaultRebateShouldBeFound("tahun.in=" + DEFAULT_TAHUN + "," + UPDATED_TAHUN);

        // Get all the rebateList where tahun equals to UPDATED_TAHUN
        defaultRebateShouldNotBeFound("tahun.in=" + UPDATED_TAHUN);
    }

    @Test
    @Transactional
    public void getAllRebatesByTahunIsNullOrNotNull() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where tahun is not null
        defaultRebateShouldBeFound("tahun.specified=true");

        // Get all the rebateList where tahun is null
        defaultRebateShouldNotBeFound("tahun.specified=false");
    }

    @Test
    @Transactional
    public void getAllRebatesByTahunIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where tahun greater than or equals to DEFAULT_TAHUN
        defaultRebateShouldBeFound("tahun.greaterOrEqualThan=" + DEFAULT_TAHUN);

        // Get all the rebateList where tahun greater than or equals to UPDATED_TAHUN
        defaultRebateShouldNotBeFound("tahun.greaterOrEqualThan=" + UPDATED_TAHUN);
    }

    @Test
    @Transactional
    public void getAllRebatesByTahunIsLessThanSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where tahun less than or equals to DEFAULT_TAHUN
        defaultRebateShouldNotBeFound("tahun.lessThan=" + DEFAULT_TAHUN);

        // Get all the rebateList where tahun less than or equals to UPDATED_TAHUN
        defaultRebateShouldBeFound("tahun.lessThan=" + UPDATED_TAHUN);
    }


    @Test
    @Transactional
    public void getAllRebatesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where status equals to DEFAULT_STATUS
        defaultRebateShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the rebateList where status equals to UPDATED_STATUS
        defaultRebateShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRebatesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultRebateShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the rebateList where status equals to UPDATED_STATUS
        defaultRebateShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRebatesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where status is not null
        defaultRebateShouldBeFound("status.specified=true");

        // Get all the rebateList where status is null
        defaultRebateShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllRebatesByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where status greater than or equals to DEFAULT_STATUS
        defaultRebateShouldBeFound("status.greaterOrEqualThan=" + DEFAULT_STATUS);

        // Get all the rebateList where status greater than or equals to UPDATED_STATUS
        defaultRebateShouldNotBeFound("status.greaterOrEqualThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRebatesByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        rebateRepository.saveAndFlush(rebate);

        // Get all the rebateList where status less than or equals to DEFAULT_STATUS
        defaultRebateShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the rebateList where status less than or equals to UPDATED_STATUS
        defaultRebateShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }


    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultRebateShouldBeFound(String filter) throws Exception {
        restRebateMockMvc.perform(get("/api/rebates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rebate.getId().intValue())))
            .andExpect(jsonPath("$.[*].idTransaksi").value(hasItem(DEFAULT_ID_TRANSAKSI.intValue())))
            .andExpect(jsonPath("$.[*].jml").value(hasItem(DEFAULT_JML.intValue())))
            .andExpect(jsonPath("$.[*].hargaProduk").value(hasItem(DEFAULT_HARGA_PRODUK.intValue())))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].bulan").value(hasItem(DEFAULT_BULAN)))
            .andExpect(jsonPath("$.[*].tahun").value(hasItem(DEFAULT_TAHUN)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultRebateShouldNotBeFound(String filter) throws Exception {
        restRebateMockMvc.perform(get("/api/rebates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingRebate() throws Exception {
        // Get the rebate
        restRebateMockMvc.perform(get("/api/rebates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRebate() throws Exception {
        // Initialize the database
        rebateService.save(rebate);

        int databaseSizeBeforeUpdate = rebateRepository.findAll().size();

        // Update the rebate
        Rebate updatedRebate = rebateRepository.findOne(rebate.getId());
        updatedRebate
            .idTransaksi(UPDATED_ID_TRANSAKSI)
            .jml(UPDATED_JML)
            .hargaProduk(UPDATED_HARGA_PRODUK)
            .idMember(UPDATED_ID_MEMBER)
            .level(UPDATED_LEVEL)
            .bulan(UPDATED_BULAN)
            .tahun(UPDATED_TAHUN)
            .status(UPDATED_STATUS);

        restRebateMockMvc.perform(put("/api/rebates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRebate)))
            .andExpect(status().isOk());

        // Validate the Rebate in the database
        List<Rebate> rebateList = rebateRepository.findAll();
        assertThat(rebateList).hasSize(databaseSizeBeforeUpdate);
        Rebate testRebate = rebateList.get(rebateList.size() - 1);
        assertThat(testRebate.getIdTransaksi()).isEqualTo(UPDATED_ID_TRANSAKSI);
        assertThat(testRebate.getJml()).isEqualTo(UPDATED_JML);
        assertThat(testRebate.getHargaProduk()).isEqualTo(UPDATED_HARGA_PRODUK);
        assertThat(testRebate.getIdMember()).isEqualTo(UPDATED_ID_MEMBER);
        assertThat(testRebate.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testRebate.getBulan()).isEqualTo(UPDATED_BULAN);
        assertThat(testRebate.getTahun()).isEqualTo(UPDATED_TAHUN);
        assertThat(testRebate.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Rebate in Elasticsearch
        Rebate rebateEs = rebateSearchRepository.findOne(testRebate.getId());
        assertThat(rebateEs).isEqualToComparingFieldByField(testRebate);
    }

    @Test
    @Transactional
    public void updateNonExistingRebate() throws Exception {
        int databaseSizeBeforeUpdate = rebateRepository.findAll().size();

        // Create the Rebate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRebateMockMvc.perform(put("/api/rebates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rebate)))
            .andExpect(status().isCreated());

        // Validate the Rebate in the database
        List<Rebate> rebateList = rebateRepository.findAll();
        assertThat(rebateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRebate() throws Exception {
        // Initialize the database
        rebateService.save(rebate);

        int databaseSizeBeforeDelete = rebateRepository.findAll().size();

        // Get the rebate
        restRebateMockMvc.perform(delete("/api/rebates/{id}", rebate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean rebateExistsInEs = rebateSearchRepository.exists(rebate.getId());
        assertThat(rebateExistsInEs).isFalse();

        // Validate the database is empty
        List<Rebate> rebateList = rebateRepository.findAll();
        assertThat(rebateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRebate() throws Exception {
        // Initialize the database
        rebateService.save(rebate);

        // Search the rebate
        restRebateMockMvc.perform(get("/api/_search/rebates?query=id:" + rebate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rebate.getId().intValue())))
            .andExpect(jsonPath("$.[*].idTransaksi").value(hasItem(DEFAULT_ID_TRANSAKSI.intValue())))
            .andExpect(jsonPath("$.[*].jml").value(hasItem(DEFAULT_JML.intValue())))
            .andExpect(jsonPath("$.[*].hargaProduk").value(hasItem(DEFAULT_HARGA_PRODUK.intValue())))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].bulan").value(hasItem(DEFAULT_BULAN)))
            .andExpect(jsonPath("$.[*].tahun").value(hasItem(DEFAULT_TAHUN)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rebate.class);
        Rebate rebate1 = new Rebate();
        rebate1.setId(1L);
        Rebate rebate2 = new Rebate();
        rebate2.setId(rebate1.getId());
        assertThat(rebate1).isEqualTo(rebate2);
        rebate2.setId(2L);
        assertThat(rebate1).isNotEqualTo(rebate2);
        rebate1.setId(null);
        assertThat(rebate1).isNotEqualTo(rebate2);
    }
}
