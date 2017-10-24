package com.ib.web.rest;

import com.ib.AyoappApp;

import com.ib.domain.Distributor;
import com.ib.repository.DistributorRepository;
import com.ib.service.DistributorService;
import com.ib.repository.search.DistributorSearchRepository;
import com.ib.web.rest.errors.ExceptionTranslator;
import com.ib.service.dto.DistributorCriteria;
import com.ib.service.DistributorQueryService;

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
 * Test class for the DistributorResource REST controller.
 *
 * @see DistributorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AyoappApp.class)
public class DistributorResourceIntTest {

    private static final Long DEFAULT_ID_DISTRIBUTOR = 1L;
    private static final Long UPDATED_ID_DISTRIBUTOR = 2L;

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final String DEFAULT_KODE_ID = "AAAAAAAAAA";
    private static final String UPDATED_KODE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PIN = "AAAAAAAAAA";
    private static final String UPDATED_PIN = "BBBBBBBBBB";

    private static final String DEFAULT_COM = "AAAAAAAAAA";
    private static final String UPDATED_COM = "BBBBBBBBBB";

    private static final String DEFAULT_NO_KONTAK = "AAAAAAAAAA";
    private static final String UPDATED_NO_KONTAK = "BBBBBBBBBB";

    private static final String DEFAULT_METODE = "AAAAAAAAAA";
    private static final String UPDATED_METODE = "BBBBBBBBBB";

    private static final String DEFAULT_KODE_PARSING = "AAAAAAAAAA";
    private static final String UPDATED_KODE_PARSING = "BBBBBBBBBB";

    private static final String DEFAULT_KODE_PARSING_2 = "AAAAAAAAAA";
    private static final String UPDATED_KODE_PARSING_2 = "BBBBBBBBBB";

    private static final String DEFAULT_REPLYNO = "AAAAAAAAAA";
    private static final String UPDATED_REPLYNO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TGL_INPUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TGL_INPUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_USER_INPUT = "AAAAAAAAAA";
    private static final String UPDATED_USER_INPUT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TGL_UPDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TGL_UPDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_USER_UPDATE = "AAAAAAAAAA";
    private static final String UPDATED_USER_UPDATE = "BBBBBBBBBB";

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final Integer DEFAULT_ISVRE = 1;
    private static final Integer UPDATED_ISVRE = 2;

    private static final Integer DEFAULT_ISGTW = 1;
    private static final Integer UPDATED_ISGTW = 2;

    private static final String DEFAULT_UGTW = "AAAAAAAAAA";
    private static final String UPDATED_UGTW = "BBBBBBBBBB";

    private static final Integer DEFAULT_ISFILTER = 1;
    private static final Integer UPDATED_ISFILTER = 2;

    private static final String DEFAULT_PARSE_SALDO = "AAAAAAAAAA";
    private static final String UPDATED_PARSE_SALDO = "BBBBBBBBBB";

    private static final String DEFAULT_PARSE_HARGA = "AAAAAAAAAA";
    private static final String UPDATED_PARSE_HARGA = "BBBBBBBBBB";

    private static final String DEFAULT_TIKET_WRAP = "AAAAAAAAAA";
    private static final String UPDATED_TIKET_WRAP = "BBBBBBBBBB";

    private static final Integer DEFAULT_ISTIKETSEND = 1;
    private static final Integer UPDATED_ISTIKETSEND = 2;

    private static final String DEFAULT_PESAN_TIKET = "AAAAAAAAAA";
    private static final String UPDATED_PESAN_TIKET = "BBBBBBBBBB";

    private static final Integer DEFAULT_SALDO_SUPWARN = 1;
    private static final Integer UPDATED_SALDO_SUPWARN = 2;

    private static final Integer DEFAULT_ISSORTBY = 1;
    private static final Integer UPDATED_ISSORTBY = 2;

    private static final String DEFAULT_PARSE_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_PARSE_UNIT = "BBBBBBBBBB";

    private static final Integer DEFAULT_ISULANGIM = 1;
    private static final Integer UPDATED_ISULANGIM = 2;

    private static final Integer DEFAULT_ISHLR = 1;
    private static final Integer UPDATED_ISHLR = 2;

    private static final String DEFAULT_KODE_PARSING_3 = "AAAAAAAAAA";
    private static final String UPDATED_KODE_PARSING_3 = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_HISTORY = 1L;
    private static final Long UPDATED_ID_HISTORY = 2L;

    private static final String DEFAULT_KODE_PARSING_4 = "AAAAAAAAAA";
    private static final String UPDATED_KODE_PARSING_4 = "BBBBBBBBBB";

    private static final Integer DEFAULT_SELISIH_SUPWARN = 1;
    private static final Integer UPDATED_SELISIH_SUPWARN = 2;

    private static final String DEFAULT_TIMEON = "AAAAAAAAAA";
    private static final String UPDATED_TIMEON = "BBBBBBBBBB";

    private static final String DEFAULT_TIMEOFF = "AAAAAAAAAA";
    private static final String UPDATED_TIMEOFF = "BBBBBBBBBB";

    @Autowired
    private DistributorRepository distributorRepository;

    @Autowired
    private DistributorService distributorService;

    @Autowired
    private DistributorSearchRepository distributorSearchRepository;

    @Autowired
    private DistributorQueryService distributorQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDistributorMockMvc;

    private Distributor distributor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DistributorResource distributorResource = new DistributorResource(distributorService, distributorQueryService);
        this.restDistributorMockMvc = MockMvcBuilders.standaloneSetup(distributorResource)
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
    public static Distributor createEntity(EntityManager em) {
        Distributor distributor = new Distributor()
            .idDistributor(DEFAULT_ID_DISTRIBUTOR)
            .nama(DEFAULT_NAMA)
            .status(DEFAULT_STATUS)
            .kodeId(DEFAULT_KODE_ID)
            .pin(DEFAULT_PIN)
            .com(DEFAULT_COM)
            .noKontak(DEFAULT_NO_KONTAK)
            .metode(DEFAULT_METODE)
            .kodeParsing(DEFAULT_KODE_PARSING)
            .kodeParsing2(DEFAULT_KODE_PARSING_2)
            .replyno(DEFAULT_REPLYNO)
            .tglInput(DEFAULT_TGL_INPUT)
            .userInput(DEFAULT_USER_INPUT)
            .tglUpdate(DEFAULT_TGL_UPDATE)
            .userUpdate(DEFAULT_USER_UPDATE)
            .ip(DEFAULT_IP)
            .isvre(DEFAULT_ISVRE)
            .isgtw(DEFAULT_ISGTW)
            .ugtw(DEFAULT_UGTW)
            .isfilter(DEFAULT_ISFILTER)
            .parseSaldo(DEFAULT_PARSE_SALDO)
            .parseHarga(DEFAULT_PARSE_HARGA)
            .tiketWrap(DEFAULT_TIKET_WRAP)
            .istiketsend(DEFAULT_ISTIKETSEND)
            .pesanTiket(DEFAULT_PESAN_TIKET)
            .saldoSupwarn(DEFAULT_SALDO_SUPWARN)
            .issortby(DEFAULT_ISSORTBY)
            .parseUnit(DEFAULT_PARSE_UNIT)
            .isulangim(DEFAULT_ISULANGIM)
            .ishlr(DEFAULT_ISHLR)
            .kodeParsing3(DEFAULT_KODE_PARSING_3)
            .idHistory(DEFAULT_ID_HISTORY)
            .kodeParsing4(DEFAULT_KODE_PARSING_4)
            .selisihSupwarn(DEFAULT_SELISIH_SUPWARN)
            .timeon(DEFAULT_TIMEON)
            .timeoff(DEFAULT_TIMEOFF);
        return distributor;
    }

    @Before
    public void initTest() {
        distributorSearchRepository.deleteAll();
        distributor = createEntity(em);
    }

    @Test
    @Transactional
    public void createDistributor() throws Exception {
        int databaseSizeBeforeCreate = distributorRepository.findAll().size();

        // Create the Distributor
        restDistributorMockMvc.perform(post("/api/distributors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(distributor)))
            .andExpect(status().isCreated());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeCreate + 1);
        Distributor testDistributor = distributorList.get(distributorList.size() - 1);
        assertThat(testDistributor.getIdDistributor()).isEqualTo(DEFAULT_ID_DISTRIBUTOR);
        assertThat(testDistributor.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testDistributor.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDistributor.getKodeId()).isEqualTo(DEFAULT_KODE_ID);
        assertThat(testDistributor.getPin()).isEqualTo(DEFAULT_PIN);
        assertThat(testDistributor.getCom()).isEqualTo(DEFAULT_COM);
        assertThat(testDistributor.getNoKontak()).isEqualTo(DEFAULT_NO_KONTAK);
        assertThat(testDistributor.getMetode()).isEqualTo(DEFAULT_METODE);
        assertThat(testDistributor.getKodeParsing()).isEqualTo(DEFAULT_KODE_PARSING);
        assertThat(testDistributor.getKodeParsing2()).isEqualTo(DEFAULT_KODE_PARSING_2);
        assertThat(testDistributor.getReplyno()).isEqualTo(DEFAULT_REPLYNO);
        assertThat(testDistributor.getTglInput()).isEqualTo(DEFAULT_TGL_INPUT);
        assertThat(testDistributor.getUserInput()).isEqualTo(DEFAULT_USER_INPUT);
        assertThat(testDistributor.getTglUpdate()).isEqualTo(DEFAULT_TGL_UPDATE);
        assertThat(testDistributor.getUserUpdate()).isEqualTo(DEFAULT_USER_UPDATE);
        assertThat(testDistributor.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testDistributor.getIsvre()).isEqualTo(DEFAULT_ISVRE);
        assertThat(testDistributor.getIsgtw()).isEqualTo(DEFAULT_ISGTW);
        assertThat(testDistributor.getUgtw()).isEqualTo(DEFAULT_UGTW);
        assertThat(testDistributor.getIsfilter()).isEqualTo(DEFAULT_ISFILTER);
        assertThat(testDistributor.getParseSaldo()).isEqualTo(DEFAULT_PARSE_SALDO);
        assertThat(testDistributor.getParseHarga()).isEqualTo(DEFAULT_PARSE_HARGA);
        assertThat(testDistributor.getTiketWrap()).isEqualTo(DEFAULT_TIKET_WRAP);
        assertThat(testDistributor.getIstiketsend()).isEqualTo(DEFAULT_ISTIKETSEND);
        assertThat(testDistributor.getPesanTiket()).isEqualTo(DEFAULT_PESAN_TIKET);
        assertThat(testDistributor.getSaldoSupwarn()).isEqualTo(DEFAULT_SALDO_SUPWARN);
        assertThat(testDistributor.getIssortby()).isEqualTo(DEFAULT_ISSORTBY);
        assertThat(testDistributor.getParseUnit()).isEqualTo(DEFAULT_PARSE_UNIT);
        assertThat(testDistributor.getIsulangim()).isEqualTo(DEFAULT_ISULANGIM);
        assertThat(testDistributor.getIshlr()).isEqualTo(DEFAULT_ISHLR);
        assertThat(testDistributor.getKodeParsing3()).isEqualTo(DEFAULT_KODE_PARSING_3);
        assertThat(testDistributor.getIdHistory()).isEqualTo(DEFAULT_ID_HISTORY);
        assertThat(testDistributor.getKodeParsing4()).isEqualTo(DEFAULT_KODE_PARSING_4);
        assertThat(testDistributor.getSelisihSupwarn()).isEqualTo(DEFAULT_SELISIH_SUPWARN);
        assertThat(testDistributor.getTimeon()).isEqualTo(DEFAULT_TIMEON);
        assertThat(testDistributor.getTimeoff()).isEqualTo(DEFAULT_TIMEOFF);

        // Validate the Distributor in Elasticsearch
        Distributor distributorEs = distributorSearchRepository.findOne(testDistributor.getId());
        assertThat(distributorEs).isEqualToComparingFieldByField(testDistributor);
    }

    @Test
    @Transactional
    public void createDistributorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = distributorRepository.findAll().size();

        // Create the Distributor with an existing ID
        distributor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistributorMockMvc.perform(post("/api/distributors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(distributor)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDistributors() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList
        restDistributorMockMvc.perform(get("/api/distributors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(distributor.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDistributor").value(hasItem(DEFAULT_ID_DISTRIBUTOR.intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].kodeId").value(hasItem(DEFAULT_KODE_ID.toString())))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN.toString())))
            .andExpect(jsonPath("$.[*].com").value(hasItem(DEFAULT_COM.toString())))
            .andExpect(jsonPath("$.[*].noKontak").value(hasItem(DEFAULT_NO_KONTAK.toString())))
            .andExpect(jsonPath("$.[*].metode").value(hasItem(DEFAULT_METODE.toString())))
            .andExpect(jsonPath("$.[*].kodeParsing").value(hasItem(DEFAULT_KODE_PARSING.toString())))
            .andExpect(jsonPath("$.[*].kodeParsing2").value(hasItem(DEFAULT_KODE_PARSING_2.toString())))
            .andExpect(jsonPath("$.[*].replyno").value(hasItem(DEFAULT_REPLYNO.toString())))
            .andExpect(jsonPath("$.[*].tglInput").value(hasItem(sameInstant(DEFAULT_TGL_INPUT))))
            .andExpect(jsonPath("$.[*].userInput").value(hasItem(DEFAULT_USER_INPUT.toString())))
            .andExpect(jsonPath("$.[*].tglUpdate").value(hasItem(sameInstant(DEFAULT_TGL_UPDATE))))
            .andExpect(jsonPath("$.[*].userUpdate").value(hasItem(DEFAULT_USER_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].isvre").value(hasItem(DEFAULT_ISVRE)))
            .andExpect(jsonPath("$.[*].isgtw").value(hasItem(DEFAULT_ISGTW)))
            .andExpect(jsonPath("$.[*].ugtw").value(hasItem(DEFAULT_UGTW.toString())))
            .andExpect(jsonPath("$.[*].isfilter").value(hasItem(DEFAULT_ISFILTER)))
            .andExpect(jsonPath("$.[*].parseSaldo").value(hasItem(DEFAULT_PARSE_SALDO.toString())))
            .andExpect(jsonPath("$.[*].parseHarga").value(hasItem(DEFAULT_PARSE_HARGA.toString())))
            .andExpect(jsonPath("$.[*].tiketWrap").value(hasItem(DEFAULT_TIKET_WRAP.toString())))
            .andExpect(jsonPath("$.[*].istiketsend").value(hasItem(DEFAULT_ISTIKETSEND)))
            .andExpect(jsonPath("$.[*].pesanTiket").value(hasItem(DEFAULT_PESAN_TIKET.toString())))
            .andExpect(jsonPath("$.[*].saldoSupwarn").value(hasItem(DEFAULT_SALDO_SUPWARN)))
            .andExpect(jsonPath("$.[*].issortby").value(hasItem(DEFAULT_ISSORTBY)))
            .andExpect(jsonPath("$.[*].parseUnit").value(hasItem(DEFAULT_PARSE_UNIT.toString())))
            .andExpect(jsonPath("$.[*].isulangim").value(hasItem(DEFAULT_ISULANGIM)))
            .andExpect(jsonPath("$.[*].ishlr").value(hasItem(DEFAULT_ISHLR)))
            .andExpect(jsonPath("$.[*].kodeParsing3").value(hasItem(DEFAULT_KODE_PARSING_3.toString())))
            .andExpect(jsonPath("$.[*].idHistory").value(hasItem(DEFAULT_ID_HISTORY.intValue())))
            .andExpect(jsonPath("$.[*].kodeParsing4").value(hasItem(DEFAULT_KODE_PARSING_4.toString())))
            .andExpect(jsonPath("$.[*].selisihSupwarn").value(hasItem(DEFAULT_SELISIH_SUPWARN)))
            .andExpect(jsonPath("$.[*].timeon").value(hasItem(DEFAULT_TIMEON.toString())))
            .andExpect(jsonPath("$.[*].timeoff").value(hasItem(DEFAULT_TIMEOFF.toString())));
    }

    @Test
    @Transactional
    public void getDistributor() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get the distributor
        restDistributorMockMvc.perform(get("/api/distributors/{id}", distributor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(distributor.getId().intValue()))
            .andExpect(jsonPath("$.idDistributor").value(DEFAULT_ID_DISTRIBUTOR.intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.kodeId").value(DEFAULT_KODE_ID.toString()))
            .andExpect(jsonPath("$.pin").value(DEFAULT_PIN.toString()))
            .andExpect(jsonPath("$.com").value(DEFAULT_COM.toString()))
            .andExpect(jsonPath("$.noKontak").value(DEFAULT_NO_KONTAK.toString()))
            .andExpect(jsonPath("$.metode").value(DEFAULT_METODE.toString()))
            .andExpect(jsonPath("$.kodeParsing").value(DEFAULT_KODE_PARSING.toString()))
            .andExpect(jsonPath("$.kodeParsing2").value(DEFAULT_KODE_PARSING_2.toString()))
            .andExpect(jsonPath("$.replyno").value(DEFAULT_REPLYNO.toString()))
            .andExpect(jsonPath("$.tglInput").value(sameInstant(DEFAULT_TGL_INPUT)))
            .andExpect(jsonPath("$.userInput").value(DEFAULT_USER_INPUT.toString()))
            .andExpect(jsonPath("$.tglUpdate").value(sameInstant(DEFAULT_TGL_UPDATE)))
            .andExpect(jsonPath("$.userUpdate").value(DEFAULT_USER_UPDATE.toString()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP.toString()))
            .andExpect(jsonPath("$.isvre").value(DEFAULT_ISVRE))
            .andExpect(jsonPath("$.isgtw").value(DEFAULT_ISGTW))
            .andExpect(jsonPath("$.ugtw").value(DEFAULT_UGTW.toString()))
            .andExpect(jsonPath("$.isfilter").value(DEFAULT_ISFILTER))
            .andExpect(jsonPath("$.parseSaldo").value(DEFAULT_PARSE_SALDO.toString()))
            .andExpect(jsonPath("$.parseHarga").value(DEFAULT_PARSE_HARGA.toString()))
            .andExpect(jsonPath("$.tiketWrap").value(DEFAULT_TIKET_WRAP.toString()))
            .andExpect(jsonPath("$.istiketsend").value(DEFAULT_ISTIKETSEND))
            .andExpect(jsonPath("$.pesanTiket").value(DEFAULT_PESAN_TIKET.toString()))
            .andExpect(jsonPath("$.saldoSupwarn").value(DEFAULT_SALDO_SUPWARN))
            .andExpect(jsonPath("$.issortby").value(DEFAULT_ISSORTBY))
            .andExpect(jsonPath("$.parseUnit").value(DEFAULT_PARSE_UNIT.toString()))
            .andExpect(jsonPath("$.isulangim").value(DEFAULT_ISULANGIM))
            .andExpect(jsonPath("$.ishlr").value(DEFAULT_ISHLR))
            .andExpect(jsonPath("$.kodeParsing3").value(DEFAULT_KODE_PARSING_3.toString()))
            .andExpect(jsonPath("$.idHistory").value(DEFAULT_ID_HISTORY.intValue()))
            .andExpect(jsonPath("$.kodeParsing4").value(DEFAULT_KODE_PARSING_4.toString()))
            .andExpect(jsonPath("$.selisihSupwarn").value(DEFAULT_SELISIH_SUPWARN))
            .andExpect(jsonPath("$.timeon").value(DEFAULT_TIMEON.toString()))
            .andExpect(jsonPath("$.timeoff").value(DEFAULT_TIMEOFF.toString()));
    }

    @Test
    @Transactional
    public void getAllDistributorsByIdDistributorIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where idDistributor equals to DEFAULT_ID_DISTRIBUTOR
        defaultDistributorShouldBeFound("idDistributor.equals=" + DEFAULT_ID_DISTRIBUTOR);

        // Get all the distributorList where idDistributor equals to UPDATED_ID_DISTRIBUTOR
        defaultDistributorShouldNotBeFound("idDistributor.equals=" + UPDATED_ID_DISTRIBUTOR);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIdDistributorIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where idDistributor in DEFAULT_ID_DISTRIBUTOR or UPDATED_ID_DISTRIBUTOR
        defaultDistributorShouldBeFound("idDistributor.in=" + DEFAULT_ID_DISTRIBUTOR + "," + UPDATED_ID_DISTRIBUTOR);

        // Get all the distributorList where idDistributor equals to UPDATED_ID_DISTRIBUTOR
        defaultDistributorShouldNotBeFound("idDistributor.in=" + UPDATED_ID_DISTRIBUTOR);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIdDistributorIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where idDistributor is not null
        defaultDistributorShouldBeFound("idDistributor.specified=true");

        // Get all the distributorList where idDistributor is null
        defaultDistributorShouldNotBeFound("idDistributor.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByIdDistributorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where idDistributor greater than or equals to DEFAULT_ID_DISTRIBUTOR
        defaultDistributorShouldBeFound("idDistributor.greaterOrEqualThan=" + DEFAULT_ID_DISTRIBUTOR);

        // Get all the distributorList where idDistributor greater than or equals to UPDATED_ID_DISTRIBUTOR
        defaultDistributorShouldNotBeFound("idDistributor.greaterOrEqualThan=" + UPDATED_ID_DISTRIBUTOR);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIdDistributorIsLessThanSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where idDistributor less than or equals to DEFAULT_ID_DISTRIBUTOR
        defaultDistributorShouldNotBeFound("idDistributor.lessThan=" + DEFAULT_ID_DISTRIBUTOR);

        // Get all the distributorList where idDistributor less than or equals to UPDATED_ID_DISTRIBUTOR
        defaultDistributorShouldBeFound("idDistributor.lessThan=" + UPDATED_ID_DISTRIBUTOR);
    }


    @Test
    @Transactional
    public void getAllDistributorsByNamaIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where nama equals to DEFAULT_NAMA
        defaultDistributorShouldBeFound("nama.equals=" + DEFAULT_NAMA);

        // Get all the distributorList where nama equals to UPDATED_NAMA
        defaultDistributorShouldNotBeFound("nama.equals=" + UPDATED_NAMA);
    }

    @Test
    @Transactional
    public void getAllDistributorsByNamaIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where nama in DEFAULT_NAMA or UPDATED_NAMA
        defaultDistributorShouldBeFound("nama.in=" + DEFAULT_NAMA + "," + UPDATED_NAMA);

        // Get all the distributorList where nama equals to UPDATED_NAMA
        defaultDistributorShouldNotBeFound("nama.in=" + UPDATED_NAMA);
    }

    @Test
    @Transactional
    public void getAllDistributorsByNamaIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where nama is not null
        defaultDistributorShouldBeFound("nama.specified=true");

        // Get all the distributorList where nama is null
        defaultDistributorShouldNotBeFound("nama.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where status equals to DEFAULT_STATUS
        defaultDistributorShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the distributorList where status equals to UPDATED_STATUS
        defaultDistributorShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllDistributorsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultDistributorShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the distributorList where status equals to UPDATED_STATUS
        defaultDistributorShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllDistributorsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where status is not null
        defaultDistributorShouldBeFound("status.specified=true");

        // Get all the distributorList where status is null
        defaultDistributorShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where status greater than or equals to DEFAULT_STATUS
        defaultDistributorShouldBeFound("status.greaterOrEqualThan=" + DEFAULT_STATUS);

        // Get all the distributorList where status greater than or equals to UPDATED_STATUS
        defaultDistributorShouldNotBeFound("status.greaterOrEqualThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllDistributorsByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where status less than or equals to DEFAULT_STATUS
        defaultDistributorShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the distributorList where status less than or equals to UPDATED_STATUS
        defaultDistributorShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllDistributorsByKodeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where kodeId equals to DEFAULT_KODE_ID
        defaultDistributorShouldBeFound("kodeId.equals=" + DEFAULT_KODE_ID);

        // Get all the distributorList where kodeId equals to UPDATED_KODE_ID
        defaultDistributorShouldNotBeFound("kodeId.equals=" + UPDATED_KODE_ID);
    }

    @Test
    @Transactional
    public void getAllDistributorsByKodeIdIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where kodeId in DEFAULT_KODE_ID or UPDATED_KODE_ID
        defaultDistributorShouldBeFound("kodeId.in=" + DEFAULT_KODE_ID + "," + UPDATED_KODE_ID);

        // Get all the distributorList where kodeId equals to UPDATED_KODE_ID
        defaultDistributorShouldNotBeFound("kodeId.in=" + UPDATED_KODE_ID);
    }

    @Test
    @Transactional
    public void getAllDistributorsByKodeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where kodeId is not null
        defaultDistributorShouldBeFound("kodeId.specified=true");

        // Get all the distributorList where kodeId is null
        defaultDistributorShouldNotBeFound("kodeId.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByPinIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where pin equals to DEFAULT_PIN
        defaultDistributorShouldBeFound("pin.equals=" + DEFAULT_PIN);

        // Get all the distributorList where pin equals to UPDATED_PIN
        defaultDistributorShouldNotBeFound("pin.equals=" + UPDATED_PIN);
    }

    @Test
    @Transactional
    public void getAllDistributorsByPinIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where pin in DEFAULT_PIN or UPDATED_PIN
        defaultDistributorShouldBeFound("pin.in=" + DEFAULT_PIN + "," + UPDATED_PIN);

        // Get all the distributorList where pin equals to UPDATED_PIN
        defaultDistributorShouldNotBeFound("pin.in=" + UPDATED_PIN);
    }

    @Test
    @Transactional
    public void getAllDistributorsByPinIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where pin is not null
        defaultDistributorShouldBeFound("pin.specified=true");

        // Get all the distributorList where pin is null
        defaultDistributorShouldNotBeFound("pin.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByComIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where com equals to DEFAULT_COM
        defaultDistributorShouldBeFound("com.equals=" + DEFAULT_COM);

        // Get all the distributorList where com equals to UPDATED_COM
        defaultDistributorShouldNotBeFound("com.equals=" + UPDATED_COM);
    }

    @Test
    @Transactional
    public void getAllDistributorsByComIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where com in DEFAULT_COM or UPDATED_COM
        defaultDistributorShouldBeFound("com.in=" + DEFAULT_COM + "," + UPDATED_COM);

        // Get all the distributorList where com equals to UPDATED_COM
        defaultDistributorShouldNotBeFound("com.in=" + UPDATED_COM);
    }

    @Test
    @Transactional
    public void getAllDistributorsByComIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where com is not null
        defaultDistributorShouldBeFound("com.specified=true");

        // Get all the distributorList where com is null
        defaultDistributorShouldNotBeFound("com.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByNoKontakIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where noKontak equals to DEFAULT_NO_KONTAK
        defaultDistributorShouldBeFound("noKontak.equals=" + DEFAULT_NO_KONTAK);

        // Get all the distributorList where noKontak equals to UPDATED_NO_KONTAK
        defaultDistributorShouldNotBeFound("noKontak.equals=" + UPDATED_NO_KONTAK);
    }

    @Test
    @Transactional
    public void getAllDistributorsByNoKontakIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where noKontak in DEFAULT_NO_KONTAK or UPDATED_NO_KONTAK
        defaultDistributorShouldBeFound("noKontak.in=" + DEFAULT_NO_KONTAK + "," + UPDATED_NO_KONTAK);

        // Get all the distributorList where noKontak equals to UPDATED_NO_KONTAK
        defaultDistributorShouldNotBeFound("noKontak.in=" + UPDATED_NO_KONTAK);
    }

    @Test
    @Transactional
    public void getAllDistributorsByNoKontakIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where noKontak is not null
        defaultDistributorShouldBeFound("noKontak.specified=true");

        // Get all the distributorList where noKontak is null
        defaultDistributorShouldNotBeFound("noKontak.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByMetodeIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where metode equals to DEFAULT_METODE
        defaultDistributorShouldBeFound("metode.equals=" + DEFAULT_METODE);

        // Get all the distributorList where metode equals to UPDATED_METODE
        defaultDistributorShouldNotBeFound("metode.equals=" + UPDATED_METODE);
    }

    @Test
    @Transactional
    public void getAllDistributorsByMetodeIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where metode in DEFAULT_METODE or UPDATED_METODE
        defaultDistributorShouldBeFound("metode.in=" + DEFAULT_METODE + "," + UPDATED_METODE);

        // Get all the distributorList where metode equals to UPDATED_METODE
        defaultDistributorShouldNotBeFound("metode.in=" + UPDATED_METODE);
    }

    @Test
    @Transactional
    public void getAllDistributorsByMetodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where metode is not null
        defaultDistributorShouldBeFound("metode.specified=true");

        // Get all the distributorList where metode is null
        defaultDistributorShouldNotBeFound("metode.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByKodeParsingIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where kodeParsing equals to DEFAULT_KODE_PARSING
        defaultDistributorShouldBeFound("kodeParsing.equals=" + DEFAULT_KODE_PARSING);

        // Get all the distributorList where kodeParsing equals to UPDATED_KODE_PARSING
        defaultDistributorShouldNotBeFound("kodeParsing.equals=" + UPDATED_KODE_PARSING);
    }

    @Test
    @Transactional
    public void getAllDistributorsByKodeParsingIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where kodeParsing in DEFAULT_KODE_PARSING or UPDATED_KODE_PARSING
        defaultDistributorShouldBeFound("kodeParsing.in=" + DEFAULT_KODE_PARSING + "," + UPDATED_KODE_PARSING);

        // Get all the distributorList where kodeParsing equals to UPDATED_KODE_PARSING
        defaultDistributorShouldNotBeFound("kodeParsing.in=" + UPDATED_KODE_PARSING);
    }

    @Test
    @Transactional
    public void getAllDistributorsByKodeParsingIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where kodeParsing is not null
        defaultDistributorShouldBeFound("kodeParsing.specified=true");

        // Get all the distributorList where kodeParsing is null
        defaultDistributorShouldNotBeFound("kodeParsing.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByKodeParsing2IsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where kodeParsing2 equals to DEFAULT_KODE_PARSING_2
        defaultDistributorShouldBeFound("kodeParsing2.equals=" + DEFAULT_KODE_PARSING_2);

        // Get all the distributorList where kodeParsing2 equals to UPDATED_KODE_PARSING_2
        defaultDistributorShouldNotBeFound("kodeParsing2.equals=" + UPDATED_KODE_PARSING_2);
    }

    @Test
    @Transactional
    public void getAllDistributorsByKodeParsing2IsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where kodeParsing2 in DEFAULT_KODE_PARSING_2 or UPDATED_KODE_PARSING_2
        defaultDistributorShouldBeFound("kodeParsing2.in=" + DEFAULT_KODE_PARSING_2 + "," + UPDATED_KODE_PARSING_2);

        // Get all the distributorList where kodeParsing2 equals to UPDATED_KODE_PARSING_2
        defaultDistributorShouldNotBeFound("kodeParsing2.in=" + UPDATED_KODE_PARSING_2);
    }

    @Test
    @Transactional
    public void getAllDistributorsByKodeParsing2IsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where kodeParsing2 is not null
        defaultDistributorShouldBeFound("kodeParsing2.specified=true");

        // Get all the distributorList where kodeParsing2 is null
        defaultDistributorShouldNotBeFound("kodeParsing2.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByReplynoIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where replyno equals to DEFAULT_REPLYNO
        defaultDistributorShouldBeFound("replyno.equals=" + DEFAULT_REPLYNO);

        // Get all the distributorList where replyno equals to UPDATED_REPLYNO
        defaultDistributorShouldNotBeFound("replyno.equals=" + UPDATED_REPLYNO);
    }

    @Test
    @Transactional
    public void getAllDistributorsByReplynoIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where replyno in DEFAULT_REPLYNO or UPDATED_REPLYNO
        defaultDistributorShouldBeFound("replyno.in=" + DEFAULT_REPLYNO + "," + UPDATED_REPLYNO);

        // Get all the distributorList where replyno equals to UPDATED_REPLYNO
        defaultDistributorShouldNotBeFound("replyno.in=" + UPDATED_REPLYNO);
    }

    @Test
    @Transactional
    public void getAllDistributorsByReplynoIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where replyno is not null
        defaultDistributorShouldBeFound("replyno.specified=true");

        // Get all the distributorList where replyno is null
        defaultDistributorShouldNotBeFound("replyno.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByTglInputIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where tglInput equals to DEFAULT_TGL_INPUT
        defaultDistributorShouldBeFound("tglInput.equals=" + DEFAULT_TGL_INPUT);

        // Get all the distributorList where tglInput equals to UPDATED_TGL_INPUT
        defaultDistributorShouldNotBeFound("tglInput.equals=" + UPDATED_TGL_INPUT);
    }

    @Test
    @Transactional
    public void getAllDistributorsByTglInputIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where tglInput in DEFAULT_TGL_INPUT or UPDATED_TGL_INPUT
        defaultDistributorShouldBeFound("tglInput.in=" + DEFAULT_TGL_INPUT + "," + UPDATED_TGL_INPUT);

        // Get all the distributorList where tglInput equals to UPDATED_TGL_INPUT
        defaultDistributorShouldNotBeFound("tglInput.in=" + UPDATED_TGL_INPUT);
    }

    @Test
    @Transactional
    public void getAllDistributorsByTglInputIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where tglInput is not null
        defaultDistributorShouldBeFound("tglInput.specified=true");

        // Get all the distributorList where tglInput is null
        defaultDistributorShouldNotBeFound("tglInput.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByTglInputIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where tglInput greater than or equals to DEFAULT_TGL_INPUT
        defaultDistributorShouldBeFound("tglInput.greaterOrEqualThan=" + DEFAULT_TGL_INPUT);

        // Get all the distributorList where tglInput greater than or equals to UPDATED_TGL_INPUT
        defaultDistributorShouldNotBeFound("tglInput.greaterOrEqualThan=" + UPDATED_TGL_INPUT);
    }

    @Test
    @Transactional
    public void getAllDistributorsByTglInputIsLessThanSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where tglInput less than or equals to DEFAULT_TGL_INPUT
        defaultDistributorShouldNotBeFound("tglInput.lessThan=" + DEFAULT_TGL_INPUT);

        // Get all the distributorList where tglInput less than or equals to UPDATED_TGL_INPUT
        defaultDistributorShouldBeFound("tglInput.lessThan=" + UPDATED_TGL_INPUT);
    }


    @Test
    @Transactional
    public void getAllDistributorsByUserInputIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where userInput equals to DEFAULT_USER_INPUT
        defaultDistributorShouldBeFound("userInput.equals=" + DEFAULT_USER_INPUT);

        // Get all the distributorList where userInput equals to UPDATED_USER_INPUT
        defaultDistributorShouldNotBeFound("userInput.equals=" + UPDATED_USER_INPUT);
    }

    @Test
    @Transactional
    public void getAllDistributorsByUserInputIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where userInput in DEFAULT_USER_INPUT or UPDATED_USER_INPUT
        defaultDistributorShouldBeFound("userInput.in=" + DEFAULT_USER_INPUT + "," + UPDATED_USER_INPUT);

        // Get all the distributorList where userInput equals to UPDATED_USER_INPUT
        defaultDistributorShouldNotBeFound("userInput.in=" + UPDATED_USER_INPUT);
    }

    @Test
    @Transactional
    public void getAllDistributorsByUserInputIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where userInput is not null
        defaultDistributorShouldBeFound("userInput.specified=true");

        // Get all the distributorList where userInput is null
        defaultDistributorShouldNotBeFound("userInput.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByTglUpdateIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where tglUpdate equals to DEFAULT_TGL_UPDATE
        defaultDistributorShouldBeFound("tglUpdate.equals=" + DEFAULT_TGL_UPDATE);

        // Get all the distributorList where tglUpdate equals to UPDATED_TGL_UPDATE
        defaultDistributorShouldNotBeFound("tglUpdate.equals=" + UPDATED_TGL_UPDATE);
    }

    @Test
    @Transactional
    public void getAllDistributorsByTglUpdateIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where tglUpdate in DEFAULT_TGL_UPDATE or UPDATED_TGL_UPDATE
        defaultDistributorShouldBeFound("tglUpdate.in=" + DEFAULT_TGL_UPDATE + "," + UPDATED_TGL_UPDATE);

        // Get all the distributorList where tglUpdate equals to UPDATED_TGL_UPDATE
        defaultDistributorShouldNotBeFound("tglUpdate.in=" + UPDATED_TGL_UPDATE);
    }

    @Test
    @Transactional
    public void getAllDistributorsByTglUpdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where tglUpdate is not null
        defaultDistributorShouldBeFound("tglUpdate.specified=true");

        // Get all the distributorList where tglUpdate is null
        defaultDistributorShouldNotBeFound("tglUpdate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByTglUpdateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where tglUpdate greater than or equals to DEFAULT_TGL_UPDATE
        defaultDistributorShouldBeFound("tglUpdate.greaterOrEqualThan=" + DEFAULT_TGL_UPDATE);

        // Get all the distributorList where tglUpdate greater than or equals to UPDATED_TGL_UPDATE
        defaultDistributorShouldNotBeFound("tglUpdate.greaterOrEqualThan=" + UPDATED_TGL_UPDATE);
    }

    @Test
    @Transactional
    public void getAllDistributorsByTglUpdateIsLessThanSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where tglUpdate less than or equals to DEFAULT_TGL_UPDATE
        defaultDistributorShouldNotBeFound("tglUpdate.lessThan=" + DEFAULT_TGL_UPDATE);

        // Get all the distributorList where tglUpdate less than or equals to UPDATED_TGL_UPDATE
        defaultDistributorShouldBeFound("tglUpdate.lessThan=" + UPDATED_TGL_UPDATE);
    }


    @Test
    @Transactional
    public void getAllDistributorsByUserUpdateIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where userUpdate equals to DEFAULT_USER_UPDATE
        defaultDistributorShouldBeFound("userUpdate.equals=" + DEFAULT_USER_UPDATE);

        // Get all the distributorList where userUpdate equals to UPDATED_USER_UPDATE
        defaultDistributorShouldNotBeFound("userUpdate.equals=" + UPDATED_USER_UPDATE);
    }

    @Test
    @Transactional
    public void getAllDistributorsByUserUpdateIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where userUpdate in DEFAULT_USER_UPDATE or UPDATED_USER_UPDATE
        defaultDistributorShouldBeFound("userUpdate.in=" + DEFAULT_USER_UPDATE + "," + UPDATED_USER_UPDATE);

        // Get all the distributorList where userUpdate equals to UPDATED_USER_UPDATE
        defaultDistributorShouldNotBeFound("userUpdate.in=" + UPDATED_USER_UPDATE);
    }

    @Test
    @Transactional
    public void getAllDistributorsByUserUpdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where userUpdate is not null
        defaultDistributorShouldBeFound("userUpdate.specified=true");

        // Get all the distributorList where userUpdate is null
        defaultDistributorShouldNotBeFound("userUpdate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByIpIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where ip equals to DEFAULT_IP
        defaultDistributorShouldBeFound("ip.equals=" + DEFAULT_IP);

        // Get all the distributorList where ip equals to UPDATED_IP
        defaultDistributorShouldNotBeFound("ip.equals=" + UPDATED_IP);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIpIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where ip in DEFAULT_IP or UPDATED_IP
        defaultDistributorShouldBeFound("ip.in=" + DEFAULT_IP + "," + UPDATED_IP);

        // Get all the distributorList where ip equals to UPDATED_IP
        defaultDistributorShouldNotBeFound("ip.in=" + UPDATED_IP);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIpIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where ip is not null
        defaultDistributorShouldBeFound("ip.specified=true");

        // Get all the distributorList where ip is null
        defaultDistributorShouldNotBeFound("ip.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsvreIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isvre equals to DEFAULT_ISVRE
        defaultDistributorShouldBeFound("isvre.equals=" + DEFAULT_ISVRE);

        // Get all the distributorList where isvre equals to UPDATED_ISVRE
        defaultDistributorShouldNotBeFound("isvre.equals=" + UPDATED_ISVRE);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsvreIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isvre in DEFAULT_ISVRE or UPDATED_ISVRE
        defaultDistributorShouldBeFound("isvre.in=" + DEFAULT_ISVRE + "," + UPDATED_ISVRE);

        // Get all the distributorList where isvre equals to UPDATED_ISVRE
        defaultDistributorShouldNotBeFound("isvre.in=" + UPDATED_ISVRE);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsvreIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isvre is not null
        defaultDistributorShouldBeFound("isvre.specified=true");

        // Get all the distributorList where isvre is null
        defaultDistributorShouldNotBeFound("isvre.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsvreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isvre greater than or equals to DEFAULT_ISVRE
        defaultDistributorShouldBeFound("isvre.greaterOrEqualThan=" + DEFAULT_ISVRE);

        // Get all the distributorList where isvre greater than or equals to UPDATED_ISVRE
        defaultDistributorShouldNotBeFound("isvre.greaterOrEqualThan=" + UPDATED_ISVRE);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsvreIsLessThanSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isvre less than or equals to DEFAULT_ISVRE
        defaultDistributorShouldNotBeFound("isvre.lessThan=" + DEFAULT_ISVRE);

        // Get all the distributorList where isvre less than or equals to UPDATED_ISVRE
        defaultDistributorShouldBeFound("isvre.lessThan=" + UPDATED_ISVRE);
    }


    @Test
    @Transactional
    public void getAllDistributorsByIsgtwIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isgtw equals to DEFAULT_ISGTW
        defaultDistributorShouldBeFound("isgtw.equals=" + DEFAULT_ISGTW);

        // Get all the distributorList where isgtw equals to UPDATED_ISGTW
        defaultDistributorShouldNotBeFound("isgtw.equals=" + UPDATED_ISGTW);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsgtwIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isgtw in DEFAULT_ISGTW or UPDATED_ISGTW
        defaultDistributorShouldBeFound("isgtw.in=" + DEFAULT_ISGTW + "," + UPDATED_ISGTW);

        // Get all the distributorList where isgtw equals to UPDATED_ISGTW
        defaultDistributorShouldNotBeFound("isgtw.in=" + UPDATED_ISGTW);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsgtwIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isgtw is not null
        defaultDistributorShouldBeFound("isgtw.specified=true");

        // Get all the distributorList where isgtw is null
        defaultDistributorShouldNotBeFound("isgtw.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsgtwIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isgtw greater than or equals to DEFAULT_ISGTW
        defaultDistributorShouldBeFound("isgtw.greaterOrEqualThan=" + DEFAULT_ISGTW);

        // Get all the distributorList where isgtw greater than or equals to UPDATED_ISGTW
        defaultDistributorShouldNotBeFound("isgtw.greaterOrEqualThan=" + UPDATED_ISGTW);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsgtwIsLessThanSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isgtw less than or equals to DEFAULT_ISGTW
        defaultDistributorShouldNotBeFound("isgtw.lessThan=" + DEFAULT_ISGTW);

        // Get all the distributorList where isgtw less than or equals to UPDATED_ISGTW
        defaultDistributorShouldBeFound("isgtw.lessThan=" + UPDATED_ISGTW);
    }


    @Test
    @Transactional
    public void getAllDistributorsByUgtwIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where ugtw equals to DEFAULT_UGTW
        defaultDistributorShouldBeFound("ugtw.equals=" + DEFAULT_UGTW);

        // Get all the distributorList where ugtw equals to UPDATED_UGTW
        defaultDistributorShouldNotBeFound("ugtw.equals=" + UPDATED_UGTW);
    }

    @Test
    @Transactional
    public void getAllDistributorsByUgtwIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where ugtw in DEFAULT_UGTW or UPDATED_UGTW
        defaultDistributorShouldBeFound("ugtw.in=" + DEFAULT_UGTW + "," + UPDATED_UGTW);

        // Get all the distributorList where ugtw equals to UPDATED_UGTW
        defaultDistributorShouldNotBeFound("ugtw.in=" + UPDATED_UGTW);
    }

    @Test
    @Transactional
    public void getAllDistributorsByUgtwIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where ugtw is not null
        defaultDistributorShouldBeFound("ugtw.specified=true");

        // Get all the distributorList where ugtw is null
        defaultDistributorShouldNotBeFound("ugtw.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsfilterIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isfilter equals to DEFAULT_ISFILTER
        defaultDistributorShouldBeFound("isfilter.equals=" + DEFAULT_ISFILTER);

        // Get all the distributorList where isfilter equals to UPDATED_ISFILTER
        defaultDistributorShouldNotBeFound("isfilter.equals=" + UPDATED_ISFILTER);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsfilterIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isfilter in DEFAULT_ISFILTER or UPDATED_ISFILTER
        defaultDistributorShouldBeFound("isfilter.in=" + DEFAULT_ISFILTER + "," + UPDATED_ISFILTER);

        // Get all the distributorList where isfilter equals to UPDATED_ISFILTER
        defaultDistributorShouldNotBeFound("isfilter.in=" + UPDATED_ISFILTER);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsfilterIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isfilter is not null
        defaultDistributorShouldBeFound("isfilter.specified=true");

        // Get all the distributorList where isfilter is null
        defaultDistributorShouldNotBeFound("isfilter.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsfilterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isfilter greater than or equals to DEFAULT_ISFILTER
        defaultDistributorShouldBeFound("isfilter.greaterOrEqualThan=" + DEFAULT_ISFILTER);

        // Get all the distributorList where isfilter greater than or equals to UPDATED_ISFILTER
        defaultDistributorShouldNotBeFound("isfilter.greaterOrEqualThan=" + UPDATED_ISFILTER);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsfilterIsLessThanSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isfilter less than or equals to DEFAULT_ISFILTER
        defaultDistributorShouldNotBeFound("isfilter.lessThan=" + DEFAULT_ISFILTER);

        // Get all the distributorList where isfilter less than or equals to UPDATED_ISFILTER
        defaultDistributorShouldBeFound("isfilter.lessThan=" + UPDATED_ISFILTER);
    }


    @Test
    @Transactional
    public void getAllDistributorsByParseSaldoIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where parseSaldo equals to DEFAULT_PARSE_SALDO
        defaultDistributorShouldBeFound("parseSaldo.equals=" + DEFAULT_PARSE_SALDO);

        // Get all the distributorList where parseSaldo equals to UPDATED_PARSE_SALDO
        defaultDistributorShouldNotBeFound("parseSaldo.equals=" + UPDATED_PARSE_SALDO);
    }

    @Test
    @Transactional
    public void getAllDistributorsByParseSaldoIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where parseSaldo in DEFAULT_PARSE_SALDO or UPDATED_PARSE_SALDO
        defaultDistributorShouldBeFound("parseSaldo.in=" + DEFAULT_PARSE_SALDO + "," + UPDATED_PARSE_SALDO);

        // Get all the distributorList where parseSaldo equals to UPDATED_PARSE_SALDO
        defaultDistributorShouldNotBeFound("parseSaldo.in=" + UPDATED_PARSE_SALDO);
    }

    @Test
    @Transactional
    public void getAllDistributorsByParseSaldoIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where parseSaldo is not null
        defaultDistributorShouldBeFound("parseSaldo.specified=true");

        // Get all the distributorList where parseSaldo is null
        defaultDistributorShouldNotBeFound("parseSaldo.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByParseHargaIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where parseHarga equals to DEFAULT_PARSE_HARGA
        defaultDistributorShouldBeFound("parseHarga.equals=" + DEFAULT_PARSE_HARGA);

        // Get all the distributorList where parseHarga equals to UPDATED_PARSE_HARGA
        defaultDistributorShouldNotBeFound("parseHarga.equals=" + UPDATED_PARSE_HARGA);
    }

    @Test
    @Transactional
    public void getAllDistributorsByParseHargaIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where parseHarga in DEFAULT_PARSE_HARGA or UPDATED_PARSE_HARGA
        defaultDistributorShouldBeFound("parseHarga.in=" + DEFAULT_PARSE_HARGA + "," + UPDATED_PARSE_HARGA);

        // Get all the distributorList where parseHarga equals to UPDATED_PARSE_HARGA
        defaultDistributorShouldNotBeFound("parseHarga.in=" + UPDATED_PARSE_HARGA);
    }

    @Test
    @Transactional
    public void getAllDistributorsByParseHargaIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where parseHarga is not null
        defaultDistributorShouldBeFound("parseHarga.specified=true");

        // Get all the distributorList where parseHarga is null
        defaultDistributorShouldNotBeFound("parseHarga.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByTiketWrapIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where tiketWrap equals to DEFAULT_TIKET_WRAP
        defaultDistributorShouldBeFound("tiketWrap.equals=" + DEFAULT_TIKET_WRAP);

        // Get all the distributorList where tiketWrap equals to UPDATED_TIKET_WRAP
        defaultDistributorShouldNotBeFound("tiketWrap.equals=" + UPDATED_TIKET_WRAP);
    }

    @Test
    @Transactional
    public void getAllDistributorsByTiketWrapIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where tiketWrap in DEFAULT_TIKET_WRAP or UPDATED_TIKET_WRAP
        defaultDistributorShouldBeFound("tiketWrap.in=" + DEFAULT_TIKET_WRAP + "," + UPDATED_TIKET_WRAP);

        // Get all the distributorList where tiketWrap equals to UPDATED_TIKET_WRAP
        defaultDistributorShouldNotBeFound("tiketWrap.in=" + UPDATED_TIKET_WRAP);
    }

    @Test
    @Transactional
    public void getAllDistributorsByTiketWrapIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where tiketWrap is not null
        defaultDistributorShouldBeFound("tiketWrap.specified=true");

        // Get all the distributorList where tiketWrap is null
        defaultDistributorShouldNotBeFound("tiketWrap.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByIstiketsendIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where istiketsend equals to DEFAULT_ISTIKETSEND
        defaultDistributorShouldBeFound("istiketsend.equals=" + DEFAULT_ISTIKETSEND);

        // Get all the distributorList where istiketsend equals to UPDATED_ISTIKETSEND
        defaultDistributorShouldNotBeFound("istiketsend.equals=" + UPDATED_ISTIKETSEND);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIstiketsendIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where istiketsend in DEFAULT_ISTIKETSEND or UPDATED_ISTIKETSEND
        defaultDistributorShouldBeFound("istiketsend.in=" + DEFAULT_ISTIKETSEND + "," + UPDATED_ISTIKETSEND);

        // Get all the distributorList where istiketsend equals to UPDATED_ISTIKETSEND
        defaultDistributorShouldNotBeFound("istiketsend.in=" + UPDATED_ISTIKETSEND);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIstiketsendIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where istiketsend is not null
        defaultDistributorShouldBeFound("istiketsend.specified=true");

        // Get all the distributorList where istiketsend is null
        defaultDistributorShouldNotBeFound("istiketsend.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByIstiketsendIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where istiketsend greater than or equals to DEFAULT_ISTIKETSEND
        defaultDistributorShouldBeFound("istiketsend.greaterOrEqualThan=" + DEFAULT_ISTIKETSEND);

        // Get all the distributorList where istiketsend greater than or equals to UPDATED_ISTIKETSEND
        defaultDistributorShouldNotBeFound("istiketsend.greaterOrEqualThan=" + UPDATED_ISTIKETSEND);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIstiketsendIsLessThanSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where istiketsend less than or equals to DEFAULT_ISTIKETSEND
        defaultDistributorShouldNotBeFound("istiketsend.lessThan=" + DEFAULT_ISTIKETSEND);

        // Get all the distributorList where istiketsend less than or equals to UPDATED_ISTIKETSEND
        defaultDistributorShouldBeFound("istiketsend.lessThan=" + UPDATED_ISTIKETSEND);
    }


    @Test
    @Transactional
    public void getAllDistributorsByPesanTiketIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where pesanTiket equals to DEFAULT_PESAN_TIKET
        defaultDistributorShouldBeFound("pesanTiket.equals=" + DEFAULT_PESAN_TIKET);

        // Get all the distributorList where pesanTiket equals to UPDATED_PESAN_TIKET
        defaultDistributorShouldNotBeFound("pesanTiket.equals=" + UPDATED_PESAN_TIKET);
    }

    @Test
    @Transactional
    public void getAllDistributorsByPesanTiketIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where pesanTiket in DEFAULT_PESAN_TIKET or UPDATED_PESAN_TIKET
        defaultDistributorShouldBeFound("pesanTiket.in=" + DEFAULT_PESAN_TIKET + "," + UPDATED_PESAN_TIKET);

        // Get all the distributorList where pesanTiket equals to UPDATED_PESAN_TIKET
        defaultDistributorShouldNotBeFound("pesanTiket.in=" + UPDATED_PESAN_TIKET);
    }

    @Test
    @Transactional
    public void getAllDistributorsByPesanTiketIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where pesanTiket is not null
        defaultDistributorShouldBeFound("pesanTiket.specified=true");

        // Get all the distributorList where pesanTiket is null
        defaultDistributorShouldNotBeFound("pesanTiket.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsBySaldoSupwarnIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where saldoSupwarn equals to DEFAULT_SALDO_SUPWARN
        defaultDistributorShouldBeFound("saldoSupwarn.equals=" + DEFAULT_SALDO_SUPWARN);

        // Get all the distributorList where saldoSupwarn equals to UPDATED_SALDO_SUPWARN
        defaultDistributorShouldNotBeFound("saldoSupwarn.equals=" + UPDATED_SALDO_SUPWARN);
    }

    @Test
    @Transactional
    public void getAllDistributorsBySaldoSupwarnIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where saldoSupwarn in DEFAULT_SALDO_SUPWARN or UPDATED_SALDO_SUPWARN
        defaultDistributorShouldBeFound("saldoSupwarn.in=" + DEFAULT_SALDO_SUPWARN + "," + UPDATED_SALDO_SUPWARN);

        // Get all the distributorList where saldoSupwarn equals to UPDATED_SALDO_SUPWARN
        defaultDistributorShouldNotBeFound("saldoSupwarn.in=" + UPDATED_SALDO_SUPWARN);
    }

    @Test
    @Transactional
    public void getAllDistributorsBySaldoSupwarnIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where saldoSupwarn is not null
        defaultDistributorShouldBeFound("saldoSupwarn.specified=true");

        // Get all the distributorList where saldoSupwarn is null
        defaultDistributorShouldNotBeFound("saldoSupwarn.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsBySaldoSupwarnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where saldoSupwarn greater than or equals to DEFAULT_SALDO_SUPWARN
        defaultDistributorShouldBeFound("saldoSupwarn.greaterOrEqualThan=" + DEFAULT_SALDO_SUPWARN);

        // Get all the distributorList where saldoSupwarn greater than or equals to UPDATED_SALDO_SUPWARN
        defaultDistributorShouldNotBeFound("saldoSupwarn.greaterOrEqualThan=" + UPDATED_SALDO_SUPWARN);
    }

    @Test
    @Transactional
    public void getAllDistributorsBySaldoSupwarnIsLessThanSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where saldoSupwarn less than or equals to DEFAULT_SALDO_SUPWARN
        defaultDistributorShouldNotBeFound("saldoSupwarn.lessThan=" + DEFAULT_SALDO_SUPWARN);

        // Get all the distributorList where saldoSupwarn less than or equals to UPDATED_SALDO_SUPWARN
        defaultDistributorShouldBeFound("saldoSupwarn.lessThan=" + UPDATED_SALDO_SUPWARN);
    }


    @Test
    @Transactional
    public void getAllDistributorsByIssortbyIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where issortby equals to DEFAULT_ISSORTBY
        defaultDistributorShouldBeFound("issortby.equals=" + DEFAULT_ISSORTBY);

        // Get all the distributorList where issortby equals to UPDATED_ISSORTBY
        defaultDistributorShouldNotBeFound("issortby.equals=" + UPDATED_ISSORTBY);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIssortbyIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where issortby in DEFAULT_ISSORTBY or UPDATED_ISSORTBY
        defaultDistributorShouldBeFound("issortby.in=" + DEFAULT_ISSORTBY + "," + UPDATED_ISSORTBY);

        // Get all the distributorList where issortby equals to UPDATED_ISSORTBY
        defaultDistributorShouldNotBeFound("issortby.in=" + UPDATED_ISSORTBY);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIssortbyIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where issortby is not null
        defaultDistributorShouldBeFound("issortby.specified=true");

        // Get all the distributorList where issortby is null
        defaultDistributorShouldNotBeFound("issortby.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByIssortbyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where issortby greater than or equals to DEFAULT_ISSORTBY
        defaultDistributorShouldBeFound("issortby.greaterOrEqualThan=" + DEFAULT_ISSORTBY);

        // Get all the distributorList where issortby greater than or equals to UPDATED_ISSORTBY
        defaultDistributorShouldNotBeFound("issortby.greaterOrEqualThan=" + UPDATED_ISSORTBY);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIssortbyIsLessThanSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where issortby less than or equals to DEFAULT_ISSORTBY
        defaultDistributorShouldNotBeFound("issortby.lessThan=" + DEFAULT_ISSORTBY);

        // Get all the distributorList where issortby less than or equals to UPDATED_ISSORTBY
        defaultDistributorShouldBeFound("issortby.lessThan=" + UPDATED_ISSORTBY);
    }


    @Test
    @Transactional
    public void getAllDistributorsByParseUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where parseUnit equals to DEFAULT_PARSE_UNIT
        defaultDistributorShouldBeFound("parseUnit.equals=" + DEFAULT_PARSE_UNIT);

        // Get all the distributorList where parseUnit equals to UPDATED_PARSE_UNIT
        defaultDistributorShouldNotBeFound("parseUnit.equals=" + UPDATED_PARSE_UNIT);
    }

    @Test
    @Transactional
    public void getAllDistributorsByParseUnitIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where parseUnit in DEFAULT_PARSE_UNIT or UPDATED_PARSE_UNIT
        defaultDistributorShouldBeFound("parseUnit.in=" + DEFAULT_PARSE_UNIT + "," + UPDATED_PARSE_UNIT);

        // Get all the distributorList where parseUnit equals to UPDATED_PARSE_UNIT
        defaultDistributorShouldNotBeFound("parseUnit.in=" + UPDATED_PARSE_UNIT);
    }

    @Test
    @Transactional
    public void getAllDistributorsByParseUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where parseUnit is not null
        defaultDistributorShouldBeFound("parseUnit.specified=true");

        // Get all the distributorList where parseUnit is null
        defaultDistributorShouldNotBeFound("parseUnit.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsulangimIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isulangim equals to DEFAULT_ISULANGIM
        defaultDistributorShouldBeFound("isulangim.equals=" + DEFAULT_ISULANGIM);

        // Get all the distributorList where isulangim equals to UPDATED_ISULANGIM
        defaultDistributorShouldNotBeFound("isulangim.equals=" + UPDATED_ISULANGIM);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsulangimIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isulangim in DEFAULT_ISULANGIM or UPDATED_ISULANGIM
        defaultDistributorShouldBeFound("isulangim.in=" + DEFAULT_ISULANGIM + "," + UPDATED_ISULANGIM);

        // Get all the distributorList where isulangim equals to UPDATED_ISULANGIM
        defaultDistributorShouldNotBeFound("isulangim.in=" + UPDATED_ISULANGIM);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsulangimIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isulangim is not null
        defaultDistributorShouldBeFound("isulangim.specified=true");

        // Get all the distributorList where isulangim is null
        defaultDistributorShouldNotBeFound("isulangim.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsulangimIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isulangim greater than or equals to DEFAULT_ISULANGIM
        defaultDistributorShouldBeFound("isulangim.greaterOrEqualThan=" + DEFAULT_ISULANGIM);

        // Get all the distributorList where isulangim greater than or equals to UPDATED_ISULANGIM
        defaultDistributorShouldNotBeFound("isulangim.greaterOrEqualThan=" + UPDATED_ISULANGIM);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIsulangimIsLessThanSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where isulangim less than or equals to DEFAULT_ISULANGIM
        defaultDistributorShouldNotBeFound("isulangim.lessThan=" + DEFAULT_ISULANGIM);

        // Get all the distributorList where isulangim less than or equals to UPDATED_ISULANGIM
        defaultDistributorShouldBeFound("isulangim.lessThan=" + UPDATED_ISULANGIM);
    }


    @Test
    @Transactional
    public void getAllDistributorsByIshlrIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where ishlr equals to DEFAULT_ISHLR
        defaultDistributorShouldBeFound("ishlr.equals=" + DEFAULT_ISHLR);

        // Get all the distributorList where ishlr equals to UPDATED_ISHLR
        defaultDistributorShouldNotBeFound("ishlr.equals=" + UPDATED_ISHLR);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIshlrIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where ishlr in DEFAULT_ISHLR or UPDATED_ISHLR
        defaultDistributorShouldBeFound("ishlr.in=" + DEFAULT_ISHLR + "," + UPDATED_ISHLR);

        // Get all the distributorList where ishlr equals to UPDATED_ISHLR
        defaultDistributorShouldNotBeFound("ishlr.in=" + UPDATED_ISHLR);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIshlrIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where ishlr is not null
        defaultDistributorShouldBeFound("ishlr.specified=true");

        // Get all the distributorList where ishlr is null
        defaultDistributorShouldNotBeFound("ishlr.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByIshlrIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where ishlr greater than or equals to DEFAULT_ISHLR
        defaultDistributorShouldBeFound("ishlr.greaterOrEqualThan=" + DEFAULT_ISHLR);

        // Get all the distributorList where ishlr greater than or equals to UPDATED_ISHLR
        defaultDistributorShouldNotBeFound("ishlr.greaterOrEqualThan=" + UPDATED_ISHLR);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIshlrIsLessThanSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where ishlr less than or equals to DEFAULT_ISHLR
        defaultDistributorShouldNotBeFound("ishlr.lessThan=" + DEFAULT_ISHLR);

        // Get all the distributorList where ishlr less than or equals to UPDATED_ISHLR
        defaultDistributorShouldBeFound("ishlr.lessThan=" + UPDATED_ISHLR);
    }


    @Test
    @Transactional
    public void getAllDistributorsByKodeParsing3IsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where kodeParsing3 equals to DEFAULT_KODE_PARSING_3
        defaultDistributorShouldBeFound("kodeParsing3.equals=" + DEFAULT_KODE_PARSING_3);

        // Get all the distributorList where kodeParsing3 equals to UPDATED_KODE_PARSING_3
        defaultDistributorShouldNotBeFound("kodeParsing3.equals=" + UPDATED_KODE_PARSING_3);
    }

    @Test
    @Transactional
    public void getAllDistributorsByKodeParsing3IsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where kodeParsing3 in DEFAULT_KODE_PARSING_3 or UPDATED_KODE_PARSING_3
        defaultDistributorShouldBeFound("kodeParsing3.in=" + DEFAULT_KODE_PARSING_3 + "," + UPDATED_KODE_PARSING_3);

        // Get all the distributorList where kodeParsing3 equals to UPDATED_KODE_PARSING_3
        defaultDistributorShouldNotBeFound("kodeParsing3.in=" + UPDATED_KODE_PARSING_3);
    }

    @Test
    @Transactional
    public void getAllDistributorsByKodeParsing3IsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where kodeParsing3 is not null
        defaultDistributorShouldBeFound("kodeParsing3.specified=true");

        // Get all the distributorList where kodeParsing3 is null
        defaultDistributorShouldNotBeFound("kodeParsing3.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByIdHistoryIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where idHistory equals to DEFAULT_ID_HISTORY
        defaultDistributorShouldBeFound("idHistory.equals=" + DEFAULT_ID_HISTORY);

        // Get all the distributorList where idHistory equals to UPDATED_ID_HISTORY
        defaultDistributorShouldNotBeFound("idHistory.equals=" + UPDATED_ID_HISTORY);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIdHistoryIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where idHistory in DEFAULT_ID_HISTORY or UPDATED_ID_HISTORY
        defaultDistributorShouldBeFound("idHistory.in=" + DEFAULT_ID_HISTORY + "," + UPDATED_ID_HISTORY);

        // Get all the distributorList where idHistory equals to UPDATED_ID_HISTORY
        defaultDistributorShouldNotBeFound("idHistory.in=" + UPDATED_ID_HISTORY);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIdHistoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where idHistory is not null
        defaultDistributorShouldBeFound("idHistory.specified=true");

        // Get all the distributorList where idHistory is null
        defaultDistributorShouldNotBeFound("idHistory.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByIdHistoryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where idHistory greater than or equals to DEFAULT_ID_HISTORY
        defaultDistributorShouldBeFound("idHistory.greaterOrEqualThan=" + DEFAULT_ID_HISTORY);

        // Get all the distributorList where idHistory greater than or equals to UPDATED_ID_HISTORY
        defaultDistributorShouldNotBeFound("idHistory.greaterOrEqualThan=" + UPDATED_ID_HISTORY);
    }

    @Test
    @Transactional
    public void getAllDistributorsByIdHistoryIsLessThanSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where idHistory less than or equals to DEFAULT_ID_HISTORY
        defaultDistributorShouldNotBeFound("idHistory.lessThan=" + DEFAULT_ID_HISTORY);

        // Get all the distributorList where idHistory less than or equals to UPDATED_ID_HISTORY
        defaultDistributorShouldBeFound("idHistory.lessThan=" + UPDATED_ID_HISTORY);
    }


    @Test
    @Transactional
    public void getAllDistributorsByKodeParsing4IsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where kodeParsing4 equals to DEFAULT_KODE_PARSING_4
        defaultDistributorShouldBeFound("kodeParsing4.equals=" + DEFAULT_KODE_PARSING_4);

        // Get all the distributorList where kodeParsing4 equals to UPDATED_KODE_PARSING_4
        defaultDistributorShouldNotBeFound("kodeParsing4.equals=" + UPDATED_KODE_PARSING_4);
    }

    @Test
    @Transactional
    public void getAllDistributorsByKodeParsing4IsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where kodeParsing4 in DEFAULT_KODE_PARSING_4 or UPDATED_KODE_PARSING_4
        defaultDistributorShouldBeFound("kodeParsing4.in=" + DEFAULT_KODE_PARSING_4 + "," + UPDATED_KODE_PARSING_4);

        // Get all the distributorList where kodeParsing4 equals to UPDATED_KODE_PARSING_4
        defaultDistributorShouldNotBeFound("kodeParsing4.in=" + UPDATED_KODE_PARSING_4);
    }

    @Test
    @Transactional
    public void getAllDistributorsByKodeParsing4IsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where kodeParsing4 is not null
        defaultDistributorShouldBeFound("kodeParsing4.specified=true");

        // Get all the distributorList where kodeParsing4 is null
        defaultDistributorShouldNotBeFound("kodeParsing4.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsBySelisihSupwarnIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where selisihSupwarn equals to DEFAULT_SELISIH_SUPWARN
        defaultDistributorShouldBeFound("selisihSupwarn.equals=" + DEFAULT_SELISIH_SUPWARN);

        // Get all the distributorList where selisihSupwarn equals to UPDATED_SELISIH_SUPWARN
        defaultDistributorShouldNotBeFound("selisihSupwarn.equals=" + UPDATED_SELISIH_SUPWARN);
    }

    @Test
    @Transactional
    public void getAllDistributorsBySelisihSupwarnIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where selisihSupwarn in DEFAULT_SELISIH_SUPWARN or UPDATED_SELISIH_SUPWARN
        defaultDistributorShouldBeFound("selisihSupwarn.in=" + DEFAULT_SELISIH_SUPWARN + "," + UPDATED_SELISIH_SUPWARN);

        // Get all the distributorList where selisihSupwarn equals to UPDATED_SELISIH_SUPWARN
        defaultDistributorShouldNotBeFound("selisihSupwarn.in=" + UPDATED_SELISIH_SUPWARN);
    }

    @Test
    @Transactional
    public void getAllDistributorsBySelisihSupwarnIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where selisihSupwarn is not null
        defaultDistributorShouldBeFound("selisihSupwarn.specified=true");

        // Get all the distributorList where selisihSupwarn is null
        defaultDistributorShouldNotBeFound("selisihSupwarn.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsBySelisihSupwarnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where selisihSupwarn greater than or equals to DEFAULT_SELISIH_SUPWARN
        defaultDistributorShouldBeFound("selisihSupwarn.greaterOrEqualThan=" + DEFAULT_SELISIH_SUPWARN);

        // Get all the distributorList where selisihSupwarn greater than or equals to UPDATED_SELISIH_SUPWARN
        defaultDistributorShouldNotBeFound("selisihSupwarn.greaterOrEqualThan=" + UPDATED_SELISIH_SUPWARN);
    }

    @Test
    @Transactional
    public void getAllDistributorsBySelisihSupwarnIsLessThanSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where selisihSupwarn less than or equals to DEFAULT_SELISIH_SUPWARN
        defaultDistributorShouldNotBeFound("selisihSupwarn.lessThan=" + DEFAULT_SELISIH_SUPWARN);

        // Get all the distributorList where selisihSupwarn less than or equals to UPDATED_SELISIH_SUPWARN
        defaultDistributorShouldBeFound("selisihSupwarn.lessThan=" + UPDATED_SELISIH_SUPWARN);
    }


    @Test
    @Transactional
    public void getAllDistributorsByTimeonIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where timeon equals to DEFAULT_TIMEON
        defaultDistributorShouldBeFound("timeon.equals=" + DEFAULT_TIMEON);

        // Get all the distributorList where timeon equals to UPDATED_TIMEON
        defaultDistributorShouldNotBeFound("timeon.equals=" + UPDATED_TIMEON);
    }

    @Test
    @Transactional
    public void getAllDistributorsByTimeonIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where timeon in DEFAULT_TIMEON or UPDATED_TIMEON
        defaultDistributorShouldBeFound("timeon.in=" + DEFAULT_TIMEON + "," + UPDATED_TIMEON);

        // Get all the distributorList where timeon equals to UPDATED_TIMEON
        defaultDistributorShouldNotBeFound("timeon.in=" + UPDATED_TIMEON);
    }

    @Test
    @Transactional
    public void getAllDistributorsByTimeonIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where timeon is not null
        defaultDistributorShouldBeFound("timeon.specified=true");

        // Get all the distributorList where timeon is null
        defaultDistributorShouldNotBeFound("timeon.specified=false");
    }

    @Test
    @Transactional
    public void getAllDistributorsByTimeoffIsEqualToSomething() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where timeoff equals to DEFAULT_TIMEOFF
        defaultDistributorShouldBeFound("timeoff.equals=" + DEFAULT_TIMEOFF);

        // Get all the distributorList where timeoff equals to UPDATED_TIMEOFF
        defaultDistributorShouldNotBeFound("timeoff.equals=" + UPDATED_TIMEOFF);
    }

    @Test
    @Transactional
    public void getAllDistributorsByTimeoffIsInShouldWork() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where timeoff in DEFAULT_TIMEOFF or UPDATED_TIMEOFF
        defaultDistributorShouldBeFound("timeoff.in=" + DEFAULT_TIMEOFF + "," + UPDATED_TIMEOFF);

        // Get all the distributorList where timeoff equals to UPDATED_TIMEOFF
        defaultDistributorShouldNotBeFound("timeoff.in=" + UPDATED_TIMEOFF);
    }

    @Test
    @Transactional
    public void getAllDistributorsByTimeoffIsNullOrNotNull() throws Exception {
        // Initialize the database
        distributorRepository.saveAndFlush(distributor);

        // Get all the distributorList where timeoff is not null
        defaultDistributorShouldBeFound("timeoff.specified=true");

        // Get all the distributorList where timeoff is null
        defaultDistributorShouldNotBeFound("timeoff.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDistributorShouldBeFound(String filter) throws Exception {
        restDistributorMockMvc.perform(get("/api/distributors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(distributor.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDistributor").value(hasItem(DEFAULT_ID_DISTRIBUTOR.intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].kodeId").value(hasItem(DEFAULT_KODE_ID.toString())))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN.toString())))
            .andExpect(jsonPath("$.[*].com").value(hasItem(DEFAULT_COM.toString())))
            .andExpect(jsonPath("$.[*].noKontak").value(hasItem(DEFAULT_NO_KONTAK.toString())))
            .andExpect(jsonPath("$.[*].metode").value(hasItem(DEFAULT_METODE.toString())))
            .andExpect(jsonPath("$.[*].kodeParsing").value(hasItem(DEFAULT_KODE_PARSING.toString())))
            .andExpect(jsonPath("$.[*].kodeParsing2").value(hasItem(DEFAULT_KODE_PARSING_2.toString())))
            .andExpect(jsonPath("$.[*].replyno").value(hasItem(DEFAULT_REPLYNO.toString())))
            .andExpect(jsonPath("$.[*].tglInput").value(hasItem(sameInstant(DEFAULT_TGL_INPUT))))
            .andExpect(jsonPath("$.[*].userInput").value(hasItem(DEFAULT_USER_INPUT.toString())))
            .andExpect(jsonPath("$.[*].tglUpdate").value(hasItem(sameInstant(DEFAULT_TGL_UPDATE))))
            .andExpect(jsonPath("$.[*].userUpdate").value(hasItem(DEFAULT_USER_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].isvre").value(hasItem(DEFAULT_ISVRE)))
            .andExpect(jsonPath("$.[*].isgtw").value(hasItem(DEFAULT_ISGTW)))
            .andExpect(jsonPath("$.[*].ugtw").value(hasItem(DEFAULT_UGTW.toString())))
            .andExpect(jsonPath("$.[*].isfilter").value(hasItem(DEFAULT_ISFILTER)))
            .andExpect(jsonPath("$.[*].parseSaldo").value(hasItem(DEFAULT_PARSE_SALDO.toString())))
            .andExpect(jsonPath("$.[*].parseHarga").value(hasItem(DEFAULT_PARSE_HARGA.toString())))
            .andExpect(jsonPath("$.[*].tiketWrap").value(hasItem(DEFAULT_TIKET_WRAP.toString())))
            .andExpect(jsonPath("$.[*].istiketsend").value(hasItem(DEFAULT_ISTIKETSEND)))
            .andExpect(jsonPath("$.[*].pesanTiket").value(hasItem(DEFAULT_PESAN_TIKET.toString())))
            .andExpect(jsonPath("$.[*].saldoSupwarn").value(hasItem(DEFAULT_SALDO_SUPWARN)))
            .andExpect(jsonPath("$.[*].issortby").value(hasItem(DEFAULT_ISSORTBY)))
            .andExpect(jsonPath("$.[*].parseUnit").value(hasItem(DEFAULT_PARSE_UNIT.toString())))
            .andExpect(jsonPath("$.[*].isulangim").value(hasItem(DEFAULT_ISULANGIM)))
            .andExpect(jsonPath("$.[*].ishlr").value(hasItem(DEFAULT_ISHLR)))
            .andExpect(jsonPath("$.[*].kodeParsing3").value(hasItem(DEFAULT_KODE_PARSING_3.toString())))
            .andExpect(jsonPath("$.[*].idHistory").value(hasItem(DEFAULT_ID_HISTORY.intValue())))
            .andExpect(jsonPath("$.[*].kodeParsing4").value(hasItem(DEFAULT_KODE_PARSING_4.toString())))
            .andExpect(jsonPath("$.[*].selisihSupwarn").value(hasItem(DEFAULT_SELISIH_SUPWARN)))
            .andExpect(jsonPath("$.[*].timeon").value(hasItem(DEFAULT_TIMEON.toString())))
            .andExpect(jsonPath("$.[*].timeoff").value(hasItem(DEFAULT_TIMEOFF.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDistributorShouldNotBeFound(String filter) throws Exception {
        restDistributorMockMvc.perform(get("/api/distributors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingDistributor() throws Exception {
        // Get the distributor
        restDistributorMockMvc.perform(get("/api/distributors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDistributor() throws Exception {
        // Initialize the database
        distributorService.save(distributor);

        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();

        // Update the distributor
        Distributor updatedDistributor = distributorRepository.findOne(distributor.getId());
        updatedDistributor
            .idDistributor(UPDATED_ID_DISTRIBUTOR)
            .nama(UPDATED_NAMA)
            .status(UPDATED_STATUS)
            .kodeId(UPDATED_KODE_ID)
            .pin(UPDATED_PIN)
            .com(UPDATED_COM)
            .noKontak(UPDATED_NO_KONTAK)
            .metode(UPDATED_METODE)
            .kodeParsing(UPDATED_KODE_PARSING)
            .kodeParsing2(UPDATED_KODE_PARSING_2)
            .replyno(UPDATED_REPLYNO)
            .tglInput(UPDATED_TGL_INPUT)
            .userInput(UPDATED_USER_INPUT)
            .tglUpdate(UPDATED_TGL_UPDATE)
            .userUpdate(UPDATED_USER_UPDATE)
            .ip(UPDATED_IP)
            .isvre(UPDATED_ISVRE)
            .isgtw(UPDATED_ISGTW)
            .ugtw(UPDATED_UGTW)
            .isfilter(UPDATED_ISFILTER)
            .parseSaldo(UPDATED_PARSE_SALDO)
            .parseHarga(UPDATED_PARSE_HARGA)
            .tiketWrap(UPDATED_TIKET_WRAP)
            .istiketsend(UPDATED_ISTIKETSEND)
            .pesanTiket(UPDATED_PESAN_TIKET)
            .saldoSupwarn(UPDATED_SALDO_SUPWARN)
            .issortby(UPDATED_ISSORTBY)
            .parseUnit(UPDATED_PARSE_UNIT)
            .isulangim(UPDATED_ISULANGIM)
            .ishlr(UPDATED_ISHLR)
            .kodeParsing3(UPDATED_KODE_PARSING_3)
            .idHistory(UPDATED_ID_HISTORY)
            .kodeParsing4(UPDATED_KODE_PARSING_4)
            .selisihSupwarn(UPDATED_SELISIH_SUPWARN)
            .timeon(UPDATED_TIMEON)
            .timeoff(UPDATED_TIMEOFF);

        restDistributorMockMvc.perform(put("/api/distributors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDistributor)))
            .andExpect(status().isOk());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate);
        Distributor testDistributor = distributorList.get(distributorList.size() - 1);
        assertThat(testDistributor.getIdDistributor()).isEqualTo(UPDATED_ID_DISTRIBUTOR);
        assertThat(testDistributor.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testDistributor.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDistributor.getKodeId()).isEqualTo(UPDATED_KODE_ID);
        assertThat(testDistributor.getPin()).isEqualTo(UPDATED_PIN);
        assertThat(testDistributor.getCom()).isEqualTo(UPDATED_COM);
        assertThat(testDistributor.getNoKontak()).isEqualTo(UPDATED_NO_KONTAK);
        assertThat(testDistributor.getMetode()).isEqualTo(UPDATED_METODE);
        assertThat(testDistributor.getKodeParsing()).isEqualTo(UPDATED_KODE_PARSING);
        assertThat(testDistributor.getKodeParsing2()).isEqualTo(UPDATED_KODE_PARSING_2);
        assertThat(testDistributor.getReplyno()).isEqualTo(UPDATED_REPLYNO);
        assertThat(testDistributor.getTglInput()).isEqualTo(UPDATED_TGL_INPUT);
        assertThat(testDistributor.getUserInput()).isEqualTo(UPDATED_USER_INPUT);
        assertThat(testDistributor.getTglUpdate()).isEqualTo(UPDATED_TGL_UPDATE);
        assertThat(testDistributor.getUserUpdate()).isEqualTo(UPDATED_USER_UPDATE);
        assertThat(testDistributor.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testDistributor.getIsvre()).isEqualTo(UPDATED_ISVRE);
        assertThat(testDistributor.getIsgtw()).isEqualTo(UPDATED_ISGTW);
        assertThat(testDistributor.getUgtw()).isEqualTo(UPDATED_UGTW);
        assertThat(testDistributor.getIsfilter()).isEqualTo(UPDATED_ISFILTER);
        assertThat(testDistributor.getParseSaldo()).isEqualTo(UPDATED_PARSE_SALDO);
        assertThat(testDistributor.getParseHarga()).isEqualTo(UPDATED_PARSE_HARGA);
        assertThat(testDistributor.getTiketWrap()).isEqualTo(UPDATED_TIKET_WRAP);
        assertThat(testDistributor.getIstiketsend()).isEqualTo(UPDATED_ISTIKETSEND);
        assertThat(testDistributor.getPesanTiket()).isEqualTo(UPDATED_PESAN_TIKET);
        assertThat(testDistributor.getSaldoSupwarn()).isEqualTo(UPDATED_SALDO_SUPWARN);
        assertThat(testDistributor.getIssortby()).isEqualTo(UPDATED_ISSORTBY);
        assertThat(testDistributor.getParseUnit()).isEqualTo(UPDATED_PARSE_UNIT);
        assertThat(testDistributor.getIsulangim()).isEqualTo(UPDATED_ISULANGIM);
        assertThat(testDistributor.getIshlr()).isEqualTo(UPDATED_ISHLR);
        assertThat(testDistributor.getKodeParsing3()).isEqualTo(UPDATED_KODE_PARSING_3);
        assertThat(testDistributor.getIdHistory()).isEqualTo(UPDATED_ID_HISTORY);
        assertThat(testDistributor.getKodeParsing4()).isEqualTo(UPDATED_KODE_PARSING_4);
        assertThat(testDistributor.getSelisihSupwarn()).isEqualTo(UPDATED_SELISIH_SUPWARN);
        assertThat(testDistributor.getTimeon()).isEqualTo(UPDATED_TIMEON);
        assertThat(testDistributor.getTimeoff()).isEqualTo(UPDATED_TIMEOFF);

        // Validate the Distributor in Elasticsearch
        Distributor distributorEs = distributorSearchRepository.findOne(testDistributor.getId());
        assertThat(distributorEs).isEqualToComparingFieldByField(testDistributor);
    }

    @Test
    @Transactional
    public void updateNonExistingDistributor() throws Exception {
        int databaseSizeBeforeUpdate = distributorRepository.findAll().size();

        // Create the Distributor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDistributorMockMvc.perform(put("/api/distributors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(distributor)))
            .andExpect(status().isCreated());

        // Validate the Distributor in the database
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDistributor() throws Exception {
        // Initialize the database
        distributorService.save(distributor);

        int databaseSizeBeforeDelete = distributorRepository.findAll().size();

        // Get the distributor
        restDistributorMockMvc.perform(delete("/api/distributors/{id}", distributor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean distributorExistsInEs = distributorSearchRepository.exists(distributor.getId());
        assertThat(distributorExistsInEs).isFalse();

        // Validate the database is empty
        List<Distributor> distributorList = distributorRepository.findAll();
        assertThat(distributorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDistributor() throws Exception {
        // Initialize the database
        distributorService.save(distributor);

        // Search the distributor
        restDistributorMockMvc.perform(get("/api/_search/distributors?query=id:" + distributor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(distributor.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDistributor").value(hasItem(DEFAULT_ID_DISTRIBUTOR.intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].kodeId").value(hasItem(DEFAULT_KODE_ID.toString())))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN.toString())))
            .andExpect(jsonPath("$.[*].com").value(hasItem(DEFAULT_COM.toString())))
            .andExpect(jsonPath("$.[*].noKontak").value(hasItem(DEFAULT_NO_KONTAK.toString())))
            .andExpect(jsonPath("$.[*].metode").value(hasItem(DEFAULT_METODE.toString())))
            .andExpect(jsonPath("$.[*].kodeParsing").value(hasItem(DEFAULT_KODE_PARSING.toString())))
            .andExpect(jsonPath("$.[*].kodeParsing2").value(hasItem(DEFAULT_KODE_PARSING_2.toString())))
            .andExpect(jsonPath("$.[*].replyno").value(hasItem(DEFAULT_REPLYNO.toString())))
            .andExpect(jsonPath("$.[*].tglInput").value(hasItem(sameInstant(DEFAULT_TGL_INPUT))))
            .andExpect(jsonPath("$.[*].userInput").value(hasItem(DEFAULT_USER_INPUT.toString())))
            .andExpect(jsonPath("$.[*].tglUpdate").value(hasItem(sameInstant(DEFAULT_TGL_UPDATE))))
            .andExpect(jsonPath("$.[*].userUpdate").value(hasItem(DEFAULT_USER_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].isvre").value(hasItem(DEFAULT_ISVRE)))
            .andExpect(jsonPath("$.[*].isgtw").value(hasItem(DEFAULT_ISGTW)))
            .andExpect(jsonPath("$.[*].ugtw").value(hasItem(DEFAULT_UGTW.toString())))
            .andExpect(jsonPath("$.[*].isfilter").value(hasItem(DEFAULT_ISFILTER)))
            .andExpect(jsonPath("$.[*].parseSaldo").value(hasItem(DEFAULT_PARSE_SALDO.toString())))
            .andExpect(jsonPath("$.[*].parseHarga").value(hasItem(DEFAULT_PARSE_HARGA.toString())))
            .andExpect(jsonPath("$.[*].tiketWrap").value(hasItem(DEFAULT_TIKET_WRAP.toString())))
            .andExpect(jsonPath("$.[*].istiketsend").value(hasItem(DEFAULT_ISTIKETSEND)))
            .andExpect(jsonPath("$.[*].pesanTiket").value(hasItem(DEFAULT_PESAN_TIKET.toString())))
            .andExpect(jsonPath("$.[*].saldoSupwarn").value(hasItem(DEFAULT_SALDO_SUPWARN)))
            .andExpect(jsonPath("$.[*].issortby").value(hasItem(DEFAULT_ISSORTBY)))
            .andExpect(jsonPath("$.[*].parseUnit").value(hasItem(DEFAULT_PARSE_UNIT.toString())))
            .andExpect(jsonPath("$.[*].isulangim").value(hasItem(DEFAULT_ISULANGIM)))
            .andExpect(jsonPath("$.[*].ishlr").value(hasItem(DEFAULT_ISHLR)))
            .andExpect(jsonPath("$.[*].kodeParsing3").value(hasItem(DEFAULT_KODE_PARSING_3.toString())))
            .andExpect(jsonPath("$.[*].idHistory").value(hasItem(DEFAULT_ID_HISTORY.intValue())))
            .andExpect(jsonPath("$.[*].kodeParsing4").value(hasItem(DEFAULT_KODE_PARSING_4.toString())))
            .andExpect(jsonPath("$.[*].selisihSupwarn").value(hasItem(DEFAULT_SELISIH_SUPWARN)))
            .andExpect(jsonPath("$.[*].timeon").value(hasItem(DEFAULT_TIMEON.toString())))
            .andExpect(jsonPath("$.[*].timeoff").value(hasItem(DEFAULT_TIMEOFF.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Distributor.class);
        Distributor distributor1 = new Distributor();
        distributor1.setId(1L);
        Distributor distributor2 = new Distributor();
        distributor2.setId(distributor1.getId());
        assertThat(distributor1).isEqualTo(distributor2);
        distributor2.setId(2L);
        assertThat(distributor1).isNotEqualTo(distributor2);
        distributor1.setId(null);
        assertThat(distributor1).isNotEqualTo(distributor2);
    }
}
