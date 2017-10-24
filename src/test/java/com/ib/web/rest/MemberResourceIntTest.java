package com.ib.web.rest;

import com.ib.AyoappApp;

import com.ib.domain.Member;
import com.ib.repository.MemberRepository;
import com.ib.service.MemberService;
import com.ib.repository.search.MemberSearchRepository;
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
import java.time.LocalDate;
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
 * Test class for the MemberResource REST controller.
 *
 * @see MemberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AyoappApp.class)
public class MemberResourceIntTest {

    private static final String DEFAULT_ID_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_ID_MEMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TGL_REGISTER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TGL_REGISTER = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    private static final String DEFAULT_ALAMAT = "AAAAAAAAAA";
    private static final String UPDATED_ALAMAT = "BBBBBBBBBB";

    private static final String DEFAULT_PIN = "AAAAAAAAAA";
    private static final String UPDATED_PIN = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final BigDecimal DEFAULT_SALDO = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALDO = new BigDecimal(2);

    private static final String DEFAULT_ID_UPLINE = "AAAAAAAAAA";
    private static final String UPDATED_ID_UPLINE = "BBBBBBBBBB";

    private static final String DEFAULT_KODE_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_KODE_LEVEL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TGL_INPUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TGL_INPUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_USER_INPUT = "AAAAAAAAAA";
    private static final String UPDATED_USER_INPUT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TGL_UPDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TGL_UPDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_USER_UPDATE = "AAAAAAAAAA";
    private static final String UPDATED_USER_UPDATE = "BBBBBBBBBB";

    private static final String DEFAULT_ID_MASTER = "AAAAAAAAAA";
    private static final String UPDATED_ID_MASTER = "BBBBBBBBBB";

    private static final Integer DEFAULT_RPT_TRX = 1;
    private static final Integer UPDATED_RPT_TRX = 2;

    private static final BigDecimal DEFAULT_SELISIH = new BigDecimal(1);
    private static final BigDecimal UPDATED_SELISIH = new BigDecimal(2);

    private static final Integer DEFAULT_COUNTER = 1;
    private static final Integer UPDATED_COUNTER = 2;

    private static final String DEFAULT_DONGLE_NO = "AAAAAAAAAA";
    private static final String UPDATED_DONGLE_NO = "BBBBBBBBBB";

    private static final Integer DEFAULT_HEAD_2_HEAD = 1;
    private static final Integer UPDATED_HEAD_2_HEAD = 2;

    private static final String DEFAULT_YMID = "AAAAAAAAAA";
    private static final String UPDATED_YMID = "BBBBBBBBBB";

    private static final String DEFAULT_IPRPT = "AAAAAAAAAA";
    private static final String UPDATED_IPRPT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_TRX = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_TRX = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_REF = "AAAAAAAAAA";
    private static final String UPDATED_REF = "BBBBBBBBBB";

    private static final String DEFAULT_CRYPT = "AAAAAAAAAA";
    private static final String UPDATED_CRYPT = "BBBBBBBBBB";

    private static final String DEFAULT_GTALKID = "AAAAAAAAAA";
    private static final String UPDATED_GTALKID = "BBBBBBBBBB";

    private static final String DEFAULT_VREMSGID = "AAAAAAAAAA";
    private static final String UPDATED_VREMSGID = "BBBBBBBBBB";

    private static final String DEFAULT_KODEPOS = "AAAAAAAAAA";
    private static final String UPDATED_KODEPOS = "BBBBBBBBBB";

    private static final Integer DEFAULT_ISWARN = 1;
    private static final Integer UPDATED_ISWARN = 2;

    private static final String DEFAULT_MSNID = "AAAAAAAAAA";
    private static final String UPDATED_MSNID = "BBBBBBBBBB";

    private static final Long DEFAULT_IDLOGSAL = 1L;
    private static final Long UPDATED_IDLOGSAL = 2L;

    private static final Long DEFAULT_LAST_KODETRX = 1L;
    private static final Long UPDATED_LAST_KODETRX = 2L;

    private static final Long DEFAULT_LAST_IDTRX = 1L;
    private static final Long UPDATED_LAST_IDTRX = 2L;

    private static final String DEFAULT_TELEBOTID = "AAAAAAAAAA";
    private static final String UPDATED_TELEBOTID = "BBBBBBBBBB";

    private static final String DEFAULT_TELEGRAMID = "AAAAAAAAAA";
    private static final String UPDATED_TELEGRAMID = "BBBBBBBBBB";

    private static final Long DEFAULT_ISOWNER = 1L;
    private static final Long UPDATED_ISOWNER = 2L;

    private static final String DEFAULT_CRYPTOWNER = "AAAAAAAAAA";
    private static final String UPDATED_CRYPTOWNER = "BBBBBBBBBB";

    private static final String DEFAULT_PINOWNER = "AAAAAAAAAA";
    private static final String UPDATED_PINOWNER = "BBBBBBBBBB";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberSearchRepository memberSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMemberMockMvc;

    private Member member;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MemberResource memberResource = new MemberResource(memberService);
        this.restMemberMockMvc = MockMvcBuilders.standaloneSetup(memberResource)
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
    public static Member createEntity(EntityManager em) {
        Member member = new Member()
            .idMember(DEFAULT_ID_MEMBER)
            .tglRegister(DEFAULT_TGL_REGISTER)
            .nama(DEFAULT_NAMA)
            .alamat(DEFAULT_ALAMAT)
            .pin(DEFAULT_PIN)
            .status(DEFAULT_STATUS)
            .saldo(DEFAULT_SALDO)
            .idUpline(DEFAULT_ID_UPLINE)
            .kodeLevel(DEFAULT_KODE_LEVEL)
            .tglInput(DEFAULT_TGL_INPUT)
            .userInput(DEFAULT_USER_INPUT)
            .tglUpdate(DEFAULT_TGL_UPDATE)
            .userUpdate(DEFAULT_USER_UPDATE)
            .idMaster(DEFAULT_ID_MASTER)
            .rptTrx(DEFAULT_RPT_TRX)
            .selisih(DEFAULT_SELISIH)
            .counter(DEFAULT_COUNTER)
            .dongleNo(DEFAULT_DONGLE_NO)
            .head2head(DEFAULT_HEAD_2_HEAD)
            .ymid(DEFAULT_YMID)
            .iprpt(DEFAULT_IPRPT)
            .lastTrx(DEFAULT_LAST_TRX)
            .ref(DEFAULT_REF)
            .crypt(DEFAULT_CRYPT)
            .gtalkid(DEFAULT_GTALKID)
            .vremsgid(DEFAULT_VREMSGID)
            .kodepos(DEFAULT_KODEPOS)
            .iswarn(DEFAULT_ISWARN)
            .msnid(DEFAULT_MSNID)
            .idlogsal(DEFAULT_IDLOGSAL)
            .lastKodetrx(DEFAULT_LAST_KODETRX)
            .lastIdtrx(DEFAULT_LAST_IDTRX)
            .telebotid(DEFAULT_TELEBOTID)
            .telegramid(DEFAULT_TELEGRAMID)
            .isowner(DEFAULT_ISOWNER)
            .cryptowner(DEFAULT_CRYPTOWNER)
            .pinowner(DEFAULT_PINOWNER);
        return member;
    }

    @Before
    public void initTest() {
        memberSearchRepository.deleteAll();
        member = createEntity(em);
    }

    @Test
    @Transactional
    public void createMember() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member
        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate + 1);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getIdMember()).isEqualTo(DEFAULT_ID_MEMBER);
        assertThat(testMember.getTglRegister()).isEqualTo(DEFAULT_TGL_REGISTER);
        assertThat(testMember.getNama()).isEqualTo(DEFAULT_NAMA);
        assertThat(testMember.getAlamat()).isEqualTo(DEFAULT_ALAMAT);
        assertThat(testMember.getPin()).isEqualTo(DEFAULT_PIN);
        assertThat(testMember.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMember.getSaldo()).isEqualTo(DEFAULT_SALDO);
        assertThat(testMember.getIdUpline()).isEqualTo(DEFAULT_ID_UPLINE);
        assertThat(testMember.getKodeLevel()).isEqualTo(DEFAULT_KODE_LEVEL);
        assertThat(testMember.getTglInput()).isEqualTo(DEFAULT_TGL_INPUT);
        assertThat(testMember.getUserInput()).isEqualTo(DEFAULT_USER_INPUT);
        assertThat(testMember.getTglUpdate()).isEqualTo(DEFAULT_TGL_UPDATE);
        assertThat(testMember.getUserUpdate()).isEqualTo(DEFAULT_USER_UPDATE);
        assertThat(testMember.getIdMaster()).isEqualTo(DEFAULT_ID_MASTER);
        assertThat(testMember.getRptTrx()).isEqualTo(DEFAULT_RPT_TRX);
        assertThat(testMember.getSelisih()).isEqualTo(DEFAULT_SELISIH);
        assertThat(testMember.getCounter()).isEqualTo(DEFAULT_COUNTER);
        assertThat(testMember.getDongleNo()).isEqualTo(DEFAULT_DONGLE_NO);
        assertThat(testMember.getHead2head()).isEqualTo(DEFAULT_HEAD_2_HEAD);
        assertThat(testMember.getYmid()).isEqualTo(DEFAULT_YMID);
        assertThat(testMember.getIprpt()).isEqualTo(DEFAULT_IPRPT);
        assertThat(testMember.getLastTrx()).isEqualTo(DEFAULT_LAST_TRX);
        assertThat(testMember.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testMember.getCrypt()).isEqualTo(DEFAULT_CRYPT);
        assertThat(testMember.getGtalkid()).isEqualTo(DEFAULT_GTALKID);
        assertThat(testMember.getVremsgid()).isEqualTo(DEFAULT_VREMSGID);
        assertThat(testMember.getKodepos()).isEqualTo(DEFAULT_KODEPOS);
        assertThat(testMember.getIswarn()).isEqualTo(DEFAULT_ISWARN);
        assertThat(testMember.getMsnid()).isEqualTo(DEFAULT_MSNID);
        assertThat(testMember.getIdlogsal()).isEqualTo(DEFAULT_IDLOGSAL);
        assertThat(testMember.getLastKodetrx()).isEqualTo(DEFAULT_LAST_KODETRX);
        assertThat(testMember.getLastIdtrx()).isEqualTo(DEFAULT_LAST_IDTRX);
        assertThat(testMember.getTelebotid()).isEqualTo(DEFAULT_TELEBOTID);
        assertThat(testMember.getTelegramid()).isEqualTo(DEFAULT_TELEGRAMID);
        assertThat(testMember.getIsowner()).isEqualTo(DEFAULT_ISOWNER);
        assertThat(testMember.getCryptowner()).isEqualTo(DEFAULT_CRYPTOWNER);
        assertThat(testMember.getPinowner()).isEqualTo(DEFAULT_PINOWNER);

        // Validate the Member in Elasticsearch
        Member memberEs = memberSearchRepository.findOne(testMember.getId());
        assertThat(memberEs).isEqualToComparingFieldByField(testMember);
    }

    @Test
    @Transactional
    public void createMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member with an existing ID
        member.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdMemberIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setIdMember(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTglRegisterIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setTglRegister(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSaldoIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setSaldo(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRptTrxIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setRptTrx(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSelisihIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setSelisih(null);

        // Create the Member, which fails.

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMembers() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList
        restMemberMockMvc.perform(get("/api/members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].tglRegister").value(hasItem(DEFAULT_TGL_REGISTER.toString())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].alamat").value(hasItem(DEFAULT_ALAMAT.toString())))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].saldo").value(hasItem(DEFAULT_SALDO.intValue())))
            .andExpect(jsonPath("$.[*].idUpline").value(hasItem(DEFAULT_ID_UPLINE.toString())))
            .andExpect(jsonPath("$.[*].kodeLevel").value(hasItem(DEFAULT_KODE_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].tglInput").value(hasItem(sameInstant(DEFAULT_TGL_INPUT))))
            .andExpect(jsonPath("$.[*].userInput").value(hasItem(DEFAULT_USER_INPUT.toString())))
            .andExpect(jsonPath("$.[*].tglUpdate").value(hasItem(sameInstant(DEFAULT_TGL_UPDATE))))
            .andExpect(jsonPath("$.[*].userUpdate").value(hasItem(DEFAULT_USER_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].idMaster").value(hasItem(DEFAULT_ID_MASTER.toString())))
            .andExpect(jsonPath("$.[*].rptTrx").value(hasItem(DEFAULT_RPT_TRX)))
            .andExpect(jsonPath("$.[*].selisih").value(hasItem(DEFAULT_SELISIH.intValue())))
            .andExpect(jsonPath("$.[*].counter").value(hasItem(DEFAULT_COUNTER)))
            .andExpect(jsonPath("$.[*].dongleNo").value(hasItem(DEFAULT_DONGLE_NO.toString())))
            .andExpect(jsonPath("$.[*].head2head").value(hasItem(DEFAULT_HEAD_2_HEAD)))
            .andExpect(jsonPath("$.[*].ymid").value(hasItem(DEFAULT_YMID.toString())))
            .andExpect(jsonPath("$.[*].iprpt").value(hasItem(DEFAULT_IPRPT.toString())))
            .andExpect(jsonPath("$.[*].lastTrx").value(hasItem(sameInstant(DEFAULT_LAST_TRX))))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF.toString())))
            .andExpect(jsonPath("$.[*].crypt").value(hasItem(DEFAULT_CRYPT.toString())))
            .andExpect(jsonPath("$.[*].gtalkid").value(hasItem(DEFAULT_GTALKID.toString())))
            .andExpect(jsonPath("$.[*].vremsgid").value(hasItem(DEFAULT_VREMSGID.toString())))
            .andExpect(jsonPath("$.[*].kodepos").value(hasItem(DEFAULT_KODEPOS.toString())))
            .andExpect(jsonPath("$.[*].iswarn").value(hasItem(DEFAULT_ISWARN)))
            .andExpect(jsonPath("$.[*].msnid").value(hasItem(DEFAULT_MSNID.toString())))
            .andExpect(jsonPath("$.[*].idlogsal").value(hasItem(DEFAULT_IDLOGSAL.intValue())))
            .andExpect(jsonPath("$.[*].lastKodetrx").value(hasItem(DEFAULT_LAST_KODETRX.intValue())))
            .andExpect(jsonPath("$.[*].lastIdtrx").value(hasItem(DEFAULT_LAST_IDTRX.intValue())))
            .andExpect(jsonPath("$.[*].telebotid").value(hasItem(DEFAULT_TELEBOTID.toString())))
            .andExpect(jsonPath("$.[*].telegramid").value(hasItem(DEFAULT_TELEGRAMID.toString())))
            .andExpect(jsonPath("$.[*].isowner").value(hasItem(DEFAULT_ISOWNER.intValue())))
            .andExpect(jsonPath("$.[*].cryptowner").value(hasItem(DEFAULT_CRYPTOWNER.toString())))
            .andExpect(jsonPath("$.[*].pinowner").value(hasItem(DEFAULT_PINOWNER.toString())));
    }

    @Test
    @Transactional
    public void getMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(member.getId().intValue()))
            .andExpect(jsonPath("$.idMember").value(DEFAULT_ID_MEMBER.toString()))
            .andExpect(jsonPath("$.tglRegister").value(DEFAULT_TGL_REGISTER.toString()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()))
            .andExpect(jsonPath("$.alamat").value(DEFAULT_ALAMAT.toString()))
            .andExpect(jsonPath("$.pin").value(DEFAULT_PIN.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.saldo").value(DEFAULT_SALDO.intValue()))
            .andExpect(jsonPath("$.idUpline").value(DEFAULT_ID_UPLINE.toString()))
            .andExpect(jsonPath("$.kodeLevel").value(DEFAULT_KODE_LEVEL.toString()))
            .andExpect(jsonPath("$.tglInput").value(sameInstant(DEFAULT_TGL_INPUT)))
            .andExpect(jsonPath("$.userInput").value(DEFAULT_USER_INPUT.toString()))
            .andExpect(jsonPath("$.tglUpdate").value(sameInstant(DEFAULT_TGL_UPDATE)))
            .andExpect(jsonPath("$.userUpdate").value(DEFAULT_USER_UPDATE.toString()))
            .andExpect(jsonPath("$.idMaster").value(DEFAULT_ID_MASTER.toString()))
            .andExpect(jsonPath("$.rptTrx").value(DEFAULT_RPT_TRX))
            .andExpect(jsonPath("$.selisih").value(DEFAULT_SELISIH.intValue()))
            .andExpect(jsonPath("$.counter").value(DEFAULT_COUNTER))
            .andExpect(jsonPath("$.dongleNo").value(DEFAULT_DONGLE_NO.toString()))
            .andExpect(jsonPath("$.head2head").value(DEFAULT_HEAD_2_HEAD))
            .andExpect(jsonPath("$.ymid").value(DEFAULT_YMID.toString()))
            .andExpect(jsonPath("$.iprpt").value(DEFAULT_IPRPT.toString()))
            .andExpect(jsonPath("$.lastTrx").value(sameInstant(DEFAULT_LAST_TRX)))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF.toString()))
            .andExpect(jsonPath("$.crypt").value(DEFAULT_CRYPT.toString()))
            .andExpect(jsonPath("$.gtalkid").value(DEFAULT_GTALKID.toString()))
            .andExpect(jsonPath("$.vremsgid").value(DEFAULT_VREMSGID.toString()))
            .andExpect(jsonPath("$.kodepos").value(DEFAULT_KODEPOS.toString()))
            .andExpect(jsonPath("$.iswarn").value(DEFAULT_ISWARN))
            .andExpect(jsonPath("$.msnid").value(DEFAULT_MSNID.toString()))
            .andExpect(jsonPath("$.idlogsal").value(DEFAULT_IDLOGSAL.intValue()))
            .andExpect(jsonPath("$.lastKodetrx").value(DEFAULT_LAST_KODETRX.intValue()))
            .andExpect(jsonPath("$.lastIdtrx").value(DEFAULT_LAST_IDTRX.intValue()))
            .andExpect(jsonPath("$.telebotid").value(DEFAULT_TELEBOTID.toString()))
            .andExpect(jsonPath("$.telegramid").value(DEFAULT_TELEGRAMID.toString()))
            .andExpect(jsonPath("$.isowner").value(DEFAULT_ISOWNER.intValue()))
            .andExpect(jsonPath("$.cryptowner").value(DEFAULT_CRYPTOWNER.toString()))
            .andExpect(jsonPath("$.pinowner").value(DEFAULT_PINOWNER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMember() throws Exception {
        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMember() throws Exception {
        // Initialize the database
        memberService.save(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member
        Member updatedMember = memberRepository.findOne(member.getId());
        updatedMember
            .idMember(UPDATED_ID_MEMBER)
            .tglRegister(UPDATED_TGL_REGISTER)
            .nama(UPDATED_NAMA)
            .alamat(UPDATED_ALAMAT)
            .pin(UPDATED_PIN)
            .status(UPDATED_STATUS)
            .saldo(UPDATED_SALDO)
            .idUpline(UPDATED_ID_UPLINE)
            .kodeLevel(UPDATED_KODE_LEVEL)
            .tglInput(UPDATED_TGL_INPUT)
            .userInput(UPDATED_USER_INPUT)
            .tglUpdate(UPDATED_TGL_UPDATE)
            .userUpdate(UPDATED_USER_UPDATE)
            .idMaster(UPDATED_ID_MASTER)
            .rptTrx(UPDATED_RPT_TRX)
            .selisih(UPDATED_SELISIH)
            .counter(UPDATED_COUNTER)
            .dongleNo(UPDATED_DONGLE_NO)
            .head2head(UPDATED_HEAD_2_HEAD)
            .ymid(UPDATED_YMID)
            .iprpt(UPDATED_IPRPT)
            .lastTrx(UPDATED_LAST_TRX)
            .ref(UPDATED_REF)
            .crypt(UPDATED_CRYPT)
            .gtalkid(UPDATED_GTALKID)
            .vremsgid(UPDATED_VREMSGID)
            .kodepos(UPDATED_KODEPOS)
            .iswarn(UPDATED_ISWARN)
            .msnid(UPDATED_MSNID)
            .idlogsal(UPDATED_IDLOGSAL)
            .lastKodetrx(UPDATED_LAST_KODETRX)
            .lastIdtrx(UPDATED_LAST_IDTRX)
            .telebotid(UPDATED_TELEBOTID)
            .telegramid(UPDATED_TELEGRAMID)
            .isowner(UPDATED_ISOWNER)
            .cryptowner(UPDATED_CRYPTOWNER)
            .pinowner(UPDATED_PINOWNER);

        restMemberMockMvc.perform(put("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMember)))
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getIdMember()).isEqualTo(UPDATED_ID_MEMBER);
        assertThat(testMember.getTglRegister()).isEqualTo(UPDATED_TGL_REGISTER);
        assertThat(testMember.getNama()).isEqualTo(UPDATED_NAMA);
        assertThat(testMember.getAlamat()).isEqualTo(UPDATED_ALAMAT);
        assertThat(testMember.getPin()).isEqualTo(UPDATED_PIN);
        assertThat(testMember.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMember.getSaldo()).isEqualTo(UPDATED_SALDO);
        assertThat(testMember.getIdUpline()).isEqualTo(UPDATED_ID_UPLINE);
        assertThat(testMember.getKodeLevel()).isEqualTo(UPDATED_KODE_LEVEL);
        assertThat(testMember.getTglInput()).isEqualTo(UPDATED_TGL_INPUT);
        assertThat(testMember.getUserInput()).isEqualTo(UPDATED_USER_INPUT);
        assertThat(testMember.getTglUpdate()).isEqualTo(UPDATED_TGL_UPDATE);
        assertThat(testMember.getUserUpdate()).isEqualTo(UPDATED_USER_UPDATE);
        assertThat(testMember.getIdMaster()).isEqualTo(UPDATED_ID_MASTER);
        assertThat(testMember.getRptTrx()).isEqualTo(UPDATED_RPT_TRX);
        assertThat(testMember.getSelisih()).isEqualTo(UPDATED_SELISIH);
        assertThat(testMember.getCounter()).isEqualTo(UPDATED_COUNTER);
        assertThat(testMember.getDongleNo()).isEqualTo(UPDATED_DONGLE_NO);
        assertThat(testMember.getHead2head()).isEqualTo(UPDATED_HEAD_2_HEAD);
        assertThat(testMember.getYmid()).isEqualTo(UPDATED_YMID);
        assertThat(testMember.getIprpt()).isEqualTo(UPDATED_IPRPT);
        assertThat(testMember.getLastTrx()).isEqualTo(UPDATED_LAST_TRX);
        assertThat(testMember.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testMember.getCrypt()).isEqualTo(UPDATED_CRYPT);
        assertThat(testMember.getGtalkid()).isEqualTo(UPDATED_GTALKID);
        assertThat(testMember.getVremsgid()).isEqualTo(UPDATED_VREMSGID);
        assertThat(testMember.getKodepos()).isEqualTo(UPDATED_KODEPOS);
        assertThat(testMember.getIswarn()).isEqualTo(UPDATED_ISWARN);
        assertThat(testMember.getMsnid()).isEqualTo(UPDATED_MSNID);
        assertThat(testMember.getIdlogsal()).isEqualTo(UPDATED_IDLOGSAL);
        assertThat(testMember.getLastKodetrx()).isEqualTo(UPDATED_LAST_KODETRX);
        assertThat(testMember.getLastIdtrx()).isEqualTo(UPDATED_LAST_IDTRX);
        assertThat(testMember.getTelebotid()).isEqualTo(UPDATED_TELEBOTID);
        assertThat(testMember.getTelegramid()).isEqualTo(UPDATED_TELEGRAMID);
        assertThat(testMember.getIsowner()).isEqualTo(UPDATED_ISOWNER);
        assertThat(testMember.getCryptowner()).isEqualTo(UPDATED_CRYPTOWNER);
        assertThat(testMember.getPinowner()).isEqualTo(UPDATED_PINOWNER);

        // Validate the Member in Elasticsearch
        Member memberEs = memberSearchRepository.findOne(testMember.getId());
        assertThat(memberEs).isEqualToComparingFieldByField(testMember);
    }

    @Test
    @Transactional
    public void updateNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Create the Member

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMemberMockMvc.perform(put("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(member)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMember() throws Exception {
        // Initialize the database
        memberService.save(member);

        int databaseSizeBeforeDelete = memberRepository.findAll().size();

        // Get the member
        restMemberMockMvc.perform(delete("/api/members/{id}", member.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean memberExistsInEs = memberSearchRepository.exists(member.getId());
        assertThat(memberExistsInEs).isFalse();

        // Validate the database is empty
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMember() throws Exception {
        // Initialize the database
        memberService.save(member);

        // Search the member
        restMemberMockMvc.perform(get("/api/_search/members?query=id:" + member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMember").value(hasItem(DEFAULT_ID_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].tglRegister").value(hasItem(DEFAULT_TGL_REGISTER.toString())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())))
            .andExpect(jsonPath("$.[*].alamat").value(hasItem(DEFAULT_ALAMAT.toString())))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].saldo").value(hasItem(DEFAULT_SALDO.intValue())))
            .andExpect(jsonPath("$.[*].idUpline").value(hasItem(DEFAULT_ID_UPLINE.toString())))
            .andExpect(jsonPath("$.[*].kodeLevel").value(hasItem(DEFAULT_KODE_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].tglInput").value(hasItem(sameInstant(DEFAULT_TGL_INPUT))))
            .andExpect(jsonPath("$.[*].userInput").value(hasItem(DEFAULT_USER_INPUT.toString())))
            .andExpect(jsonPath("$.[*].tglUpdate").value(hasItem(sameInstant(DEFAULT_TGL_UPDATE))))
            .andExpect(jsonPath("$.[*].userUpdate").value(hasItem(DEFAULT_USER_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].idMaster").value(hasItem(DEFAULT_ID_MASTER.toString())))
            .andExpect(jsonPath("$.[*].rptTrx").value(hasItem(DEFAULT_RPT_TRX)))
            .andExpect(jsonPath("$.[*].selisih").value(hasItem(DEFAULT_SELISIH.intValue())))
            .andExpect(jsonPath("$.[*].counter").value(hasItem(DEFAULT_COUNTER)))
            .andExpect(jsonPath("$.[*].dongleNo").value(hasItem(DEFAULT_DONGLE_NO.toString())))
            .andExpect(jsonPath("$.[*].head2head").value(hasItem(DEFAULT_HEAD_2_HEAD)))
            .andExpect(jsonPath("$.[*].ymid").value(hasItem(DEFAULT_YMID.toString())))
            .andExpect(jsonPath("$.[*].iprpt").value(hasItem(DEFAULT_IPRPT.toString())))
            .andExpect(jsonPath("$.[*].lastTrx").value(hasItem(sameInstant(DEFAULT_LAST_TRX))))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF.toString())))
            .andExpect(jsonPath("$.[*].crypt").value(hasItem(DEFAULT_CRYPT.toString())))
            .andExpect(jsonPath("$.[*].gtalkid").value(hasItem(DEFAULT_GTALKID.toString())))
            .andExpect(jsonPath("$.[*].vremsgid").value(hasItem(DEFAULT_VREMSGID.toString())))
            .andExpect(jsonPath("$.[*].kodepos").value(hasItem(DEFAULT_KODEPOS.toString())))
            .andExpect(jsonPath("$.[*].iswarn").value(hasItem(DEFAULT_ISWARN)))
            .andExpect(jsonPath("$.[*].msnid").value(hasItem(DEFAULT_MSNID.toString())))
            .andExpect(jsonPath("$.[*].idlogsal").value(hasItem(DEFAULT_IDLOGSAL.intValue())))
            .andExpect(jsonPath("$.[*].lastKodetrx").value(hasItem(DEFAULT_LAST_KODETRX.intValue())))
            .andExpect(jsonPath("$.[*].lastIdtrx").value(hasItem(DEFAULT_LAST_IDTRX.intValue())))
            .andExpect(jsonPath("$.[*].telebotid").value(hasItem(DEFAULT_TELEBOTID.toString())))
            .andExpect(jsonPath("$.[*].telegramid").value(hasItem(DEFAULT_TELEGRAMID.toString())))
            .andExpect(jsonPath("$.[*].isowner").value(hasItem(DEFAULT_ISOWNER.intValue())))
            .andExpect(jsonPath("$.[*].cryptowner").value(hasItem(DEFAULT_CRYPTOWNER.toString())))
            .andExpect(jsonPath("$.[*].pinowner").value(hasItem(DEFAULT_PINOWNER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Member.class);
        Member member1 = new Member();
        member1.setId(1L);
        Member member2 = new Member();
        member2.setId(member1.getId());
        assertThat(member1).isEqualTo(member2);
        member2.setId(2L);
        assertThat(member1).isNotEqualTo(member2);
        member1.setId(null);
        assertThat(member1).isNotEqualTo(member2);
    }
}
