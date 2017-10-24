package com.ib.web.rest;

import com.ib.AyoappApp;

import com.ib.domain.Transaksi;
import com.ib.repository.TransaksiRepository;
import com.ib.service.TransaksiService;
import com.ib.repository.search.TransaksiSearchRepository;
import com.ib.web.rest.errors.ExceptionTranslator;
import com.ib.service.dto.TransaksiCriteria;
import com.ib.service.TransaksiQueryService;

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
 * Test class for the TransaksiResource REST controller.
 *
 * @see TransaksiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AyoappApp.class)
public class TransaksiResourceIntTest {

    private static final ZonedDateTime DEFAULT_TGL_TRANSAKSI = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TGL_TRANSAKSI = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ID_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_ID_MEMBER = "BBBBBBBBBB";

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_JML = new BigDecimal(1);
    private static final BigDecimal UPDATED_JML = new BigDecimal(2);

    private static final Integer DEFAULT_KODE_TRX = 1;
    private static final Integer UPDATED_KODE_TRX = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final BigDecimal DEFAULT_SALDO_AWAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALDO_AWAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SALDO_AKHIR = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALDO_AKHIR = new BigDecimal(2);

    private static final String DEFAULT_KET = "AAAAAAAAAA";
    private static final String UPDATED_KET = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TGL_INPUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TGL_INPUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_USER_INPUT = "AAAAAAAAAA";
    private static final String UPDATED_USER_INPUT = "BBBBBBBBBB";

    private static final Integer DEFAULT_ISSTOK = 1;
    private static final Integer UPDATED_ISSTOK = 2;

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private TransaksiService transaksiService;

    @Autowired
    private TransaksiSearchRepository transaksiSearchRepository;

    @Autowired
    private TransaksiQueryService transaksiQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransaksiMockMvc;

    private Transaksi transaksi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransaksiResource transaksiResource = new TransaksiResource(transaksiService, transaksiQueryService);
        this.restTransaksiMockMvc = MockMvcBuilders.standaloneSetup(transaksiResource)
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
    public static Transaksi createEntity(EntityManager em) {
        Transaksi transaksi = new Transaksi()
            .tglTransaksi(DEFAULT_TGL_TRANSAKSI)
            .idMember(DEFAULT_ID_MEMBER)
            .nama(DEFAULT_NAMA)
            .jml(DEFAULT_JML)
            .kodeTrx(DEFAULT_KODE_TRX)
            .status(DEFAULT_STATUS)
            .saldoAwal(DEFAULT_SALDO_AWAL)
            .saldoAkhir(DEFAULT_SALDO_AKHIR)
            .ket(DEFAULT_KET)
            .tglInput(DEFAULT_TGL_INPUT)
            .userInput(DEFAULT_USER_INPUT)
            .isstok(DEFAULT_ISSTOK);
        return transaksi;
    }

    @Before
    public void initTest() {
        transaksiSearchRepository.deleteAll();
        transaksi = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransaksi() throws Exception {
        int databaseSizeBeforeCreate = transaksiRepository.findAll().size();

        // Create the Transaksi
        restTransaksiMockMvc.perform(post("/api/transaksis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaksi)))
            .andExpect(status().isCreated());

        // Validate the Transaksi in the database
        List<Transaksi> transaksiList = transaksiRepository.findAll();
        assertThat(transaksiList).hasSize(databaseSizeBeforeCreate + 1);
        Transaksi testTransaksi = transaksiList.get(transaksiList.size() - 1);
        assertThat(testTransaksi.getTglTransaksi()).isEqualTo(DEFAULT_TGL_TRANSAKSI);
        assertThat(testTransaksi.getIdMember()).isEqualTo(DEFAULT_ID_MEMBER);
        assertThat(testTransaksi.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testTransaksi.getJml()).isEqualTo(DEFAULT_JML);
        assertThat(testTransaksi.getKodeTrx()).isEqualTo(DEFAULT_KODE_TRX);
        assertThat(testTransaksi.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTransaksi.getSaldoAwal()).isEqualTo(DEFAULT_SALDO_AWAL);
        assertThat(testTransaksi.getSaldoAkhir()).isEqualTo(DEFAULT_SALDO_AKHIR);
        assertThat(testTransaksi.getKet()).isEqualTo(DEFAULT_KET);
        assertThat(testTransaksi.getTglInput()).isEqualTo(DEFAULT_TGL_INPUT);
        assertThat(testTransaksi.getUserInput()).isEqualTo(DEFAULT_USER_INPUT);
        assertThat(testTransaksi.getIsstok()).isEqualTo(DEFAULT_ISSTOK);

        // Validate the Transaksi in Elasticsearch
        Transaksi transaksiEs = transaksiSearchRepository.findOne(testTransaksi.getId());
        assertThat(transaksiEs).isEqualToComparingFieldByField(testTransaksi);
    }

    @Test
    @Transactional
    public void createTransaksiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transaksiRepository.findAll().size();

        // Create the Transaksi with an existing ID
        transaksi.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransaksiMockMvc.perform(post("/api/transaksis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaksi)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Transaksi> transaksiList = transaksiRepository.findAll();
        assertThat(transaksiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTransaksis() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList
        restTransaksiMockMvc.perform(get("/api/transaksis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaksi.getId().intValue())))
            .andExpect(jsonPath("$.[*].tglTransaksi").value(hasItem(sameInstant(DEFAULT_TGL_TRANSAKSI))))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].jml").value(hasItem(DEFAULT_JML.intValue())))
            .andExpect(jsonPath("$.[*].kodeTrx").value(hasItem(DEFAULT_KODE_TRX)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].saldoAwal").value(hasItem(DEFAULT_SALDO_AWAL.intValue())))
            .andExpect(jsonPath("$.[*].saldoAkhir").value(hasItem(DEFAULT_SALDO_AKHIR.intValue())))
            .andExpect(jsonPath("$.[*].ket").value(hasItem(DEFAULT_KET.toString())))
            .andExpect(jsonPath("$.[*].tglInput").value(hasItem(sameInstant(DEFAULT_TGL_INPUT))))
            .andExpect(jsonPath("$.[*].userInput").value(hasItem(DEFAULT_USER_INPUT.toString())))
            .andExpect(jsonPath("$.[*].isstok").value(hasItem(DEFAULT_ISSTOK)));
    }

    @Test
    @Transactional
    public void getTransaksi() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get the transaksi
        restTransaksiMockMvc.perform(get("/api/transaksis/{id}", transaksi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transaksi.getId().intValue()))
            .andExpect(jsonPath("$.tglTransaksi").value(sameInstant(DEFAULT_TGL_TRANSAKSI)))
            .andExpect(jsonPath("$.idMember").value(DEFAULT_ID_MEMBER.toString()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.jml").value(DEFAULT_JML.intValue()))
            .andExpect(jsonPath("$.kodeTrx").value(DEFAULT_KODE_TRX))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.saldoAwal").value(DEFAULT_SALDO_AWAL.intValue()))
            .andExpect(jsonPath("$.saldoAkhir").value(DEFAULT_SALDO_AKHIR.intValue()))
            .andExpect(jsonPath("$.ket").value(DEFAULT_KET.toString()))
            .andExpect(jsonPath("$.tglInput").value(sameInstant(DEFAULT_TGL_INPUT)))
            .andExpect(jsonPath("$.userInput").value(DEFAULT_USER_INPUT.toString()))
            .andExpect(jsonPath("$.isstok").value(DEFAULT_ISSTOK));
    }

    @Test
    @Transactional
    public void getAllTransaksisByTglTransaksiIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where tglTransaksi equals to DEFAULT_TGL_TRANSAKSI
        defaultTransaksiShouldBeFound("tglTransaksi.equals=" + DEFAULT_TGL_TRANSAKSI);

        // Get all the transaksiList where tglTransaksi equals to UPDATED_TGL_TRANSAKSI
        defaultTransaksiShouldNotBeFound("tglTransaksi.equals=" + UPDATED_TGL_TRANSAKSI);
    }

    @Test
    @Transactional
    public void getAllTransaksisByTglTransaksiIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where tglTransaksi in DEFAULT_TGL_TRANSAKSI or UPDATED_TGL_TRANSAKSI
        defaultTransaksiShouldBeFound("tglTransaksi.in=" + DEFAULT_TGL_TRANSAKSI + "," + UPDATED_TGL_TRANSAKSI);

        // Get all the transaksiList where tglTransaksi equals to UPDATED_TGL_TRANSAKSI
        defaultTransaksiShouldNotBeFound("tglTransaksi.in=" + UPDATED_TGL_TRANSAKSI);
    }

    @Test
    @Transactional
    public void getAllTransaksisByTglTransaksiIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where tglTransaksi is not null
        defaultTransaksiShouldBeFound("tglTransaksi.specified=true");

        // Get all the transaksiList where tglTransaksi is null
        defaultTransaksiShouldNotBeFound("tglTransaksi.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksisByTglTransaksiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where tglTransaksi greater than or equals to DEFAULT_TGL_TRANSAKSI
        defaultTransaksiShouldBeFound("tglTransaksi.greaterOrEqualThan=" + DEFAULT_TGL_TRANSAKSI);

        // Get all the transaksiList where tglTransaksi greater than or equals to UPDATED_TGL_TRANSAKSI
        defaultTransaksiShouldNotBeFound("tglTransaksi.greaterOrEqualThan=" + UPDATED_TGL_TRANSAKSI);
    }

    @Test
    @Transactional
    public void getAllTransaksisByTglTransaksiIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where tglTransaksi less than or equals to DEFAULT_TGL_TRANSAKSI
        defaultTransaksiShouldNotBeFound("tglTransaksi.lessThan=" + DEFAULT_TGL_TRANSAKSI);

        // Get all the transaksiList where tglTransaksi less than or equals to UPDATED_TGL_TRANSAKSI
        defaultTransaksiShouldBeFound("tglTransaksi.lessThan=" + UPDATED_TGL_TRANSAKSI);
    }


    @Test
    @Transactional
    public void getAllTransaksisByIdMemberIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where idMember equals to DEFAULT_ID_MEMBER
        defaultTransaksiShouldBeFound("idMember.equals=" + DEFAULT_ID_MEMBER);

        // Get all the transaksiList where idMember equals to UPDATED_ID_MEMBER
        defaultTransaksiShouldNotBeFound("idMember.equals=" + UPDATED_ID_MEMBER);
    }

    @Test
    @Transactional
    public void getAllTransaksisByIdMemberIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where idMember in DEFAULT_ID_MEMBER or UPDATED_ID_MEMBER
        defaultTransaksiShouldBeFound("idMember.in=" + DEFAULT_ID_MEMBER + "," + UPDATED_ID_MEMBER);

        // Get all the transaksiList where idMember equals to UPDATED_ID_MEMBER
        defaultTransaksiShouldNotBeFound("idMember.in=" + UPDATED_ID_MEMBER);
    }

    @Test
    @Transactional
    public void getAllTransaksisByIdMemberIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where idMember is not null
        defaultTransaksiShouldBeFound("idMember.specified=true");

        // Get all the transaksiList where idMember is null
        defaultTransaksiShouldNotBeFound("idMember.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksisByNamaIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where nama equals to DEFAULT_NAMA
        defaultTransaksiShouldBeFound("nama.equals=" + DEFAULT_NAMA);

        // Get all the transaksiList where nama equals to UPDATED_NAMA
        defaultTransaksiShouldNotBeFound("nama.equals=" + UPDATED_NAMA);
    }

    @Test
    @Transactional
    public void getAllTransaksisByNamaIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where nama in DEFAULT_NAMA or UPDATED_NAMA
        defaultTransaksiShouldBeFound("nama.in=" + DEFAULT_NAMA + "," + UPDATED_NAMA);

        // Get all the transaksiList where nama equals to UPDATED_NAMA
        defaultTransaksiShouldNotBeFound("nama.in=" + UPDATED_NAMA);
    }

    @Test
    @Transactional
    public void getAllTransaksisByNamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where nama is not null
        defaultTransaksiShouldBeFound("nama.specified=true");

        // Get all the transaksiList where nama is null
        defaultTransaksiShouldNotBeFound("nama.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksisByJmlIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where jml equals to DEFAULT_JML
        defaultTransaksiShouldBeFound("jml.equals=" + DEFAULT_JML);

        // Get all the transaksiList where jml equals to UPDATED_JML
        defaultTransaksiShouldNotBeFound("jml.equals=" + UPDATED_JML);
    }

    @Test
    @Transactional
    public void getAllTransaksisByJmlIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where jml in DEFAULT_JML or UPDATED_JML
        defaultTransaksiShouldBeFound("jml.in=" + DEFAULT_JML + "," + UPDATED_JML);

        // Get all the transaksiList where jml equals to UPDATED_JML
        defaultTransaksiShouldNotBeFound("jml.in=" + UPDATED_JML);
    }

    @Test
    @Transactional
    public void getAllTransaksisByJmlIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where jml is not null
        defaultTransaksiShouldBeFound("jml.specified=true");

        // Get all the transaksiList where jml is null
        defaultTransaksiShouldNotBeFound("jml.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksisByKodeTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where kodeTrx equals to DEFAULT_KODE_TRX
        defaultTransaksiShouldBeFound("kodeTrx.equals=" + DEFAULT_KODE_TRX);

        // Get all the transaksiList where kodeTrx equals to UPDATED_KODE_TRX
        defaultTransaksiShouldNotBeFound("kodeTrx.equals=" + UPDATED_KODE_TRX);
    }

    @Test
    @Transactional
    public void getAllTransaksisByKodeTrxIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where kodeTrx in DEFAULT_KODE_TRX or UPDATED_KODE_TRX
        defaultTransaksiShouldBeFound("kodeTrx.in=" + DEFAULT_KODE_TRX + "," + UPDATED_KODE_TRX);

        // Get all the transaksiList where kodeTrx equals to UPDATED_KODE_TRX
        defaultTransaksiShouldNotBeFound("kodeTrx.in=" + UPDATED_KODE_TRX);
    }

    @Test
    @Transactional
    public void getAllTransaksisByKodeTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where kodeTrx is not null
        defaultTransaksiShouldBeFound("kodeTrx.specified=true");

        // Get all the transaksiList where kodeTrx is null
        defaultTransaksiShouldNotBeFound("kodeTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksisByKodeTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where kodeTrx greater than or equals to DEFAULT_KODE_TRX
        defaultTransaksiShouldBeFound("kodeTrx.greaterOrEqualThan=" + DEFAULT_KODE_TRX);

        // Get all the transaksiList where kodeTrx greater than or equals to UPDATED_KODE_TRX
        defaultTransaksiShouldNotBeFound("kodeTrx.greaterOrEqualThan=" + UPDATED_KODE_TRX);
    }

    @Test
    @Transactional
    public void getAllTransaksisByKodeTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where kodeTrx less than or equals to DEFAULT_KODE_TRX
        defaultTransaksiShouldNotBeFound("kodeTrx.lessThan=" + DEFAULT_KODE_TRX);

        // Get all the transaksiList where kodeTrx less than or equals to UPDATED_KODE_TRX
        defaultTransaksiShouldBeFound("kodeTrx.lessThan=" + UPDATED_KODE_TRX);
    }


    @Test
    @Transactional
    public void getAllTransaksisByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where status equals to DEFAULT_STATUS
        defaultTransaksiShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the transaksiList where status equals to UPDATED_STATUS
        defaultTransaksiShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTransaksisByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTransaksiShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the transaksiList where status equals to UPDATED_STATUS
        defaultTransaksiShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTransaksisByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where status is not null
        defaultTransaksiShouldBeFound("status.specified=true");

        // Get all the transaksiList where status is null
        defaultTransaksiShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksisByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where status greater than or equals to DEFAULT_STATUS
        defaultTransaksiShouldBeFound("status.greaterOrEqualThan=" + DEFAULT_STATUS);

        // Get all the transaksiList where status greater than or equals to UPDATED_STATUS
        defaultTransaksiShouldNotBeFound("status.greaterOrEqualThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTransaksisByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where status less than or equals to DEFAULT_STATUS
        defaultTransaksiShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the transaksiList where status less than or equals to UPDATED_STATUS
        defaultTransaksiShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllTransaksisBySaldoAwalIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where saldoAwal equals to DEFAULT_SALDO_AWAL
        defaultTransaksiShouldBeFound("saldoAwal.equals=" + DEFAULT_SALDO_AWAL);

        // Get all the transaksiList where saldoAwal equals to UPDATED_SALDO_AWAL
        defaultTransaksiShouldNotBeFound("saldoAwal.equals=" + UPDATED_SALDO_AWAL);
    }

    @Test
    @Transactional
    public void getAllTransaksisBySaldoAwalIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where saldoAwal in DEFAULT_SALDO_AWAL or UPDATED_SALDO_AWAL
        defaultTransaksiShouldBeFound("saldoAwal.in=" + DEFAULT_SALDO_AWAL + "," + UPDATED_SALDO_AWAL);

        // Get all the transaksiList where saldoAwal equals to UPDATED_SALDO_AWAL
        defaultTransaksiShouldNotBeFound("saldoAwal.in=" + UPDATED_SALDO_AWAL);
    }

    @Test
    @Transactional
    public void getAllTransaksisBySaldoAwalIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where saldoAwal is not null
        defaultTransaksiShouldBeFound("saldoAwal.specified=true");

        // Get all the transaksiList where saldoAwal is null
        defaultTransaksiShouldNotBeFound("saldoAwal.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksisBySaldoAkhirIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where saldoAkhir equals to DEFAULT_SALDO_AKHIR
        defaultTransaksiShouldBeFound("saldoAkhir.equals=" + DEFAULT_SALDO_AKHIR);

        // Get all the transaksiList where saldoAkhir equals to UPDATED_SALDO_AKHIR
        defaultTransaksiShouldNotBeFound("saldoAkhir.equals=" + UPDATED_SALDO_AKHIR);
    }

    @Test
    @Transactional
    public void getAllTransaksisBySaldoAkhirIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where saldoAkhir in DEFAULT_SALDO_AKHIR or UPDATED_SALDO_AKHIR
        defaultTransaksiShouldBeFound("saldoAkhir.in=" + DEFAULT_SALDO_AKHIR + "," + UPDATED_SALDO_AKHIR);

        // Get all the transaksiList where saldoAkhir equals to UPDATED_SALDO_AKHIR
        defaultTransaksiShouldNotBeFound("saldoAkhir.in=" + UPDATED_SALDO_AKHIR);
    }

    @Test
    @Transactional
    public void getAllTransaksisBySaldoAkhirIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where saldoAkhir is not null
        defaultTransaksiShouldBeFound("saldoAkhir.specified=true");

        // Get all the transaksiList where saldoAkhir is null
        defaultTransaksiShouldNotBeFound("saldoAkhir.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksisByKetIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where ket equals to DEFAULT_KET
        defaultTransaksiShouldBeFound("ket.equals=" + DEFAULT_KET);

        // Get all the transaksiList where ket equals to UPDATED_KET
        defaultTransaksiShouldNotBeFound("ket.equals=" + UPDATED_KET);
    }

    @Test
    @Transactional
    public void getAllTransaksisByKetIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where ket in DEFAULT_KET or UPDATED_KET
        defaultTransaksiShouldBeFound("ket.in=" + DEFAULT_KET + "," + UPDATED_KET);

        // Get all the transaksiList where ket equals to UPDATED_KET
        defaultTransaksiShouldNotBeFound("ket.in=" + UPDATED_KET);
    }

    @Test
    @Transactional
    public void getAllTransaksisByKetIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where ket is not null
        defaultTransaksiShouldBeFound("ket.specified=true");

        // Get all the transaksiList where ket is null
        defaultTransaksiShouldNotBeFound("ket.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksisByTglInputIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where tglInput equals to DEFAULT_TGL_INPUT
        defaultTransaksiShouldBeFound("tglInput.equals=" + DEFAULT_TGL_INPUT);

        // Get all the transaksiList where tglInput equals to UPDATED_TGL_INPUT
        defaultTransaksiShouldNotBeFound("tglInput.equals=" + UPDATED_TGL_INPUT);
    }

    @Test
    @Transactional
    public void getAllTransaksisByTglInputIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where tglInput in DEFAULT_TGL_INPUT or UPDATED_TGL_INPUT
        defaultTransaksiShouldBeFound("tglInput.in=" + DEFAULT_TGL_INPUT + "," + UPDATED_TGL_INPUT);

        // Get all the transaksiList where tglInput equals to UPDATED_TGL_INPUT
        defaultTransaksiShouldNotBeFound("tglInput.in=" + UPDATED_TGL_INPUT);
    }

    @Test
    @Transactional
    public void getAllTransaksisByTglInputIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where tglInput is not null
        defaultTransaksiShouldBeFound("tglInput.specified=true");

        // Get all the transaksiList where tglInput is null
        defaultTransaksiShouldNotBeFound("tglInput.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksisByTglInputIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where tglInput greater than or equals to DEFAULT_TGL_INPUT
        defaultTransaksiShouldBeFound("tglInput.greaterOrEqualThan=" + DEFAULT_TGL_INPUT);

        // Get all the transaksiList where tglInput greater than or equals to UPDATED_TGL_INPUT
        defaultTransaksiShouldNotBeFound("tglInput.greaterOrEqualThan=" + UPDATED_TGL_INPUT);
    }

    @Test
    @Transactional
    public void getAllTransaksisByTglInputIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where tglInput less than or equals to DEFAULT_TGL_INPUT
        defaultTransaksiShouldNotBeFound("tglInput.lessThan=" + DEFAULT_TGL_INPUT);

        // Get all the transaksiList where tglInput less than or equals to UPDATED_TGL_INPUT
        defaultTransaksiShouldBeFound("tglInput.lessThan=" + UPDATED_TGL_INPUT);
    }


    @Test
    @Transactional
    public void getAllTransaksisByUserInputIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where userInput equals to DEFAULT_USER_INPUT
        defaultTransaksiShouldBeFound("userInput.equals=" + DEFAULT_USER_INPUT);

        // Get all the transaksiList where userInput equals to UPDATED_USER_INPUT
        defaultTransaksiShouldNotBeFound("userInput.equals=" + UPDATED_USER_INPUT);
    }

    @Test
    @Transactional
    public void getAllTransaksisByUserInputIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where userInput in DEFAULT_USER_INPUT or UPDATED_USER_INPUT
        defaultTransaksiShouldBeFound("userInput.in=" + DEFAULT_USER_INPUT + "," + UPDATED_USER_INPUT);

        // Get all the transaksiList where userInput equals to UPDATED_USER_INPUT
        defaultTransaksiShouldNotBeFound("userInput.in=" + UPDATED_USER_INPUT);
    }

    @Test
    @Transactional
    public void getAllTransaksisByUserInputIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where userInput is not null
        defaultTransaksiShouldBeFound("userInput.specified=true");

        // Get all the transaksiList where userInput is null
        defaultTransaksiShouldNotBeFound("userInput.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksisByIsstokIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where isstok equals to DEFAULT_ISSTOK
        defaultTransaksiShouldBeFound("isstok.equals=" + DEFAULT_ISSTOK);

        // Get all the transaksiList where isstok equals to UPDATED_ISSTOK
        defaultTransaksiShouldNotBeFound("isstok.equals=" + UPDATED_ISSTOK);
    }

    @Test
    @Transactional
    public void getAllTransaksisByIsstokIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where isstok in DEFAULT_ISSTOK or UPDATED_ISSTOK
        defaultTransaksiShouldBeFound("isstok.in=" + DEFAULT_ISSTOK + "," + UPDATED_ISSTOK);

        // Get all the transaksiList where isstok equals to UPDATED_ISSTOK
        defaultTransaksiShouldNotBeFound("isstok.in=" + UPDATED_ISSTOK);
    }

    @Test
    @Transactional
    public void getAllTransaksisByIsstokIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where isstok is not null
        defaultTransaksiShouldBeFound("isstok.specified=true");

        // Get all the transaksiList where isstok is null
        defaultTransaksiShouldNotBeFound("isstok.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksisByIsstokIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where isstok greater than or equals to DEFAULT_ISSTOK
        defaultTransaksiShouldBeFound("isstok.greaterOrEqualThan=" + DEFAULT_ISSTOK);

        // Get all the transaksiList where isstok greater than or equals to UPDATED_ISSTOK
        defaultTransaksiShouldNotBeFound("isstok.greaterOrEqualThan=" + UPDATED_ISSTOK);
    }

    @Test
    @Transactional
    public void getAllTransaksisByIsstokIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiRepository.saveAndFlush(transaksi);

        // Get all the transaksiList where isstok less than or equals to DEFAULT_ISSTOK
        defaultTransaksiShouldNotBeFound("isstok.lessThan=" + DEFAULT_ISSTOK);

        // Get all the transaksiList where isstok less than or equals to UPDATED_ISSTOK
        defaultTransaksiShouldBeFound("isstok.lessThan=" + UPDATED_ISSTOK);
    }


    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTransaksiShouldBeFound(String filter) throws Exception {
        restTransaksiMockMvc.perform(get("/api/transaksis?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaksi.getId().intValue())))
            .andExpect(jsonPath("$.[*].tglTransaksi").value(hasItem(sameInstant(DEFAULT_TGL_TRANSAKSI))))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].jml").value(hasItem(DEFAULT_JML.intValue())))
            .andExpect(jsonPath("$.[*].kodeTrx").value(hasItem(DEFAULT_KODE_TRX)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].saldoAwal").value(hasItem(DEFAULT_SALDO_AWAL.intValue())))
            .andExpect(jsonPath("$.[*].saldoAkhir").value(hasItem(DEFAULT_SALDO_AKHIR.intValue())))
            .andExpect(jsonPath("$.[*].ket").value(hasItem(DEFAULT_KET.toString())))
            .andExpect(jsonPath("$.[*].tglInput").value(hasItem(sameInstant(DEFAULT_TGL_INPUT))))
            .andExpect(jsonPath("$.[*].userInput").value(hasItem(DEFAULT_USER_INPUT.toString())))
            .andExpect(jsonPath("$.[*].isstok").value(hasItem(DEFAULT_ISSTOK)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTransaksiShouldNotBeFound(String filter) throws Exception {
        restTransaksiMockMvc.perform(get("/api/transaksis?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingTransaksi() throws Exception {
        // Get the transaksi
        restTransaksiMockMvc.perform(get("/api/transaksis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaksi() throws Exception {
        // Initialize the database
        transaksiService.save(transaksi);

        int databaseSizeBeforeUpdate = transaksiRepository.findAll().size();

        // Update the transaksi
        Transaksi updatedTransaksi = transaksiRepository.findOne(transaksi.getId());
        updatedTransaksi
            .tglTransaksi(UPDATED_TGL_TRANSAKSI)
            .idMember(UPDATED_ID_MEMBER)
            .nama(UPDATED_NAMA)
            .jml(UPDATED_JML)
            .kodeTrx(UPDATED_KODE_TRX)
            .status(UPDATED_STATUS)
            .saldoAwal(UPDATED_SALDO_AWAL)
            .saldoAkhir(UPDATED_SALDO_AKHIR)
            .ket(UPDATED_KET)
            .tglInput(UPDATED_TGL_INPUT)
            .userInput(UPDATED_USER_INPUT)
            .isstok(UPDATED_ISSTOK);

        restTransaksiMockMvc.perform(put("/api/transaksis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransaksi)))
            .andExpect(status().isOk());

        // Validate the Transaksi in the database
        List<Transaksi> transaksiList = transaksiRepository.findAll();
        assertThat(transaksiList).hasSize(databaseSizeBeforeUpdate);
        Transaksi testTransaksi = transaksiList.get(transaksiList.size() - 1);
        assertThat(testTransaksi.getTglTransaksi()).isEqualTo(UPDATED_TGL_TRANSAKSI);
        assertThat(testTransaksi.getIdMember()).isEqualTo(UPDATED_ID_MEMBER);
        assertThat(testTransaksi.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testTransaksi.getJml()).isEqualTo(UPDATED_JML);
        assertThat(testTransaksi.getKodeTrx()).isEqualTo(UPDATED_KODE_TRX);
        assertThat(testTransaksi.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransaksi.getSaldoAwal()).isEqualTo(UPDATED_SALDO_AWAL);
        assertThat(testTransaksi.getSaldoAkhir()).isEqualTo(UPDATED_SALDO_AKHIR);
        assertThat(testTransaksi.getKet()).isEqualTo(UPDATED_KET);
        assertThat(testTransaksi.getTglInput()).isEqualTo(UPDATED_TGL_INPUT);
        assertThat(testTransaksi.getUserInput()).isEqualTo(UPDATED_USER_INPUT);
        assertThat(testTransaksi.getIsstok()).isEqualTo(UPDATED_ISSTOK);

        // Validate the Transaksi in Elasticsearch
        Transaksi transaksiEs = transaksiSearchRepository.findOne(testTransaksi.getId());
        assertThat(transaksiEs).isEqualToComparingFieldByField(testTransaksi);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaksi() throws Exception {
        int databaseSizeBeforeUpdate = transaksiRepository.findAll().size();

        // Create the Transaksi

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransaksiMockMvc.perform(put("/api/transaksis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaksi)))
            .andExpect(status().isCreated());

        // Validate the Transaksi in the database
        List<Transaksi> transaksiList = transaksiRepository.findAll();
        assertThat(transaksiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransaksi() throws Exception {
        // Initialize the database
        transaksiService.save(transaksi);

        int databaseSizeBeforeDelete = transaksiRepository.findAll().size();

        // Get the transaksi
        restTransaksiMockMvc.perform(delete("/api/transaksis/{id}", transaksi.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean transaksiExistsInEs = transaksiSearchRepository.exists(transaksi.getId());
        assertThat(transaksiExistsInEs).isFalse();

        // Validate the database is empty
        List<Transaksi> transaksiList = transaksiRepository.findAll();
        assertThat(transaksiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTransaksi() throws Exception {
        // Initialize the database
        transaksiService.save(transaksi);

        // Search the transaksi
        restTransaksiMockMvc.perform(get("/api/_search/transaksis?query=id:" + transaksi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaksi.getId().intValue())))
            .andExpect(jsonPath("$.[*].tglTransaksi").value(hasItem(sameInstant(DEFAULT_TGL_TRANSAKSI))))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].jml").value(hasItem(DEFAULT_JML.intValue())))
            .andExpect(jsonPath("$.[*].kodeTrx").value(hasItem(DEFAULT_KODE_TRX)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].saldoAwal").value(hasItem(DEFAULT_SALDO_AWAL.intValue())))
            .andExpect(jsonPath("$.[*].saldoAkhir").value(hasItem(DEFAULT_SALDO_AKHIR.intValue())))
            .andExpect(jsonPath("$.[*].ket").value(hasItem(DEFAULT_KET.toString())))
            .andExpect(jsonPath("$.[*].tglInput").value(hasItem(sameInstant(DEFAULT_TGL_INPUT))))
            .andExpect(jsonPath("$.[*].userInput").value(hasItem(DEFAULT_USER_INPUT.toString())))
            .andExpect(jsonPath("$.[*].isstok").value(hasItem(DEFAULT_ISSTOK)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transaksi.class);
        Transaksi transaksi1 = new Transaksi();
        transaksi1.setId(1L);
        Transaksi transaksi2 = new Transaksi();
        transaksi2.setId(transaksi1.getId());
        assertThat(transaksi1).isEqualTo(transaksi2);
        transaksi2.setId(2L);
        assertThat(transaksi1).isNotEqualTo(transaksi2);
        transaksi1.setId(null);
        assertThat(transaksi1).isNotEqualTo(transaksi2);
    }
}
