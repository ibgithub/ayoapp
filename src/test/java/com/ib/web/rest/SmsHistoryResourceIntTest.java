package com.ib.web.rest;

import com.ib.AyoappApp;

import com.ib.domain.SmsHistory;
import com.ib.repository.SmsHistoryRepository;
import com.ib.service.SmsHistoryService;
import com.ib.repository.search.SmsHistorySearchRepository;
import com.ib.web.rest.errors.ExceptionTranslator;
import com.ib.service.dto.SmsHistoryCriteria;
import com.ib.service.SmsHistoryQueryService;

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
 * Test class for the SmsHistoryResource REST controller.
 *
 * @see SmsHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AyoappApp.class)
public class SmsHistoryResourceIntTest {

    private static final ZonedDateTime DEFAULT_TGL_INPUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TGL_INPUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NO_HP = "AAAAAAAAAA";
    private static final String UPDATED_NO_HP = "BBBBBBBBBB";

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_PESAN = "AAAAAAAAAA";
    private static final String UPDATED_PESAN = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIPE = 1;
    private static final Integer UPDATED_TIPE = 2;

    private static final ZonedDateTime DEFAULT_TGL_SMS = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TGL_SMS = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_COM = "AAAAAAAAAA";
    private static final String UPDATED_COM = "BBBBBBBBBB";

    private static final Integer DEFAULT_REPORT = 1;
    private static final Integer UPDATED_REPORT = 2;

    private static final Integer DEFAULT_TRX = 1;
    private static final Integer UPDATED_TRX = 2;

    private static final Integer DEFAULT_POSTING = 1;
    private static final Integer UPDATED_POSTING = 2;

    private static final String DEFAULT_REF = "AAAAAAAAAA";
    private static final String UPDATED_REF = "BBBBBBBBBB";

    private static final String DEFAULT_MSISDN = "AAAAAAAAAA";
    private static final String UPDATED_MSISDN = "BBBBBBBBBB";

    private static final String DEFAULT_ENGINENAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGINENAME = "BBBBBBBBBB";

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPEMSG = 1;
    private static final Integer UPDATED_TYPEMSG = 2;

    private static final String DEFAULT_ID_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_ID_MEMBER = "BBBBBBBBBB";

    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    @Autowired
    private SmsHistoryService smsHistoryService;

    @Autowired
    private SmsHistorySearchRepository smsHistorySearchRepository;

    @Autowired
    private SmsHistoryQueryService smsHistoryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSmsHistoryMockMvc;

    private SmsHistory smsHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SmsHistoryResource smsHistoryResource = new SmsHistoryResource(smsHistoryService, smsHistoryQueryService);
        this.restSmsHistoryMockMvc = MockMvcBuilders.standaloneSetup(smsHistoryResource)
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
    public static SmsHistory createEntity(EntityManager em) {
        SmsHistory smsHistory = new SmsHistory()
            .tglInput(DEFAULT_TGL_INPUT)
            .noHp(DEFAULT_NO_HP)
            .nama(DEFAULT_NAMA)
            .pesan(DEFAULT_PESAN)
            .tipe(DEFAULT_TIPE)
            .tglSms(DEFAULT_TGL_SMS)
            .com(DEFAULT_COM)
            .report(DEFAULT_REPORT)
            .trx(DEFAULT_TRX)
            .posting(DEFAULT_POSTING)
            .ref(DEFAULT_REF)
            .msisdn(DEFAULT_MSISDN)
            .enginename(DEFAULT_ENGINENAME)
            .ip(DEFAULT_IP)
            .typemsg(DEFAULT_TYPEMSG)
            .idMember(DEFAULT_ID_MEMBER);
        return smsHistory;
    }

    @Before
    public void initTest() {
        smsHistorySearchRepository.deleteAll();
        smsHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createSmsHistory() throws Exception {
        int databaseSizeBeforeCreate = smsHistoryRepository.findAll().size();

        // Create the SmsHistory
        restSmsHistoryMockMvc.perform(post("/api/sms-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsHistory)))
            .andExpect(status().isCreated());

        // Validate the SmsHistory in the database
        List<SmsHistory> smsHistoryList = smsHistoryRepository.findAll();
        assertThat(smsHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        SmsHistory testSmsHistory = smsHistoryList.get(smsHistoryList.size() - 1);
        assertThat(testSmsHistory.getTglInput()).isEqualTo(DEFAULT_TGL_INPUT);
        assertThat(testSmsHistory.getNoHp()).isEqualTo(DEFAULT_NO_HP);
        assertThat(testSmsHistory.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testSmsHistory.getPesan()).isEqualTo(DEFAULT_PESAN);
        assertThat(testSmsHistory.getTipe()).isEqualTo(DEFAULT_TIPE);
        assertThat(testSmsHistory.getTglSms()).isEqualTo(DEFAULT_TGL_SMS);
        assertThat(testSmsHistory.getCom()).isEqualTo(DEFAULT_COM);
        assertThat(testSmsHistory.getReport()).isEqualTo(DEFAULT_REPORT);
        assertThat(testSmsHistory.getTrx()).isEqualTo(DEFAULT_TRX);
        assertThat(testSmsHistory.getPosting()).isEqualTo(DEFAULT_POSTING);
        assertThat(testSmsHistory.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testSmsHistory.getMsisdn()).isEqualTo(DEFAULT_MSISDN);
        assertThat(testSmsHistory.getEnginename()).isEqualTo(DEFAULT_ENGINENAME);
        assertThat(testSmsHistory.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testSmsHistory.getTypemsg()).isEqualTo(DEFAULT_TYPEMSG);
        assertThat(testSmsHistory.getIdMember()).isEqualTo(DEFAULT_ID_MEMBER);

        // Validate the SmsHistory in Elasticsearch
        SmsHistory smsHistoryEs = smsHistorySearchRepository.findOne(testSmsHistory.getId());
        assertThat(smsHistoryEs).isEqualToComparingFieldByField(testSmsHistory);
    }

    @Test
    @Transactional
    public void createSmsHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = smsHistoryRepository.findAll().size();

        // Create the SmsHistory with an existing ID
        smsHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSmsHistoryMockMvc.perform(post("/api/sms-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsHistory)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SmsHistory> smsHistoryList = smsHistoryRepository.findAll();
        assertThat(smsHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSmsHistories() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList
        restSmsHistoryMockMvc.perform(get("/api/sms-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(smsHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].tglInput").value(hasItem(sameInstant(DEFAULT_TGL_INPUT))))
            .andExpect(jsonPath("$.[*].noHp").value(hasItem(DEFAULT_NO_HP.toString())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].pesan").value(hasItem(DEFAULT_PESAN.toString())))
            .andExpect(jsonPath("$.[*].tipe").value(hasItem(DEFAULT_TIPE)))
            .andExpect(jsonPath("$.[*].tglSms").value(hasItem(sameInstant(DEFAULT_TGL_SMS))))
            .andExpect(jsonPath("$.[*].com").value(hasItem(DEFAULT_COM.toString())))
            .andExpect(jsonPath("$.[*].report").value(hasItem(DEFAULT_REPORT)))
            .andExpect(jsonPath("$.[*].trx").value(hasItem(DEFAULT_TRX)))
            .andExpect(jsonPath("$.[*].posting").value(hasItem(DEFAULT_POSTING)))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF.toString())))
            .andExpect(jsonPath("$.[*].msisdn").value(hasItem(DEFAULT_MSISDN.toString())))
            .andExpect(jsonPath("$.[*].enginename").value(hasItem(DEFAULT_ENGINENAME.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].typemsg").value(hasItem(DEFAULT_TYPEMSG)))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())));
    }

    @Test
    @Transactional
    public void getSmsHistory() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get the smsHistory
        restSmsHistoryMockMvc.perform(get("/api/sms-histories/{id}", smsHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(smsHistory.getId().intValue()))
            .andExpect(jsonPath("$.tglInput").value(sameInstant(DEFAULT_TGL_INPUT)))
            .andExpect(jsonPath("$.noHp").value(DEFAULT_NO_HP.toString()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.pesan").value(DEFAULT_PESAN.toString()))
            .andExpect(jsonPath("$.tipe").value(DEFAULT_TIPE))
            .andExpect(jsonPath("$.tglSms").value(sameInstant(DEFAULT_TGL_SMS)))
            .andExpect(jsonPath("$.com").value(DEFAULT_COM.toString()))
            .andExpect(jsonPath("$.report").value(DEFAULT_REPORT))
            .andExpect(jsonPath("$.trx").value(DEFAULT_TRX))
            .andExpect(jsonPath("$.posting").value(DEFAULT_POSTING))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF.toString()))
            .andExpect(jsonPath("$.msisdn").value(DEFAULT_MSISDN.toString()))
            .andExpect(jsonPath("$.enginename").value(DEFAULT_ENGINENAME.toString()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP.toString()))
            .andExpect(jsonPath("$.typemsg").value(DEFAULT_TYPEMSG))
            .andExpect(jsonPath("$.idMember").value(DEFAULT_ID_MEMBER.toString()));
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTglInputIsEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where tglInput equals to DEFAULT_TGL_INPUT
        defaultSmsHistoryShouldBeFound("tglInput.equals=" + DEFAULT_TGL_INPUT);

        // Get all the smsHistoryList where tglInput equals to UPDATED_TGL_INPUT
        defaultSmsHistoryShouldNotBeFound("tglInput.equals=" + UPDATED_TGL_INPUT);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTglInputIsInShouldWork() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where tglInput in DEFAULT_TGL_INPUT or UPDATED_TGL_INPUT
        defaultSmsHistoryShouldBeFound("tglInput.in=" + DEFAULT_TGL_INPUT + "," + UPDATED_TGL_INPUT);

        // Get all the smsHistoryList where tglInput equals to UPDATED_TGL_INPUT
        defaultSmsHistoryShouldNotBeFound("tglInput.in=" + UPDATED_TGL_INPUT);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTglInputIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where tglInput is not null
        defaultSmsHistoryShouldBeFound("tglInput.specified=true");

        // Get all the smsHistoryList where tglInput is null
        defaultSmsHistoryShouldNotBeFound("tglInput.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTglInputIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where tglInput greater than or equals to DEFAULT_TGL_INPUT
        defaultSmsHistoryShouldBeFound("tglInput.greaterOrEqualThan=" + DEFAULT_TGL_INPUT);

        // Get all the smsHistoryList where tglInput greater than or equals to UPDATED_TGL_INPUT
        defaultSmsHistoryShouldNotBeFound("tglInput.greaterOrEqualThan=" + UPDATED_TGL_INPUT);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTglInputIsLessThanSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where tglInput less than or equals to DEFAULT_TGL_INPUT
        defaultSmsHistoryShouldNotBeFound("tglInput.lessThan=" + DEFAULT_TGL_INPUT);

        // Get all the smsHistoryList where tglInput less than or equals to UPDATED_TGL_INPUT
        defaultSmsHistoryShouldBeFound("tglInput.lessThan=" + UPDATED_TGL_INPUT);
    }


    @Test
    @Transactional
    public void getAllSmsHistoriesByNoHpIsEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where noHp equals to DEFAULT_NO_HP
        defaultSmsHistoryShouldBeFound("noHp.equals=" + DEFAULT_NO_HP);

        // Get all the smsHistoryList where noHp equals to UPDATED_NO_HP
        defaultSmsHistoryShouldNotBeFound("noHp.equals=" + UPDATED_NO_HP);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByNoHpIsInShouldWork() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where noHp in DEFAULT_NO_HP or UPDATED_NO_HP
        defaultSmsHistoryShouldBeFound("noHp.in=" + DEFAULT_NO_HP + "," + UPDATED_NO_HP);

        // Get all the smsHistoryList where noHp equals to UPDATED_NO_HP
        defaultSmsHistoryShouldNotBeFound("noHp.in=" + UPDATED_NO_HP);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByNoHpIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where noHp is not null
        defaultSmsHistoryShouldBeFound("noHp.specified=true");

        // Get all the smsHistoryList where noHp is null
        defaultSmsHistoryShouldNotBeFound("noHp.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByNamaIsEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where nama equals to DEFAULT_NAMA
        defaultSmsHistoryShouldBeFound("nama.equals=" + DEFAULT_NAMA);

        // Get all the smsHistoryList where nama equals to UPDATED_NAMA
        defaultSmsHistoryShouldNotBeFound("nama.equals=" + UPDATED_NAMA);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByNamaIsInShouldWork() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where nama in DEFAULT_NAMA or UPDATED_NAMA
        defaultSmsHistoryShouldBeFound("nama.in=" + DEFAULT_NAMA + "," + UPDATED_NAMA);

        // Get all the smsHistoryList where nama equals to UPDATED_NAMA
        defaultSmsHistoryShouldNotBeFound("nama.in=" + UPDATED_NAMA);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByNamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where nama is not null
        defaultSmsHistoryShouldBeFound("nama.specified=true");

        // Get all the smsHistoryList where nama is null
        defaultSmsHistoryShouldNotBeFound("nama.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByPesanIsEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where pesan equals to DEFAULT_PESAN
        defaultSmsHistoryShouldBeFound("pesan.equals=" + DEFAULT_PESAN);

        // Get all the smsHistoryList where pesan equals to UPDATED_PESAN
        defaultSmsHistoryShouldNotBeFound("pesan.equals=" + UPDATED_PESAN);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByPesanIsInShouldWork() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where pesan in DEFAULT_PESAN or UPDATED_PESAN
        defaultSmsHistoryShouldBeFound("pesan.in=" + DEFAULT_PESAN + "," + UPDATED_PESAN);

        // Get all the smsHistoryList where pesan equals to UPDATED_PESAN
        defaultSmsHistoryShouldNotBeFound("pesan.in=" + UPDATED_PESAN);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByPesanIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where pesan is not null
        defaultSmsHistoryShouldBeFound("pesan.specified=true");

        // Get all the smsHistoryList where pesan is null
        defaultSmsHistoryShouldNotBeFound("pesan.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTipeIsEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where tipe equals to DEFAULT_TIPE
        defaultSmsHistoryShouldBeFound("tipe.equals=" + DEFAULT_TIPE);

        // Get all the smsHistoryList where tipe equals to UPDATED_TIPE
        defaultSmsHistoryShouldNotBeFound("tipe.equals=" + UPDATED_TIPE);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTipeIsInShouldWork() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where tipe in DEFAULT_TIPE or UPDATED_TIPE
        defaultSmsHistoryShouldBeFound("tipe.in=" + DEFAULT_TIPE + "," + UPDATED_TIPE);

        // Get all the smsHistoryList where tipe equals to UPDATED_TIPE
        defaultSmsHistoryShouldNotBeFound("tipe.in=" + UPDATED_TIPE);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTipeIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where tipe is not null
        defaultSmsHistoryShouldBeFound("tipe.specified=true");

        // Get all the smsHistoryList where tipe is null
        defaultSmsHistoryShouldNotBeFound("tipe.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTipeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where tipe greater than or equals to DEFAULT_TIPE
        defaultSmsHistoryShouldBeFound("tipe.greaterOrEqualThan=" + DEFAULT_TIPE);

        // Get all the smsHistoryList where tipe greater than or equals to UPDATED_TIPE
        defaultSmsHistoryShouldNotBeFound("tipe.greaterOrEqualThan=" + UPDATED_TIPE);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTipeIsLessThanSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where tipe less than or equals to DEFAULT_TIPE
        defaultSmsHistoryShouldNotBeFound("tipe.lessThan=" + DEFAULT_TIPE);

        // Get all the smsHistoryList where tipe less than or equals to UPDATED_TIPE
        defaultSmsHistoryShouldBeFound("tipe.lessThan=" + UPDATED_TIPE);
    }


    @Test
    @Transactional
    public void getAllSmsHistoriesByTglSmsIsEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where tglSms equals to DEFAULT_TGL_SMS
        defaultSmsHistoryShouldBeFound("tglSms.equals=" + DEFAULT_TGL_SMS);

        // Get all the smsHistoryList where tglSms equals to UPDATED_TGL_SMS
        defaultSmsHistoryShouldNotBeFound("tglSms.equals=" + UPDATED_TGL_SMS);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTglSmsIsInShouldWork() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where tglSms in DEFAULT_TGL_SMS or UPDATED_TGL_SMS
        defaultSmsHistoryShouldBeFound("tglSms.in=" + DEFAULT_TGL_SMS + "," + UPDATED_TGL_SMS);

        // Get all the smsHistoryList where tglSms equals to UPDATED_TGL_SMS
        defaultSmsHistoryShouldNotBeFound("tglSms.in=" + UPDATED_TGL_SMS);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTglSmsIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where tglSms is not null
        defaultSmsHistoryShouldBeFound("tglSms.specified=true");

        // Get all the smsHistoryList where tglSms is null
        defaultSmsHistoryShouldNotBeFound("tglSms.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTglSmsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where tglSms greater than or equals to DEFAULT_TGL_SMS
        defaultSmsHistoryShouldBeFound("tglSms.greaterOrEqualThan=" + DEFAULT_TGL_SMS);

        // Get all the smsHistoryList where tglSms greater than or equals to UPDATED_TGL_SMS
        defaultSmsHistoryShouldNotBeFound("tglSms.greaterOrEqualThan=" + UPDATED_TGL_SMS);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTglSmsIsLessThanSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where tglSms less than or equals to DEFAULT_TGL_SMS
        defaultSmsHistoryShouldNotBeFound("tglSms.lessThan=" + DEFAULT_TGL_SMS);

        // Get all the smsHistoryList where tglSms less than or equals to UPDATED_TGL_SMS
        defaultSmsHistoryShouldBeFound("tglSms.lessThan=" + UPDATED_TGL_SMS);
    }


    @Test
    @Transactional
    public void getAllSmsHistoriesByComIsEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where com equals to DEFAULT_COM
        defaultSmsHistoryShouldBeFound("com.equals=" + DEFAULT_COM);

        // Get all the smsHistoryList where com equals to UPDATED_COM
        defaultSmsHistoryShouldNotBeFound("com.equals=" + UPDATED_COM);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByComIsInShouldWork() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where com in DEFAULT_COM or UPDATED_COM
        defaultSmsHistoryShouldBeFound("com.in=" + DEFAULT_COM + "," + UPDATED_COM);

        // Get all the smsHistoryList where com equals to UPDATED_COM
        defaultSmsHistoryShouldNotBeFound("com.in=" + UPDATED_COM);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByComIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where com is not null
        defaultSmsHistoryShouldBeFound("com.specified=true");

        // Get all the smsHistoryList where com is null
        defaultSmsHistoryShouldNotBeFound("com.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByReportIsEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where report equals to DEFAULT_REPORT
        defaultSmsHistoryShouldBeFound("report.equals=" + DEFAULT_REPORT);

        // Get all the smsHistoryList where report equals to UPDATED_REPORT
        defaultSmsHistoryShouldNotBeFound("report.equals=" + UPDATED_REPORT);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByReportIsInShouldWork() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where report in DEFAULT_REPORT or UPDATED_REPORT
        defaultSmsHistoryShouldBeFound("report.in=" + DEFAULT_REPORT + "," + UPDATED_REPORT);

        // Get all the smsHistoryList where report equals to UPDATED_REPORT
        defaultSmsHistoryShouldNotBeFound("report.in=" + UPDATED_REPORT);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByReportIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where report is not null
        defaultSmsHistoryShouldBeFound("report.specified=true");

        // Get all the smsHistoryList where report is null
        defaultSmsHistoryShouldNotBeFound("report.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByReportIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where report greater than or equals to DEFAULT_REPORT
        defaultSmsHistoryShouldBeFound("report.greaterOrEqualThan=" + DEFAULT_REPORT);

        // Get all the smsHistoryList where report greater than or equals to UPDATED_REPORT
        defaultSmsHistoryShouldNotBeFound("report.greaterOrEqualThan=" + UPDATED_REPORT);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByReportIsLessThanSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where report less than or equals to DEFAULT_REPORT
        defaultSmsHistoryShouldNotBeFound("report.lessThan=" + DEFAULT_REPORT);

        // Get all the smsHistoryList where report less than or equals to UPDATED_REPORT
        defaultSmsHistoryShouldBeFound("report.lessThan=" + UPDATED_REPORT);
    }


    @Test
    @Transactional
    public void getAllSmsHistoriesByTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where trx equals to DEFAULT_TRX
        defaultSmsHistoryShouldBeFound("trx.equals=" + DEFAULT_TRX);

        // Get all the smsHistoryList where trx equals to UPDATED_TRX
        defaultSmsHistoryShouldNotBeFound("trx.equals=" + UPDATED_TRX);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTrxIsInShouldWork() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where trx in DEFAULT_TRX or UPDATED_TRX
        defaultSmsHistoryShouldBeFound("trx.in=" + DEFAULT_TRX + "," + UPDATED_TRX);

        // Get all the smsHistoryList where trx equals to UPDATED_TRX
        defaultSmsHistoryShouldNotBeFound("trx.in=" + UPDATED_TRX);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where trx is not null
        defaultSmsHistoryShouldBeFound("trx.specified=true");

        // Get all the smsHistoryList where trx is null
        defaultSmsHistoryShouldNotBeFound("trx.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where trx greater than or equals to DEFAULT_TRX
        defaultSmsHistoryShouldBeFound("trx.greaterOrEqualThan=" + DEFAULT_TRX);

        // Get all the smsHistoryList where trx greater than or equals to UPDATED_TRX
        defaultSmsHistoryShouldNotBeFound("trx.greaterOrEqualThan=" + UPDATED_TRX);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where trx less than or equals to DEFAULT_TRX
        defaultSmsHistoryShouldNotBeFound("trx.lessThan=" + DEFAULT_TRX);

        // Get all the smsHistoryList where trx less than or equals to UPDATED_TRX
        defaultSmsHistoryShouldBeFound("trx.lessThan=" + UPDATED_TRX);
    }


    @Test
    @Transactional
    public void getAllSmsHistoriesByPostingIsEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where posting equals to DEFAULT_POSTING
        defaultSmsHistoryShouldBeFound("posting.equals=" + DEFAULT_POSTING);

        // Get all the smsHistoryList where posting equals to UPDATED_POSTING
        defaultSmsHistoryShouldNotBeFound("posting.equals=" + UPDATED_POSTING);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByPostingIsInShouldWork() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where posting in DEFAULT_POSTING or UPDATED_POSTING
        defaultSmsHistoryShouldBeFound("posting.in=" + DEFAULT_POSTING + "," + UPDATED_POSTING);

        // Get all the smsHistoryList where posting equals to UPDATED_POSTING
        defaultSmsHistoryShouldNotBeFound("posting.in=" + UPDATED_POSTING);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByPostingIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where posting is not null
        defaultSmsHistoryShouldBeFound("posting.specified=true");

        // Get all the smsHistoryList where posting is null
        defaultSmsHistoryShouldNotBeFound("posting.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByPostingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where posting greater than or equals to DEFAULT_POSTING
        defaultSmsHistoryShouldBeFound("posting.greaterOrEqualThan=" + DEFAULT_POSTING);

        // Get all the smsHistoryList where posting greater than or equals to UPDATED_POSTING
        defaultSmsHistoryShouldNotBeFound("posting.greaterOrEqualThan=" + UPDATED_POSTING);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByPostingIsLessThanSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where posting less than or equals to DEFAULT_POSTING
        defaultSmsHistoryShouldNotBeFound("posting.lessThan=" + DEFAULT_POSTING);

        // Get all the smsHistoryList where posting less than or equals to UPDATED_POSTING
        defaultSmsHistoryShouldBeFound("posting.lessThan=" + UPDATED_POSTING);
    }


    @Test
    @Transactional
    public void getAllSmsHistoriesByRefIsEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where ref equals to DEFAULT_REF
        defaultSmsHistoryShouldBeFound("ref.equals=" + DEFAULT_REF);

        // Get all the smsHistoryList where ref equals to UPDATED_REF
        defaultSmsHistoryShouldNotBeFound("ref.equals=" + UPDATED_REF);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByRefIsInShouldWork() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where ref in DEFAULT_REF or UPDATED_REF
        defaultSmsHistoryShouldBeFound("ref.in=" + DEFAULT_REF + "," + UPDATED_REF);

        // Get all the smsHistoryList where ref equals to UPDATED_REF
        defaultSmsHistoryShouldNotBeFound("ref.in=" + UPDATED_REF);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByRefIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where ref is not null
        defaultSmsHistoryShouldBeFound("ref.specified=true");

        // Get all the smsHistoryList where ref is null
        defaultSmsHistoryShouldNotBeFound("ref.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByMsisdnIsEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where msisdn equals to DEFAULT_MSISDN
        defaultSmsHistoryShouldBeFound("msisdn.equals=" + DEFAULT_MSISDN);

        // Get all the smsHistoryList where msisdn equals to UPDATED_MSISDN
        defaultSmsHistoryShouldNotBeFound("msisdn.equals=" + UPDATED_MSISDN);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByMsisdnIsInShouldWork() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where msisdn in DEFAULT_MSISDN or UPDATED_MSISDN
        defaultSmsHistoryShouldBeFound("msisdn.in=" + DEFAULT_MSISDN + "," + UPDATED_MSISDN);

        // Get all the smsHistoryList where msisdn equals to UPDATED_MSISDN
        defaultSmsHistoryShouldNotBeFound("msisdn.in=" + UPDATED_MSISDN);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByMsisdnIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where msisdn is not null
        defaultSmsHistoryShouldBeFound("msisdn.specified=true");

        // Get all the smsHistoryList where msisdn is null
        defaultSmsHistoryShouldNotBeFound("msisdn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByEnginenameIsEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where enginename equals to DEFAULT_ENGINENAME
        defaultSmsHistoryShouldBeFound("enginename.equals=" + DEFAULT_ENGINENAME);

        // Get all the smsHistoryList where enginename equals to UPDATED_ENGINENAME
        defaultSmsHistoryShouldNotBeFound("enginename.equals=" + UPDATED_ENGINENAME);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByEnginenameIsInShouldWork() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where enginename in DEFAULT_ENGINENAME or UPDATED_ENGINENAME
        defaultSmsHistoryShouldBeFound("enginename.in=" + DEFAULT_ENGINENAME + "," + UPDATED_ENGINENAME);

        // Get all the smsHistoryList where enginename equals to UPDATED_ENGINENAME
        defaultSmsHistoryShouldNotBeFound("enginename.in=" + UPDATED_ENGINENAME);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByEnginenameIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where enginename is not null
        defaultSmsHistoryShouldBeFound("enginename.specified=true");

        // Get all the smsHistoryList where enginename is null
        defaultSmsHistoryShouldNotBeFound("enginename.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByIpIsEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where ip equals to DEFAULT_IP
        defaultSmsHistoryShouldBeFound("ip.equals=" + DEFAULT_IP);

        // Get all the smsHistoryList where ip equals to UPDATED_IP
        defaultSmsHistoryShouldNotBeFound("ip.equals=" + UPDATED_IP);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByIpIsInShouldWork() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where ip in DEFAULT_IP or UPDATED_IP
        defaultSmsHistoryShouldBeFound("ip.in=" + DEFAULT_IP + "," + UPDATED_IP);

        // Get all the smsHistoryList where ip equals to UPDATED_IP
        defaultSmsHistoryShouldNotBeFound("ip.in=" + UPDATED_IP);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByIpIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where ip is not null
        defaultSmsHistoryShouldBeFound("ip.specified=true");

        // Get all the smsHistoryList where ip is null
        defaultSmsHistoryShouldNotBeFound("ip.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTypemsgIsEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where typemsg equals to DEFAULT_TYPEMSG
        defaultSmsHistoryShouldBeFound("typemsg.equals=" + DEFAULT_TYPEMSG);

        // Get all the smsHistoryList where typemsg equals to UPDATED_TYPEMSG
        defaultSmsHistoryShouldNotBeFound("typemsg.equals=" + UPDATED_TYPEMSG);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTypemsgIsInShouldWork() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where typemsg in DEFAULT_TYPEMSG or UPDATED_TYPEMSG
        defaultSmsHistoryShouldBeFound("typemsg.in=" + DEFAULT_TYPEMSG + "," + UPDATED_TYPEMSG);

        // Get all the smsHistoryList where typemsg equals to UPDATED_TYPEMSG
        defaultSmsHistoryShouldNotBeFound("typemsg.in=" + UPDATED_TYPEMSG);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTypemsgIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where typemsg is not null
        defaultSmsHistoryShouldBeFound("typemsg.specified=true");

        // Get all the smsHistoryList where typemsg is null
        defaultSmsHistoryShouldNotBeFound("typemsg.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTypemsgIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where typemsg greater than or equals to DEFAULT_TYPEMSG
        defaultSmsHistoryShouldBeFound("typemsg.greaterOrEqualThan=" + DEFAULT_TYPEMSG);

        // Get all the smsHistoryList where typemsg greater than or equals to UPDATED_TYPEMSG
        defaultSmsHistoryShouldNotBeFound("typemsg.greaterOrEqualThan=" + UPDATED_TYPEMSG);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByTypemsgIsLessThanSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where typemsg less than or equals to DEFAULT_TYPEMSG
        defaultSmsHistoryShouldNotBeFound("typemsg.lessThan=" + DEFAULT_TYPEMSG);

        // Get all the smsHistoryList where typemsg less than or equals to UPDATED_TYPEMSG
        defaultSmsHistoryShouldBeFound("typemsg.lessThan=" + UPDATED_TYPEMSG);
    }


    @Test
    @Transactional
    public void getAllSmsHistoriesByIdMemberIsEqualToSomething() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where idMember equals to DEFAULT_ID_MEMBER
        defaultSmsHistoryShouldBeFound("idMember.equals=" + DEFAULT_ID_MEMBER);

        // Get all the smsHistoryList where idMember equals to UPDATED_ID_MEMBER
        defaultSmsHistoryShouldNotBeFound("idMember.equals=" + UPDATED_ID_MEMBER);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByIdMemberIsInShouldWork() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where idMember in DEFAULT_ID_MEMBER or UPDATED_ID_MEMBER
        defaultSmsHistoryShouldBeFound("idMember.in=" + DEFAULT_ID_MEMBER + "," + UPDATED_ID_MEMBER);

        // Get all the smsHistoryList where idMember equals to UPDATED_ID_MEMBER
        defaultSmsHistoryShouldNotBeFound("idMember.in=" + UPDATED_ID_MEMBER);
    }

    @Test
    @Transactional
    public void getAllSmsHistoriesByIdMemberIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsHistoryRepository.saveAndFlush(smsHistory);

        // Get all the smsHistoryList where idMember is not null
        defaultSmsHistoryShouldBeFound("idMember.specified=true");

        // Get all the smsHistoryList where idMember is null
        defaultSmsHistoryShouldNotBeFound("idMember.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSmsHistoryShouldBeFound(String filter) throws Exception {
        restSmsHistoryMockMvc.perform(get("/api/sms-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(smsHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].tglInput").value(hasItem(sameInstant(DEFAULT_TGL_INPUT))))
            .andExpect(jsonPath("$.[*].noHp").value(hasItem(DEFAULT_NO_HP.toString())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].pesan").value(hasItem(DEFAULT_PESAN.toString())))
            .andExpect(jsonPath("$.[*].tipe").value(hasItem(DEFAULT_TIPE)))
            .andExpect(jsonPath("$.[*].tglSms").value(hasItem(sameInstant(DEFAULT_TGL_SMS))))
            .andExpect(jsonPath("$.[*].com").value(hasItem(DEFAULT_COM.toString())))
            .andExpect(jsonPath("$.[*].report").value(hasItem(DEFAULT_REPORT)))
            .andExpect(jsonPath("$.[*].trx").value(hasItem(DEFAULT_TRX)))
            .andExpect(jsonPath("$.[*].posting").value(hasItem(DEFAULT_POSTING)))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF.toString())))
            .andExpect(jsonPath("$.[*].msisdn").value(hasItem(DEFAULT_MSISDN.toString())))
            .andExpect(jsonPath("$.[*].enginename").value(hasItem(DEFAULT_ENGINENAME.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].typemsg").value(hasItem(DEFAULT_TYPEMSG)))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSmsHistoryShouldNotBeFound(String filter) throws Exception {
        restSmsHistoryMockMvc.perform(get("/api/sms-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingSmsHistory() throws Exception {
        // Get the smsHistory
        restSmsHistoryMockMvc.perform(get("/api/sms-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmsHistory() throws Exception {
        // Initialize the database
        smsHistoryService.save(smsHistory);

        int databaseSizeBeforeUpdate = smsHistoryRepository.findAll().size();

        // Update the smsHistory
        SmsHistory updatedSmsHistory = smsHistoryRepository.findOne(smsHistory.getId());
        updatedSmsHistory
            .tglInput(UPDATED_TGL_INPUT)
            .noHp(UPDATED_NO_HP)
            .nama(UPDATED_NAMA)
            .pesan(UPDATED_PESAN)
            .tipe(UPDATED_TIPE)
            .tglSms(UPDATED_TGL_SMS)
            .com(UPDATED_COM)
            .report(UPDATED_REPORT)
            .trx(UPDATED_TRX)
            .posting(UPDATED_POSTING)
            .ref(UPDATED_REF)
            .msisdn(UPDATED_MSISDN)
            .enginename(UPDATED_ENGINENAME)
            .ip(UPDATED_IP)
            .typemsg(UPDATED_TYPEMSG)
            .idMember(UPDATED_ID_MEMBER);

        restSmsHistoryMockMvc.perform(put("/api/sms-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSmsHistory)))
            .andExpect(status().isOk());

        // Validate the SmsHistory in the database
        List<SmsHistory> smsHistoryList = smsHistoryRepository.findAll();
        assertThat(smsHistoryList).hasSize(databaseSizeBeforeUpdate);
        SmsHistory testSmsHistory = smsHistoryList.get(smsHistoryList.size() - 1);
        assertThat(testSmsHistory.getTglInput()).isEqualTo(UPDATED_TGL_INPUT);
        assertThat(testSmsHistory.getNoHp()).isEqualTo(UPDATED_NO_HP);
        assertThat(testSmsHistory.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testSmsHistory.getPesan()).isEqualTo(UPDATED_PESAN);
        assertThat(testSmsHistory.getTipe()).isEqualTo(UPDATED_TIPE);
        assertThat(testSmsHistory.getTglSms()).isEqualTo(UPDATED_TGL_SMS);
        assertThat(testSmsHistory.getCom()).isEqualTo(UPDATED_COM);
        assertThat(testSmsHistory.getReport()).isEqualTo(UPDATED_REPORT);
        assertThat(testSmsHistory.getTrx()).isEqualTo(UPDATED_TRX);
        assertThat(testSmsHistory.getPosting()).isEqualTo(UPDATED_POSTING);
        assertThat(testSmsHistory.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testSmsHistory.getMsisdn()).isEqualTo(UPDATED_MSISDN);
        assertThat(testSmsHistory.getEnginename()).isEqualTo(UPDATED_ENGINENAME);
        assertThat(testSmsHistory.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testSmsHistory.getTypemsg()).isEqualTo(UPDATED_TYPEMSG);
        assertThat(testSmsHistory.getIdMember()).isEqualTo(UPDATED_ID_MEMBER);

        // Validate the SmsHistory in Elasticsearch
        SmsHistory smsHistoryEs = smsHistorySearchRepository.findOne(testSmsHistory.getId());
        assertThat(smsHistoryEs).isEqualToComparingFieldByField(testSmsHistory);
    }

    @Test
    @Transactional
    public void updateNonExistingSmsHistory() throws Exception {
        int databaseSizeBeforeUpdate = smsHistoryRepository.findAll().size();

        // Create the SmsHistory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSmsHistoryMockMvc.perform(put("/api/sms-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsHistory)))
            .andExpect(status().isCreated());

        // Validate the SmsHistory in the database
        List<SmsHistory> smsHistoryList = smsHistoryRepository.findAll();
        assertThat(smsHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSmsHistory() throws Exception {
        // Initialize the database
        smsHistoryService.save(smsHistory);

        int databaseSizeBeforeDelete = smsHistoryRepository.findAll().size();

        // Get the smsHistory
        restSmsHistoryMockMvc.perform(delete("/api/sms-histories/{id}", smsHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean smsHistoryExistsInEs = smsHistorySearchRepository.exists(smsHistory.getId());
        assertThat(smsHistoryExistsInEs).isFalse();

        // Validate the database is empty
        List<SmsHistory> smsHistoryList = smsHistoryRepository.findAll();
        assertThat(smsHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSmsHistory() throws Exception {
        // Initialize the database
        smsHistoryService.save(smsHistory);

        // Search the smsHistory
        restSmsHistoryMockMvc.perform(get("/api/_search/sms-histories?query=id:" + smsHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(smsHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].tglInput").value(hasItem(sameInstant(DEFAULT_TGL_INPUT))))
            .andExpect(jsonPath("$.[*].noHp").value(hasItem(DEFAULT_NO_HP.toString())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].pesan").value(hasItem(DEFAULT_PESAN.toString())))
            .andExpect(jsonPath("$.[*].tipe").value(hasItem(DEFAULT_TIPE)))
            .andExpect(jsonPath("$.[*].tglSms").value(hasItem(sameInstant(DEFAULT_TGL_SMS))))
            .andExpect(jsonPath("$.[*].com").value(hasItem(DEFAULT_COM.toString())))
            .andExpect(jsonPath("$.[*].report").value(hasItem(DEFAULT_REPORT)))
            .andExpect(jsonPath("$.[*].trx").value(hasItem(DEFAULT_TRX)))
            .andExpect(jsonPath("$.[*].posting").value(hasItem(DEFAULT_POSTING)))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF.toString())))
            .andExpect(jsonPath("$.[*].msisdn").value(hasItem(DEFAULT_MSISDN.toString())))
            .andExpect(jsonPath("$.[*].enginename").value(hasItem(DEFAULT_ENGINENAME.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].typemsg").value(hasItem(DEFAULT_TYPEMSG)))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SmsHistory.class);
        SmsHistory smsHistory1 = new SmsHistory();
        smsHistory1.setId(1L);
        SmsHistory smsHistory2 = new SmsHistory();
        smsHistory2.setId(smsHistory1.getId());
        assertThat(smsHistory1).isEqualTo(smsHistory2);
        smsHistory2.setId(2L);
        assertThat(smsHistory1).isNotEqualTo(smsHistory2);
        smsHistory1.setId(null);
        assertThat(smsHistory1).isNotEqualTo(smsHistory2);
    }
}
