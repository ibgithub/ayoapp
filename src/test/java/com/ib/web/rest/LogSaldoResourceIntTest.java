package com.ib.web.rest;

import com.ib.AyoappApp;

import com.ib.domain.LogSaldo;
import com.ib.repository.LogSaldoRepository;
import com.ib.service.LogSaldoService;
import com.ib.repository.search.LogSaldoSearchRepository;
import com.ib.web.rest.errors.ExceptionTranslator;
import com.ib.service.dto.LogSaldoCriteria;
import com.ib.service.LogSaldoQueryService;

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
 * Test class for the LogSaldoResource REST controller.
 *
 * @see LogSaldoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AyoappApp.class)
public class LogSaldoResourceIntTest {

    private static final String DEFAULT_ID_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_ID_MEMBER = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SALDO = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALDO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ACT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACT = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_TGL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TGL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_KET = "AAAAAAAAAA";
    private static final String UPDATED_KET = "BBBBBBBBBB";

    private static final Integer DEFAULT_REF = 1;
    private static final Integer UPDATED_REF = 2;

    private static final Integer DEFAULT_TKODE = 1;
    private static final Integer UPDATED_TKODE = 2;

    private static final Integer DEFAULT_KODETRX = 1;
    private static final Integer UPDATED_KODETRX = 2;

    private static final String DEFAULT_MSG = "AAAAAAAAAA";
    private static final String UPDATED_MSG = "BBBBBBBBBB";

    private static final String DEFAULT_USER_INPUT = "AAAAAAAAAA";
    private static final String UPDATED_USER_INPUT = "BBBBBBBBBB";

    private static final Integer DEFAULT_ISSTOK = 1;
    private static final Integer UPDATED_ISSTOK = 2;

    @Autowired
    private LogSaldoRepository logSaldoRepository;

    @Autowired
    private LogSaldoService logSaldoService;

    @Autowired
    private LogSaldoSearchRepository logSaldoSearchRepository;

    @Autowired
    private LogSaldoQueryService logSaldoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLogSaldoMockMvc;

    private LogSaldo logSaldo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LogSaldoResource logSaldoResource = new LogSaldoResource(logSaldoService, logSaldoQueryService);
        this.restLogSaldoMockMvc = MockMvcBuilders.standaloneSetup(logSaldoResource)
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
    public static LogSaldo createEntity(EntityManager em) {
        LogSaldo logSaldo = new LogSaldo()
            .idMember(DEFAULT_ID_MEMBER)
            .saldo(DEFAULT_SALDO)
            .act(DEFAULT_ACT)
            .tgl(DEFAULT_TGL)
            .ket(DEFAULT_KET)
            .ref(DEFAULT_REF)
            .tkode(DEFAULT_TKODE)
            .kodetrx(DEFAULT_KODETRX)
            .msg(DEFAULT_MSG)
            .userInput(DEFAULT_USER_INPUT)
            .isstok(DEFAULT_ISSTOK);
        return logSaldo;
    }

    @Before
    public void initTest() {
        logSaldoSearchRepository.deleteAll();
        logSaldo = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogSaldo() throws Exception {
        int databaseSizeBeforeCreate = logSaldoRepository.findAll().size();

        // Create the LogSaldo
        restLogSaldoMockMvc.perform(post("/api/log-saldos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logSaldo)))
            .andExpect(status().isCreated());

        // Validate the LogSaldo in the database
        List<LogSaldo> logSaldoList = logSaldoRepository.findAll();
        assertThat(logSaldoList).hasSize(databaseSizeBeforeCreate + 1);
        LogSaldo testLogSaldo = logSaldoList.get(logSaldoList.size() - 1);
        assertThat(testLogSaldo.getIdMember()).isEqualTo(DEFAULT_ID_MEMBER);
        assertThat(testLogSaldo.getSaldo()).isEqualTo(DEFAULT_SALDO);
        assertThat(testLogSaldo.getAct()).isEqualTo(DEFAULT_ACT);
        assertThat(testLogSaldo.getTgl()).isEqualTo(DEFAULT_TGL);
        assertThat(testLogSaldo.getKet()).isEqualTo(DEFAULT_KET);
        assertThat(testLogSaldo.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testLogSaldo.getTkode()).isEqualTo(DEFAULT_TKODE);
        assertThat(testLogSaldo.getKodetrx()).isEqualTo(DEFAULT_KODETRX);
        assertThat(testLogSaldo.getMsg()).isEqualTo(DEFAULT_MSG);
        assertThat(testLogSaldo.getUserInput()).isEqualTo(DEFAULT_USER_INPUT);
        assertThat(testLogSaldo.getIsstok()).isEqualTo(DEFAULT_ISSTOK);

        // Validate the LogSaldo in Elasticsearch
        LogSaldo logSaldoEs = logSaldoSearchRepository.findOne(testLogSaldo.getId());
        assertThat(logSaldoEs).isEqualToComparingFieldByField(testLogSaldo);
    }

    @Test
    @Transactional
    public void createLogSaldoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logSaldoRepository.findAll().size();

        // Create the LogSaldo with an existing ID
        logSaldo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogSaldoMockMvc.perform(post("/api/log-saldos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logSaldo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LogSaldo> logSaldoList = logSaldoRepository.findAll();
        assertThat(logSaldoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLogSaldos() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList
        restLogSaldoMockMvc.perform(get("/api/log-saldos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logSaldo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].saldo").value(hasItem(DEFAULT_SALDO.intValue())))
            .andExpect(jsonPath("$.[*].act").value(hasItem(DEFAULT_ACT.intValue())))
            .andExpect(jsonPath("$.[*].tgl").value(hasItem(sameInstant(DEFAULT_TGL))))
            .andExpect(jsonPath("$.[*].ket").value(hasItem(DEFAULT_KET.toString())))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF)))
            .andExpect(jsonPath("$.[*].tkode").value(hasItem(DEFAULT_TKODE)))
            .andExpect(jsonPath("$.[*].kodetrx").value(hasItem(DEFAULT_KODETRX)))
            .andExpect(jsonPath("$.[*].msg").value(hasItem(DEFAULT_MSG.toString())))
            .andExpect(jsonPath("$.[*].userInput").value(hasItem(DEFAULT_USER_INPUT.toString())))
            .andExpect(jsonPath("$.[*].isstok").value(hasItem(DEFAULT_ISSTOK)));
    }

    @Test
    @Transactional
    public void getLogSaldo() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get the logSaldo
        restLogSaldoMockMvc.perform(get("/api/log-saldos/{id}", logSaldo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(logSaldo.getId().intValue()))
            .andExpect(jsonPath("$.idMember").value(DEFAULT_ID_MEMBER.toString()))
            .andExpect(jsonPath("$.saldo").value(DEFAULT_SALDO.intValue()))
            .andExpect(jsonPath("$.act").value(DEFAULT_ACT.intValue()))
            .andExpect(jsonPath("$.tgl").value(sameInstant(DEFAULT_TGL)))
            .andExpect(jsonPath("$.ket").value(DEFAULT_KET.toString()))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF))
            .andExpect(jsonPath("$.tkode").value(DEFAULT_TKODE))
            .andExpect(jsonPath("$.kodetrx").value(DEFAULT_KODETRX))
            .andExpect(jsonPath("$.msg").value(DEFAULT_MSG.toString()))
            .andExpect(jsonPath("$.userInput").value(DEFAULT_USER_INPUT.toString()))
            .andExpect(jsonPath("$.isstok").value(DEFAULT_ISSTOK));
    }

    @Test
    @Transactional
    public void getAllLogSaldosByIdMemberIsEqualToSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where idMember equals to DEFAULT_ID_MEMBER
        defaultLogSaldoShouldBeFound("idMember.equals=" + DEFAULT_ID_MEMBER);

        // Get all the logSaldoList where idMember equals to UPDATED_ID_MEMBER
        defaultLogSaldoShouldNotBeFound("idMember.equals=" + UPDATED_ID_MEMBER);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByIdMemberIsInShouldWork() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where idMember in DEFAULT_ID_MEMBER or UPDATED_ID_MEMBER
        defaultLogSaldoShouldBeFound("idMember.in=" + DEFAULT_ID_MEMBER + "," + UPDATED_ID_MEMBER);

        // Get all the logSaldoList where idMember equals to UPDATED_ID_MEMBER
        defaultLogSaldoShouldNotBeFound("idMember.in=" + UPDATED_ID_MEMBER);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByIdMemberIsNullOrNotNull() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where idMember is not null
        defaultLogSaldoShouldBeFound("idMember.specified=true");

        // Get all the logSaldoList where idMember is null
        defaultLogSaldoShouldNotBeFound("idMember.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogSaldosBySaldoIsEqualToSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where saldo equals to DEFAULT_SALDO
        defaultLogSaldoShouldBeFound("saldo.equals=" + DEFAULT_SALDO);

        // Get all the logSaldoList where saldo equals to UPDATED_SALDO
        defaultLogSaldoShouldNotBeFound("saldo.equals=" + UPDATED_SALDO);
    }

    @Test
    @Transactional
    public void getAllLogSaldosBySaldoIsInShouldWork() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where saldo in DEFAULT_SALDO or UPDATED_SALDO
        defaultLogSaldoShouldBeFound("saldo.in=" + DEFAULT_SALDO + "," + UPDATED_SALDO);

        // Get all the logSaldoList where saldo equals to UPDATED_SALDO
        defaultLogSaldoShouldNotBeFound("saldo.in=" + UPDATED_SALDO);
    }

    @Test
    @Transactional
    public void getAllLogSaldosBySaldoIsNullOrNotNull() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where saldo is not null
        defaultLogSaldoShouldBeFound("saldo.specified=true");

        // Get all the logSaldoList where saldo is null
        defaultLogSaldoShouldNotBeFound("saldo.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogSaldosByActIsEqualToSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where act equals to DEFAULT_ACT
        defaultLogSaldoShouldBeFound("act.equals=" + DEFAULT_ACT);

        // Get all the logSaldoList where act equals to UPDATED_ACT
        defaultLogSaldoShouldNotBeFound("act.equals=" + UPDATED_ACT);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByActIsInShouldWork() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where act in DEFAULT_ACT or UPDATED_ACT
        defaultLogSaldoShouldBeFound("act.in=" + DEFAULT_ACT + "," + UPDATED_ACT);

        // Get all the logSaldoList where act equals to UPDATED_ACT
        defaultLogSaldoShouldNotBeFound("act.in=" + UPDATED_ACT);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByActIsNullOrNotNull() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where act is not null
        defaultLogSaldoShouldBeFound("act.specified=true");

        // Get all the logSaldoList where act is null
        defaultLogSaldoShouldNotBeFound("act.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogSaldosByTglIsEqualToSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where tgl equals to DEFAULT_TGL
        defaultLogSaldoShouldBeFound("tgl.equals=" + DEFAULT_TGL);

        // Get all the logSaldoList where tgl equals to UPDATED_TGL
        defaultLogSaldoShouldNotBeFound("tgl.equals=" + UPDATED_TGL);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByTglIsInShouldWork() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where tgl in DEFAULT_TGL or UPDATED_TGL
        defaultLogSaldoShouldBeFound("tgl.in=" + DEFAULT_TGL + "," + UPDATED_TGL);

        // Get all the logSaldoList where tgl equals to UPDATED_TGL
        defaultLogSaldoShouldNotBeFound("tgl.in=" + UPDATED_TGL);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByTglIsNullOrNotNull() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where tgl is not null
        defaultLogSaldoShouldBeFound("tgl.specified=true");

        // Get all the logSaldoList where tgl is null
        defaultLogSaldoShouldNotBeFound("tgl.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogSaldosByTglIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where tgl greater than or equals to DEFAULT_TGL
        defaultLogSaldoShouldBeFound("tgl.greaterOrEqualThan=" + DEFAULT_TGL);

        // Get all the logSaldoList where tgl greater than or equals to UPDATED_TGL
        defaultLogSaldoShouldNotBeFound("tgl.greaterOrEqualThan=" + UPDATED_TGL);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByTglIsLessThanSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where tgl less than or equals to DEFAULT_TGL
        defaultLogSaldoShouldNotBeFound("tgl.lessThan=" + DEFAULT_TGL);

        // Get all the logSaldoList where tgl less than or equals to UPDATED_TGL
        defaultLogSaldoShouldBeFound("tgl.lessThan=" + UPDATED_TGL);
    }


    @Test
    @Transactional
    public void getAllLogSaldosByKetIsEqualToSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where ket equals to DEFAULT_KET
        defaultLogSaldoShouldBeFound("ket.equals=" + DEFAULT_KET);

        // Get all the logSaldoList where ket equals to UPDATED_KET
        defaultLogSaldoShouldNotBeFound("ket.equals=" + UPDATED_KET);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByKetIsInShouldWork() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where ket in DEFAULT_KET or UPDATED_KET
        defaultLogSaldoShouldBeFound("ket.in=" + DEFAULT_KET + "," + UPDATED_KET);

        // Get all the logSaldoList where ket equals to UPDATED_KET
        defaultLogSaldoShouldNotBeFound("ket.in=" + UPDATED_KET);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByKetIsNullOrNotNull() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where ket is not null
        defaultLogSaldoShouldBeFound("ket.specified=true");

        // Get all the logSaldoList where ket is null
        defaultLogSaldoShouldNotBeFound("ket.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogSaldosByRefIsEqualToSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where ref equals to DEFAULT_REF
        defaultLogSaldoShouldBeFound("ref.equals=" + DEFAULT_REF);

        // Get all the logSaldoList where ref equals to UPDATED_REF
        defaultLogSaldoShouldNotBeFound("ref.equals=" + UPDATED_REF);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByRefIsInShouldWork() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where ref in DEFAULT_REF or UPDATED_REF
        defaultLogSaldoShouldBeFound("ref.in=" + DEFAULT_REF + "," + UPDATED_REF);

        // Get all the logSaldoList where ref equals to UPDATED_REF
        defaultLogSaldoShouldNotBeFound("ref.in=" + UPDATED_REF);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByRefIsNullOrNotNull() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where ref is not null
        defaultLogSaldoShouldBeFound("ref.specified=true");

        // Get all the logSaldoList where ref is null
        defaultLogSaldoShouldNotBeFound("ref.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogSaldosByRefIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where ref greater than or equals to DEFAULT_REF
        defaultLogSaldoShouldBeFound("ref.greaterOrEqualThan=" + DEFAULT_REF);

        // Get all the logSaldoList where ref greater than or equals to UPDATED_REF
        defaultLogSaldoShouldNotBeFound("ref.greaterOrEqualThan=" + UPDATED_REF);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByRefIsLessThanSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where ref less than or equals to DEFAULT_REF
        defaultLogSaldoShouldNotBeFound("ref.lessThan=" + DEFAULT_REF);

        // Get all the logSaldoList where ref less than or equals to UPDATED_REF
        defaultLogSaldoShouldBeFound("ref.lessThan=" + UPDATED_REF);
    }


    @Test
    @Transactional
    public void getAllLogSaldosByTkodeIsEqualToSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where tkode equals to DEFAULT_TKODE
        defaultLogSaldoShouldBeFound("tkode.equals=" + DEFAULT_TKODE);

        // Get all the logSaldoList where tkode equals to UPDATED_TKODE
        defaultLogSaldoShouldNotBeFound("tkode.equals=" + UPDATED_TKODE);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByTkodeIsInShouldWork() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where tkode in DEFAULT_TKODE or UPDATED_TKODE
        defaultLogSaldoShouldBeFound("tkode.in=" + DEFAULT_TKODE + "," + UPDATED_TKODE);

        // Get all the logSaldoList where tkode equals to UPDATED_TKODE
        defaultLogSaldoShouldNotBeFound("tkode.in=" + UPDATED_TKODE);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByTkodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where tkode is not null
        defaultLogSaldoShouldBeFound("tkode.specified=true");

        // Get all the logSaldoList where tkode is null
        defaultLogSaldoShouldNotBeFound("tkode.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogSaldosByTkodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where tkode greater than or equals to DEFAULT_TKODE
        defaultLogSaldoShouldBeFound("tkode.greaterOrEqualThan=" + DEFAULT_TKODE);

        // Get all the logSaldoList where tkode greater than or equals to UPDATED_TKODE
        defaultLogSaldoShouldNotBeFound("tkode.greaterOrEqualThan=" + UPDATED_TKODE);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByTkodeIsLessThanSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where tkode less than or equals to DEFAULT_TKODE
        defaultLogSaldoShouldNotBeFound("tkode.lessThan=" + DEFAULT_TKODE);

        // Get all the logSaldoList where tkode less than or equals to UPDATED_TKODE
        defaultLogSaldoShouldBeFound("tkode.lessThan=" + UPDATED_TKODE);
    }


    @Test
    @Transactional
    public void getAllLogSaldosByKodetrxIsEqualToSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where kodetrx equals to DEFAULT_KODETRX
        defaultLogSaldoShouldBeFound("kodetrx.equals=" + DEFAULT_KODETRX);

        // Get all the logSaldoList where kodetrx equals to UPDATED_KODETRX
        defaultLogSaldoShouldNotBeFound("kodetrx.equals=" + UPDATED_KODETRX);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByKodetrxIsInShouldWork() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where kodetrx in DEFAULT_KODETRX or UPDATED_KODETRX
        defaultLogSaldoShouldBeFound("kodetrx.in=" + DEFAULT_KODETRX + "," + UPDATED_KODETRX);

        // Get all the logSaldoList where kodetrx equals to UPDATED_KODETRX
        defaultLogSaldoShouldNotBeFound("kodetrx.in=" + UPDATED_KODETRX);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByKodetrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where kodetrx is not null
        defaultLogSaldoShouldBeFound("kodetrx.specified=true");

        // Get all the logSaldoList where kodetrx is null
        defaultLogSaldoShouldNotBeFound("kodetrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogSaldosByKodetrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where kodetrx greater than or equals to DEFAULT_KODETRX
        defaultLogSaldoShouldBeFound("kodetrx.greaterOrEqualThan=" + DEFAULT_KODETRX);

        // Get all the logSaldoList where kodetrx greater than or equals to UPDATED_KODETRX
        defaultLogSaldoShouldNotBeFound("kodetrx.greaterOrEqualThan=" + UPDATED_KODETRX);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByKodetrxIsLessThanSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where kodetrx less than or equals to DEFAULT_KODETRX
        defaultLogSaldoShouldNotBeFound("kodetrx.lessThan=" + DEFAULT_KODETRX);

        // Get all the logSaldoList where kodetrx less than or equals to UPDATED_KODETRX
        defaultLogSaldoShouldBeFound("kodetrx.lessThan=" + UPDATED_KODETRX);
    }


    @Test
    @Transactional
    public void getAllLogSaldosByMsgIsEqualToSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where msg equals to DEFAULT_MSG
        defaultLogSaldoShouldBeFound("msg.equals=" + DEFAULT_MSG);

        // Get all the logSaldoList where msg equals to UPDATED_MSG
        defaultLogSaldoShouldNotBeFound("msg.equals=" + UPDATED_MSG);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByMsgIsInShouldWork() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where msg in DEFAULT_MSG or UPDATED_MSG
        defaultLogSaldoShouldBeFound("msg.in=" + DEFAULT_MSG + "," + UPDATED_MSG);

        // Get all the logSaldoList where msg equals to UPDATED_MSG
        defaultLogSaldoShouldNotBeFound("msg.in=" + UPDATED_MSG);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByMsgIsNullOrNotNull() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where msg is not null
        defaultLogSaldoShouldBeFound("msg.specified=true");

        // Get all the logSaldoList where msg is null
        defaultLogSaldoShouldNotBeFound("msg.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogSaldosByUserInputIsEqualToSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where userInput equals to DEFAULT_USER_INPUT
        defaultLogSaldoShouldBeFound("userInput.equals=" + DEFAULT_USER_INPUT);

        // Get all the logSaldoList where userInput equals to UPDATED_USER_INPUT
        defaultLogSaldoShouldNotBeFound("userInput.equals=" + UPDATED_USER_INPUT);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByUserInputIsInShouldWork() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where userInput in DEFAULT_USER_INPUT or UPDATED_USER_INPUT
        defaultLogSaldoShouldBeFound("userInput.in=" + DEFAULT_USER_INPUT + "," + UPDATED_USER_INPUT);

        // Get all the logSaldoList where userInput equals to UPDATED_USER_INPUT
        defaultLogSaldoShouldNotBeFound("userInput.in=" + UPDATED_USER_INPUT);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByUserInputIsNullOrNotNull() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where userInput is not null
        defaultLogSaldoShouldBeFound("userInput.specified=true");

        // Get all the logSaldoList where userInput is null
        defaultLogSaldoShouldNotBeFound("userInput.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogSaldosByIsstokIsEqualToSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where isstok equals to DEFAULT_ISSTOK
        defaultLogSaldoShouldBeFound("isstok.equals=" + DEFAULT_ISSTOK);

        // Get all the logSaldoList where isstok equals to UPDATED_ISSTOK
        defaultLogSaldoShouldNotBeFound("isstok.equals=" + UPDATED_ISSTOK);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByIsstokIsInShouldWork() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where isstok in DEFAULT_ISSTOK or UPDATED_ISSTOK
        defaultLogSaldoShouldBeFound("isstok.in=" + DEFAULT_ISSTOK + "," + UPDATED_ISSTOK);

        // Get all the logSaldoList where isstok equals to UPDATED_ISSTOK
        defaultLogSaldoShouldNotBeFound("isstok.in=" + UPDATED_ISSTOK);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByIsstokIsNullOrNotNull() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where isstok is not null
        defaultLogSaldoShouldBeFound("isstok.specified=true");

        // Get all the logSaldoList where isstok is null
        defaultLogSaldoShouldNotBeFound("isstok.specified=false");
    }

    @Test
    @Transactional
    public void getAllLogSaldosByIsstokIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where isstok greater than or equals to DEFAULT_ISSTOK
        defaultLogSaldoShouldBeFound("isstok.greaterOrEqualThan=" + DEFAULT_ISSTOK);

        // Get all the logSaldoList where isstok greater than or equals to UPDATED_ISSTOK
        defaultLogSaldoShouldNotBeFound("isstok.greaterOrEqualThan=" + UPDATED_ISSTOK);
    }

    @Test
    @Transactional
    public void getAllLogSaldosByIsstokIsLessThanSomething() throws Exception {
        // Initialize the database
        logSaldoRepository.saveAndFlush(logSaldo);

        // Get all the logSaldoList where isstok less than or equals to DEFAULT_ISSTOK
        defaultLogSaldoShouldNotBeFound("isstok.lessThan=" + DEFAULT_ISSTOK);

        // Get all the logSaldoList where isstok less than or equals to UPDATED_ISSTOK
        defaultLogSaldoShouldBeFound("isstok.lessThan=" + UPDATED_ISSTOK);
    }


    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultLogSaldoShouldBeFound(String filter) throws Exception {
        restLogSaldoMockMvc.perform(get("/api/log-saldos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logSaldo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].saldo").value(hasItem(DEFAULT_SALDO.intValue())))
            .andExpect(jsonPath("$.[*].act").value(hasItem(DEFAULT_ACT.intValue())))
            .andExpect(jsonPath("$.[*].tgl").value(hasItem(sameInstant(DEFAULT_TGL))))
            .andExpect(jsonPath("$.[*].ket").value(hasItem(DEFAULT_KET.toString())))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF)))
            .andExpect(jsonPath("$.[*].tkode").value(hasItem(DEFAULT_TKODE)))
            .andExpect(jsonPath("$.[*].kodetrx").value(hasItem(DEFAULT_KODETRX)))
            .andExpect(jsonPath("$.[*].msg").value(hasItem(DEFAULT_MSG.toString())))
            .andExpect(jsonPath("$.[*].userInput").value(hasItem(DEFAULT_USER_INPUT.toString())))
            .andExpect(jsonPath("$.[*].isstok").value(hasItem(DEFAULT_ISSTOK)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultLogSaldoShouldNotBeFound(String filter) throws Exception {
        restLogSaldoMockMvc.perform(get("/api/log-saldos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingLogSaldo() throws Exception {
        // Get the logSaldo
        restLogSaldoMockMvc.perform(get("/api/log-saldos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogSaldo() throws Exception {
        // Initialize the database
        logSaldoService.save(logSaldo);

        int databaseSizeBeforeUpdate = logSaldoRepository.findAll().size();

        // Update the logSaldo
        LogSaldo updatedLogSaldo = logSaldoRepository.findOne(logSaldo.getId());
        updatedLogSaldo
            .idMember(UPDATED_ID_MEMBER)
            .saldo(UPDATED_SALDO)
            .act(UPDATED_ACT)
            .tgl(UPDATED_TGL)
            .ket(UPDATED_KET)
            .ref(UPDATED_REF)
            .tkode(UPDATED_TKODE)
            .kodetrx(UPDATED_KODETRX)
            .msg(UPDATED_MSG)
            .userInput(UPDATED_USER_INPUT)
            .isstok(UPDATED_ISSTOK);

        restLogSaldoMockMvc.perform(put("/api/log-saldos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLogSaldo)))
            .andExpect(status().isOk());

        // Validate the LogSaldo in the database
        List<LogSaldo> logSaldoList = logSaldoRepository.findAll();
        assertThat(logSaldoList).hasSize(databaseSizeBeforeUpdate);
        LogSaldo testLogSaldo = logSaldoList.get(logSaldoList.size() - 1);
        assertThat(testLogSaldo.getIdMember()).isEqualTo(UPDATED_ID_MEMBER);
        assertThat(testLogSaldo.getSaldo()).isEqualTo(UPDATED_SALDO);
        assertThat(testLogSaldo.getAct()).isEqualTo(UPDATED_ACT);
        assertThat(testLogSaldo.getTgl()).isEqualTo(UPDATED_TGL);
        assertThat(testLogSaldo.getKet()).isEqualTo(UPDATED_KET);
        assertThat(testLogSaldo.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testLogSaldo.getTkode()).isEqualTo(UPDATED_TKODE);
        assertThat(testLogSaldo.getKodetrx()).isEqualTo(UPDATED_KODETRX);
        assertThat(testLogSaldo.getMsg()).isEqualTo(UPDATED_MSG);
        assertThat(testLogSaldo.getUserInput()).isEqualTo(UPDATED_USER_INPUT);
        assertThat(testLogSaldo.getIsstok()).isEqualTo(UPDATED_ISSTOK);

        // Validate the LogSaldo in Elasticsearch
        LogSaldo logSaldoEs = logSaldoSearchRepository.findOne(testLogSaldo.getId());
        assertThat(logSaldoEs).isEqualToComparingFieldByField(testLogSaldo);
    }

    @Test
    @Transactional
    public void updateNonExistingLogSaldo() throws Exception {
        int databaseSizeBeforeUpdate = logSaldoRepository.findAll().size();

        // Create the LogSaldo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLogSaldoMockMvc.perform(put("/api/log-saldos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logSaldo)))
            .andExpect(status().isCreated());

        // Validate the LogSaldo in the database
        List<LogSaldo> logSaldoList = logSaldoRepository.findAll();
        assertThat(logSaldoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLogSaldo() throws Exception {
        // Initialize the database
        logSaldoService.save(logSaldo);

        int databaseSizeBeforeDelete = logSaldoRepository.findAll().size();

        // Get the logSaldo
        restLogSaldoMockMvc.perform(delete("/api/log-saldos/{id}", logSaldo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean logSaldoExistsInEs = logSaldoSearchRepository.exists(logSaldo.getId());
        assertThat(logSaldoExistsInEs).isFalse();

        // Validate the database is empty
        List<LogSaldo> logSaldoList = logSaldoRepository.findAll();
        assertThat(logSaldoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLogSaldo() throws Exception {
        // Initialize the database
        logSaldoService.save(logSaldo);

        // Search the logSaldo
        restLogSaldoMockMvc.perform(get("/api/_search/log-saldos?query=id:" + logSaldo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logSaldo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].saldo").value(hasItem(DEFAULT_SALDO.intValue())))
            .andExpect(jsonPath("$.[*].act").value(hasItem(DEFAULT_ACT.intValue())))
            .andExpect(jsonPath("$.[*].tgl").value(hasItem(sameInstant(DEFAULT_TGL))))
            .andExpect(jsonPath("$.[*].ket").value(hasItem(DEFAULT_KET.toString())))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF)))
            .andExpect(jsonPath("$.[*].tkode").value(hasItem(DEFAULT_TKODE)))
            .andExpect(jsonPath("$.[*].kodetrx").value(hasItem(DEFAULT_KODETRX)))
            .andExpect(jsonPath("$.[*].msg").value(hasItem(DEFAULT_MSG.toString())))
            .andExpect(jsonPath("$.[*].userInput").value(hasItem(DEFAULT_USER_INPUT.toString())))
            .andExpect(jsonPath("$.[*].isstok").value(hasItem(DEFAULT_ISSTOK)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogSaldo.class);
        LogSaldo logSaldo1 = new LogSaldo();
        logSaldo1.setId(1L);
        LogSaldo logSaldo2 = new LogSaldo();
        logSaldo2.setId(logSaldo1.getId());
        assertThat(logSaldo1).isEqualTo(logSaldo2);
        logSaldo2.setId(2L);
        assertThat(logSaldo1).isNotEqualTo(logSaldo2);
        logSaldo1.setId(null);
        assertThat(logSaldo1).isNotEqualTo(logSaldo2);
    }
}
