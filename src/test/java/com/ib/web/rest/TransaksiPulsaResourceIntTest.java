package com.ib.web.rest;

import com.ib.AyoappApp;

import com.ib.domain.TransaksiPulsa;
import com.ib.repository.TransaksiPulsaRepository;
import com.ib.service.TransaksiPulsaService;
import com.ib.repository.search.TransaksiPulsaSearchRepository;
import com.ib.web.rest.errors.ExceptionTranslator;
import com.ib.service.dto.TransaksiPulsaCriteria;
import com.ib.service.TransaksiPulsaQueryService;

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
 * Test class for the TransaksiPulsaResource REST controller.
 *
 * @see TransaksiPulsaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AyoappApp.class)
public class TransaksiPulsaResourceIntTest {

    private static final String DEFAULT_KODE_PRODUK = "AAAAAAAAAA";
    private static final String UPDATED_KODE_PRODUK = "BBBBBBBBBB";

    private static final String DEFAULT_HP_TUJUAN = "AAAAAAAAAA";
    private static final String UPDATED_HP_TUJUAN = "BBBBBBBBBB";

    private static final String DEFAULT_HP_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_HP_MEMBER = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_HARGA_BELI = new BigDecimal(1);
    private static final BigDecimal UPDATED_HARGA_BELI = new BigDecimal(2);

    private static final BigDecimal DEFAULT_HPP = new BigDecimal(1);
    private static final BigDecimal UPDATED_HPP = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LABA = new BigDecimal(1);
    private static final BigDecimal UPDATED_LABA = new BigDecimal(2);

    private static final String DEFAULT_COM = "AAAAAAAAAA";
    private static final String UPDATED_COM = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ADMRPT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ADMRPT = new BigDecimal(2);

    private static final Integer DEFAULT_ULANG = 1;
    private static final Integer UPDATED_ULANG = 2;

    private static final String DEFAULT_ULANG_TGL = "AAAAAAAAAA";
    private static final String UPDATED_ULANG_TGL = "BBBBBBBBBB";

    private static final Integer DEFAULT_FISIK = 1;
    private static final Integer UPDATED_FISIK = 2;

    private static final Integer DEFAULT_MANUAL = 1;
    private static final Integer UPDATED_MANUAL = 2;

    private static final Integer DEFAULT_SWITCH_1 = 1;
    private static final Integer UPDATED_SWITCH_1 = 2;

    private static final Integer DEFAULT_KODE_GAGAL = 1;
    private static final Integer UPDATED_KODE_GAGAL = 2;

    private static final Integer DEFAULT_WAIT_SMS = 1;
    private static final Integer UPDATED_WAIT_SMS = 2;

    private static final Integer DEFAULT_HEAD_2_HEAD = 1;
    private static final Integer UPDATED_HEAD_2_HEAD = 2;

    private static final String DEFAULT_HP_PEMBELI = "AAAAAAAAAA";
    private static final String UPDATED_HP_PEMBELI = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BEA_ADMIN = new BigDecimal(1);
    private static final BigDecimal UPDATED_BEA_ADMIN = new BigDecimal(2);

    private static final Integer DEFAULT_IS_REPORT = 1;
    private static final Integer UPDATED_IS_REPORT = 2;

    private static final Integer DEFAULT_SUPLIER_KE = 1;
    private static final Integer UPDATED_SUPLIER_KE = 2;

    private static final Long DEFAULT_ID_DISTRIBUTOR = 1L;
    private static final Long UPDATED_ID_DISTRIBUTOR = 2L;

    private static final String DEFAULT_SN = "AAAAAAAAAA";
    private static final String UPDATED_SN = "BBBBBBBBBB";

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final String DEFAULT_PESANKIRIM = "AAAAAAAAAA";
    private static final String UPDATED_PESANKIRIM = "BBBBBBBBBB";

    private static final Integer DEFAULT_METODE = 1;
    private static final Integer UPDATED_METODE = 2;

    private static final String DEFAULT_TO_DISTRIBUTOR = "AAAAAAAAAA";
    private static final String UPDATED_TO_DISTRIBUTOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_PORTIP = 1;
    private static final Integer UPDATED_ID_PORTIP = 2;

    private static final ZonedDateTime DEFAULT_TIMEUPDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMEUPDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_ID_DISTRIBUTOR_OLD = 1L;
    private static final Long UPDATED_ID_DISTRIBUTOR_OLD = 2L;

    private static final Long DEFAULT_ID_DISTRIBUTOR_PRODUK = 1L;
    private static final Long UPDATED_ID_DISTRIBUTOR_PRODUK = 2L;

    private static final BigDecimal DEFAULT_SALDO_SUP = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALDO_SUP = new BigDecimal(2);

    private static final Integer DEFAULT_ISREBATE = 1;
    private static final Integer UPDATED_ISREBATE = 2;

    private static final String DEFAULT_ENGINENAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGINENAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPEMSG = 1;
    private static final Integer UPDATED_TYPEMSG = 2;

    private static final Integer DEFAULT_ISRO = 1;
    private static final Integer UPDATED_ISRO = 2;

    @Autowired
    private TransaksiPulsaRepository transaksiPulsaRepository;

    @Autowired
    private TransaksiPulsaService transaksiPulsaService;

    @Autowired
    private TransaksiPulsaSearchRepository transaksiPulsaSearchRepository;

    @Autowired
    private TransaksiPulsaQueryService transaksiPulsaQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTransaksiPulsaMockMvc;

    private TransaksiPulsa transaksiPulsa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransaksiPulsaResource transaksiPulsaResource = new TransaksiPulsaResource(transaksiPulsaService, transaksiPulsaQueryService);
        this.restTransaksiPulsaMockMvc = MockMvcBuilders.standaloneSetup(transaksiPulsaResource)
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
    public static TransaksiPulsa createEntity(EntityManager em) {
        TransaksiPulsa transaksiPulsa = new TransaksiPulsa()
            .kodeProduk(DEFAULT_KODE_PRODUK)
            .hpTujuan(DEFAULT_HP_TUJUAN)
            .hpMember(DEFAULT_HP_MEMBER)
            .hargaBeli(DEFAULT_HARGA_BELI)
            .hpp(DEFAULT_HPP)
            .laba(DEFAULT_LABA)
            .com(DEFAULT_COM)
            .admrpt(DEFAULT_ADMRPT)
            .ulang(DEFAULT_ULANG)
            .ulangTgl(DEFAULT_ULANG_TGL)
            .fisik(DEFAULT_FISIK)
            .manual(DEFAULT_MANUAL)
            .switch1(DEFAULT_SWITCH_1)
            .kodeGagal(DEFAULT_KODE_GAGAL)
            .waitSms(DEFAULT_WAIT_SMS)
            .head2Head(DEFAULT_HEAD_2_HEAD)
            .hpPembeli(DEFAULT_HP_PEMBELI)
            .beaAdmin(DEFAULT_BEA_ADMIN)
            .isReport(DEFAULT_IS_REPORT)
            .suplierKe(DEFAULT_SUPLIER_KE)
            .idDistributor(DEFAULT_ID_DISTRIBUTOR)
            .sn(DEFAULT_SN)
            .ip(DEFAULT_IP)
            .pesankirim(DEFAULT_PESANKIRIM)
            .metode(DEFAULT_METODE)
            .toDistributor(DEFAULT_TO_DISTRIBUTOR)
            .idPortip(DEFAULT_ID_PORTIP)
            .timeupdate(DEFAULT_TIMEUPDATE)
            .idDistributorOld(DEFAULT_ID_DISTRIBUTOR_OLD)
            .idDistributorProduk(DEFAULT_ID_DISTRIBUTOR_PRODUK)
            .saldoSup(DEFAULT_SALDO_SUP)
            .isrebate(DEFAULT_ISREBATE)
            .enginename(DEFAULT_ENGINENAME)
            .typemsg(DEFAULT_TYPEMSG)
            .isro(DEFAULT_ISRO);
        return transaksiPulsa;
    }

    @Before
    public void initTest() {
        transaksiPulsaSearchRepository.deleteAll();
        transaksiPulsa = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransaksiPulsa() throws Exception {
        int databaseSizeBeforeCreate = transaksiPulsaRepository.findAll().size();

        // Create the TransaksiPulsa
        restTransaksiPulsaMockMvc.perform(post("/api/transaksi-pulsas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaksiPulsa)))
            .andExpect(status().isCreated());

        // Validate the TransaksiPulsa in the database
        List<TransaksiPulsa> transaksiPulsaList = transaksiPulsaRepository.findAll();
        assertThat(transaksiPulsaList).hasSize(databaseSizeBeforeCreate + 1);
        TransaksiPulsa testTransaksiPulsa = transaksiPulsaList.get(transaksiPulsaList.size() - 1);
        assertThat(testTransaksiPulsa.getKodeProduk()).isEqualTo(DEFAULT_KODE_PRODUK);
        assertThat(testTransaksiPulsa.getHpTujuan()).isEqualTo(DEFAULT_HP_TUJUAN);
        assertThat(testTransaksiPulsa.getHpMember()).isEqualTo(DEFAULT_HP_MEMBER);
        assertThat(testTransaksiPulsa.getHargaBeli()).isEqualTo(DEFAULT_HARGA_BELI);
        assertThat(testTransaksiPulsa.getHpp()).isEqualTo(DEFAULT_HPP);
        assertThat(testTransaksiPulsa.getLaba()).isEqualTo(DEFAULT_LABA);
        assertThat(testTransaksiPulsa.getCom()).isEqualTo(DEFAULT_COM);
        assertThat(testTransaksiPulsa.getAdmrpt()).isEqualTo(DEFAULT_ADMRPT);
        assertThat(testTransaksiPulsa.getUlang()).isEqualTo(DEFAULT_ULANG);
        assertThat(testTransaksiPulsa.getUlangTgl()).isEqualTo(DEFAULT_ULANG_TGL);
        assertThat(testTransaksiPulsa.getFisik()).isEqualTo(DEFAULT_FISIK);
        assertThat(testTransaksiPulsa.getManual()).isEqualTo(DEFAULT_MANUAL);
        assertThat(testTransaksiPulsa.getSwitch1()).isEqualTo(DEFAULT_SWITCH_1);
        assertThat(testTransaksiPulsa.getKodeGagal()).isEqualTo(DEFAULT_KODE_GAGAL);
        assertThat(testTransaksiPulsa.getWaitSms()).isEqualTo(DEFAULT_WAIT_SMS);
        assertThat(testTransaksiPulsa.getHead2Head()).isEqualTo(DEFAULT_HEAD_2_HEAD);
        assertThat(testTransaksiPulsa.getHpPembeli()).isEqualTo(DEFAULT_HP_PEMBELI);
        assertThat(testTransaksiPulsa.getBeaAdmin()).isEqualTo(DEFAULT_BEA_ADMIN);
        assertThat(testTransaksiPulsa.getIsReport()).isEqualTo(DEFAULT_IS_REPORT);
        assertThat(testTransaksiPulsa.getSuplierKe()).isEqualTo(DEFAULT_SUPLIER_KE);
        assertThat(testTransaksiPulsa.getIdDistributor()).isEqualTo(DEFAULT_ID_DISTRIBUTOR);
        assertThat(testTransaksiPulsa.getSn()).isEqualTo(DEFAULT_SN);
        assertThat(testTransaksiPulsa.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testTransaksiPulsa.getPesankirim()).isEqualTo(DEFAULT_PESANKIRIM);
        assertThat(testTransaksiPulsa.getMetode()).isEqualTo(DEFAULT_METODE);
        assertThat(testTransaksiPulsa.getToDistributor()).isEqualTo(DEFAULT_TO_DISTRIBUTOR);
        assertThat(testTransaksiPulsa.getIdPortip()).isEqualTo(DEFAULT_ID_PORTIP);
        assertThat(testTransaksiPulsa.getTimeupdate()).isEqualTo(DEFAULT_TIMEUPDATE);
        assertThat(testTransaksiPulsa.getIdDistributorOld()).isEqualTo(DEFAULT_ID_DISTRIBUTOR_OLD);
        assertThat(testTransaksiPulsa.getIdDistributorProduk()).isEqualTo(DEFAULT_ID_DISTRIBUTOR_PRODUK);
        assertThat(testTransaksiPulsa.getSaldoSup()).isEqualTo(DEFAULT_SALDO_SUP);
        assertThat(testTransaksiPulsa.getIsrebate()).isEqualTo(DEFAULT_ISREBATE);
        assertThat(testTransaksiPulsa.getEnginename()).isEqualTo(DEFAULT_ENGINENAME);
        assertThat(testTransaksiPulsa.getTypemsg()).isEqualTo(DEFAULT_TYPEMSG);
        assertThat(testTransaksiPulsa.getIsro()).isEqualTo(DEFAULT_ISRO);

        // Validate the TransaksiPulsa in Elasticsearch
        TransaksiPulsa transaksiPulsaEs = transaksiPulsaSearchRepository.findOne(testTransaksiPulsa.getId());
        assertThat(transaksiPulsaEs).isEqualToComparingFieldByField(testTransaksiPulsa);
    }

    @Test
    @Transactional
    public void createTransaksiPulsaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transaksiPulsaRepository.findAll().size();

        // Create the TransaksiPulsa with an existing ID
        transaksiPulsa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransaksiPulsaMockMvc.perform(post("/api/transaksi-pulsas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaksiPulsa)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TransaksiPulsa> transaksiPulsaList = transaksiPulsaRepository.findAll();
        assertThat(transaksiPulsaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsas() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList
        restTransaksiPulsaMockMvc.perform(get("/api/transaksi-pulsas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaksiPulsa.getId().intValue())))
            .andExpect(jsonPath("$.[*].kodeProduk").value(hasItem(DEFAULT_KODE_PRODUK.toString())))
            .andExpect(jsonPath("$.[*].hpTujuan").value(hasItem(DEFAULT_HP_TUJUAN.toString())))
            .andExpect(jsonPath("$.[*].hpMember").value(hasItem(DEFAULT_HP_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].hargaBeli").value(hasItem(DEFAULT_HARGA_BELI.intValue())))
            .andExpect(jsonPath("$.[*].hpp").value(hasItem(DEFAULT_HPP.intValue())))
            .andExpect(jsonPath("$.[*].laba").value(hasItem(DEFAULT_LABA.intValue())))
            .andExpect(jsonPath("$.[*].com").value(hasItem(DEFAULT_COM.toString())))
            .andExpect(jsonPath("$.[*].admrpt").value(hasItem(DEFAULT_ADMRPT.intValue())))
            .andExpect(jsonPath("$.[*].ulang").value(hasItem(DEFAULT_ULANG)))
            .andExpect(jsonPath("$.[*].ulangTgl").value(hasItem(DEFAULT_ULANG_TGL.toString())))
            .andExpect(jsonPath("$.[*].fisik").value(hasItem(DEFAULT_FISIK)))
            .andExpect(jsonPath("$.[*].manual").value(hasItem(DEFAULT_MANUAL)))
            .andExpect(jsonPath("$.[*].switch1").value(hasItem(DEFAULT_SWITCH_1)))
            .andExpect(jsonPath("$.[*].kodeGagal").value(hasItem(DEFAULT_KODE_GAGAL)))
            .andExpect(jsonPath("$.[*].waitSms").value(hasItem(DEFAULT_WAIT_SMS)))
            .andExpect(jsonPath("$.[*].head2Head").value(hasItem(DEFAULT_HEAD_2_HEAD)))
            .andExpect(jsonPath("$.[*].hpPembeli").value(hasItem(DEFAULT_HP_PEMBELI.toString())))
            .andExpect(jsonPath("$.[*].beaAdmin").value(hasItem(DEFAULT_BEA_ADMIN.intValue())))
            .andExpect(jsonPath("$.[*].isReport").value(hasItem(DEFAULT_IS_REPORT)))
            .andExpect(jsonPath("$.[*].suplierKe").value(hasItem(DEFAULT_SUPLIER_KE)))
            .andExpect(jsonPath("$.[*].idDistributor").value(hasItem(DEFAULT_ID_DISTRIBUTOR.intValue())))
            .andExpect(jsonPath("$.[*].sn").value(hasItem(DEFAULT_SN.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].pesankirim").value(hasItem(DEFAULT_PESANKIRIM.toString())))
            .andExpect(jsonPath("$.[*].metode").value(hasItem(DEFAULT_METODE)))
            .andExpect(jsonPath("$.[*].toDistributor").value(hasItem(DEFAULT_TO_DISTRIBUTOR.toString())))
            .andExpect(jsonPath("$.[*].idPortip").value(hasItem(DEFAULT_ID_PORTIP)))
            .andExpect(jsonPath("$.[*].timeupdate").value(hasItem(sameInstant(DEFAULT_TIMEUPDATE))))
            .andExpect(jsonPath("$.[*].idDistributorOld").value(hasItem(DEFAULT_ID_DISTRIBUTOR_OLD.intValue())))
            .andExpect(jsonPath("$.[*].idDistributorProduk").value(hasItem(DEFAULT_ID_DISTRIBUTOR_PRODUK.intValue())))
            .andExpect(jsonPath("$.[*].saldoSup").value(hasItem(DEFAULT_SALDO_SUP.intValue())))
            .andExpect(jsonPath("$.[*].isrebate").value(hasItem(DEFAULT_ISREBATE)))
            .andExpect(jsonPath("$.[*].enginename").value(hasItem(DEFAULT_ENGINENAME.toString())))
            .andExpect(jsonPath("$.[*].typemsg").value(hasItem(DEFAULT_TYPEMSG)))
            .andExpect(jsonPath("$.[*].isro").value(hasItem(DEFAULT_ISRO)));
    }

    @Test
    @Transactional
    public void getTransaksiPulsa() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get the transaksiPulsa
        restTransaksiPulsaMockMvc.perform(get("/api/transaksi-pulsas/{id}", transaksiPulsa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transaksiPulsa.getId().intValue()))
            .andExpect(jsonPath("$.kodeProduk").value(DEFAULT_KODE_PRODUK.toString()))
            .andExpect(jsonPath("$.hpTujuan").value(DEFAULT_HP_TUJUAN.toString()))
            .andExpect(jsonPath("$.hpMember").value(DEFAULT_HP_MEMBER.toString()))
            .andExpect(jsonPath("$.hargaBeli").value(DEFAULT_HARGA_BELI.intValue()))
            .andExpect(jsonPath("$.hpp").value(DEFAULT_HPP.intValue()))
            .andExpect(jsonPath("$.laba").value(DEFAULT_LABA.intValue()))
            .andExpect(jsonPath("$.com").value(DEFAULT_COM.toString()))
            .andExpect(jsonPath("$.admrpt").value(DEFAULT_ADMRPT.intValue()))
            .andExpect(jsonPath("$.ulang").value(DEFAULT_ULANG))
            .andExpect(jsonPath("$.ulangTgl").value(DEFAULT_ULANG_TGL.toString()))
            .andExpect(jsonPath("$.fisik").value(DEFAULT_FISIK))
            .andExpect(jsonPath("$.manual").value(DEFAULT_MANUAL))
            .andExpect(jsonPath("$.switch1").value(DEFAULT_SWITCH_1))
            .andExpect(jsonPath("$.kodeGagal").value(DEFAULT_KODE_GAGAL))
            .andExpect(jsonPath("$.waitSms").value(DEFAULT_WAIT_SMS))
            .andExpect(jsonPath("$.head2Head").value(DEFAULT_HEAD_2_HEAD))
            .andExpect(jsonPath("$.hpPembeli").value(DEFAULT_HP_PEMBELI.toString()))
            .andExpect(jsonPath("$.beaAdmin").value(DEFAULT_BEA_ADMIN.intValue()))
            .andExpect(jsonPath("$.isReport").value(DEFAULT_IS_REPORT))
            .andExpect(jsonPath("$.suplierKe").value(DEFAULT_SUPLIER_KE))
            .andExpect(jsonPath("$.idDistributor").value(DEFAULT_ID_DISTRIBUTOR.intValue()))
            .andExpect(jsonPath("$.sn").value(DEFAULT_SN.toString()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP.toString()))
            .andExpect(jsonPath("$.pesankirim").value(DEFAULT_PESANKIRIM.toString()))
            .andExpect(jsonPath("$.metode").value(DEFAULT_METODE))
            .andExpect(jsonPath("$.toDistributor").value(DEFAULT_TO_DISTRIBUTOR.toString()))
            .andExpect(jsonPath("$.idPortip").value(DEFAULT_ID_PORTIP))
            .andExpect(jsonPath("$.timeupdate").value(sameInstant(DEFAULT_TIMEUPDATE)))
            .andExpect(jsonPath("$.idDistributorOld").value(DEFAULT_ID_DISTRIBUTOR_OLD.intValue()))
            .andExpect(jsonPath("$.idDistributorProduk").value(DEFAULT_ID_DISTRIBUTOR_PRODUK.intValue()))
            .andExpect(jsonPath("$.saldoSup").value(DEFAULT_SALDO_SUP.intValue()))
            .andExpect(jsonPath("$.isrebate").value(DEFAULT_ISREBATE))
            .andExpect(jsonPath("$.enginename").value(DEFAULT_ENGINENAME.toString()))
            .andExpect(jsonPath("$.typemsg").value(DEFAULT_TYPEMSG))
            .andExpect(jsonPath("$.isro").value(DEFAULT_ISRO));
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByKodeProdukIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where kodeProduk equals to DEFAULT_KODE_PRODUK
        defaultTransaksiPulsaShouldBeFound("kodeProduk.equals=" + DEFAULT_KODE_PRODUK);

        // Get all the transaksiPulsaList where kodeProduk equals to UPDATED_KODE_PRODUK
        defaultTransaksiPulsaShouldNotBeFound("kodeProduk.equals=" + UPDATED_KODE_PRODUK);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByKodeProdukIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where kodeProduk in DEFAULT_KODE_PRODUK or UPDATED_KODE_PRODUK
        defaultTransaksiPulsaShouldBeFound("kodeProduk.in=" + DEFAULT_KODE_PRODUK + "," + UPDATED_KODE_PRODUK);

        // Get all the transaksiPulsaList where kodeProduk equals to UPDATED_KODE_PRODUK
        defaultTransaksiPulsaShouldNotBeFound("kodeProduk.in=" + UPDATED_KODE_PRODUK);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByKodeProdukIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where kodeProduk is not null
        defaultTransaksiPulsaShouldBeFound("kodeProduk.specified=true");

        // Get all the transaksiPulsaList where kodeProduk is null
        defaultTransaksiPulsaShouldNotBeFound("kodeProduk.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHpTujuanIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where hpTujuan equals to DEFAULT_HP_TUJUAN
        defaultTransaksiPulsaShouldBeFound("hpTujuan.equals=" + DEFAULT_HP_TUJUAN);

        // Get all the transaksiPulsaList where hpTujuan equals to UPDATED_HP_TUJUAN
        defaultTransaksiPulsaShouldNotBeFound("hpTujuan.equals=" + UPDATED_HP_TUJUAN);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHpTujuanIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where hpTujuan in DEFAULT_HP_TUJUAN or UPDATED_HP_TUJUAN
        defaultTransaksiPulsaShouldBeFound("hpTujuan.in=" + DEFAULT_HP_TUJUAN + "," + UPDATED_HP_TUJUAN);

        // Get all the transaksiPulsaList where hpTujuan equals to UPDATED_HP_TUJUAN
        defaultTransaksiPulsaShouldNotBeFound("hpTujuan.in=" + UPDATED_HP_TUJUAN);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHpTujuanIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where hpTujuan is not null
        defaultTransaksiPulsaShouldBeFound("hpTujuan.specified=true");

        // Get all the transaksiPulsaList where hpTujuan is null
        defaultTransaksiPulsaShouldNotBeFound("hpTujuan.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHpMemberIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where hpMember equals to DEFAULT_HP_MEMBER
        defaultTransaksiPulsaShouldBeFound("hpMember.equals=" + DEFAULT_HP_MEMBER);

        // Get all the transaksiPulsaList where hpMember equals to UPDATED_HP_MEMBER
        defaultTransaksiPulsaShouldNotBeFound("hpMember.equals=" + UPDATED_HP_MEMBER);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHpMemberIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where hpMember in DEFAULT_HP_MEMBER or UPDATED_HP_MEMBER
        defaultTransaksiPulsaShouldBeFound("hpMember.in=" + DEFAULT_HP_MEMBER + "," + UPDATED_HP_MEMBER);

        // Get all the transaksiPulsaList where hpMember equals to UPDATED_HP_MEMBER
        defaultTransaksiPulsaShouldNotBeFound("hpMember.in=" + UPDATED_HP_MEMBER);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHpMemberIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where hpMember is not null
        defaultTransaksiPulsaShouldBeFound("hpMember.specified=true");

        // Get all the transaksiPulsaList where hpMember is null
        defaultTransaksiPulsaShouldNotBeFound("hpMember.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHargaBeliIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where hargaBeli equals to DEFAULT_HARGA_BELI
        defaultTransaksiPulsaShouldBeFound("hargaBeli.equals=" + DEFAULT_HARGA_BELI);

        // Get all the transaksiPulsaList where hargaBeli equals to UPDATED_HARGA_BELI
        defaultTransaksiPulsaShouldNotBeFound("hargaBeli.equals=" + UPDATED_HARGA_BELI);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHargaBeliIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where hargaBeli in DEFAULT_HARGA_BELI or UPDATED_HARGA_BELI
        defaultTransaksiPulsaShouldBeFound("hargaBeli.in=" + DEFAULT_HARGA_BELI + "," + UPDATED_HARGA_BELI);

        // Get all the transaksiPulsaList where hargaBeli equals to UPDATED_HARGA_BELI
        defaultTransaksiPulsaShouldNotBeFound("hargaBeli.in=" + UPDATED_HARGA_BELI);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHargaBeliIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where hargaBeli is not null
        defaultTransaksiPulsaShouldBeFound("hargaBeli.specified=true");

        // Get all the transaksiPulsaList where hargaBeli is null
        defaultTransaksiPulsaShouldNotBeFound("hargaBeli.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHppIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where hpp equals to DEFAULT_HPP
        defaultTransaksiPulsaShouldBeFound("hpp.equals=" + DEFAULT_HPP);

        // Get all the transaksiPulsaList where hpp equals to UPDATED_HPP
        defaultTransaksiPulsaShouldNotBeFound("hpp.equals=" + UPDATED_HPP);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHppIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where hpp in DEFAULT_HPP or UPDATED_HPP
        defaultTransaksiPulsaShouldBeFound("hpp.in=" + DEFAULT_HPP + "," + UPDATED_HPP);

        // Get all the transaksiPulsaList where hpp equals to UPDATED_HPP
        defaultTransaksiPulsaShouldNotBeFound("hpp.in=" + UPDATED_HPP);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHppIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where hpp is not null
        defaultTransaksiPulsaShouldBeFound("hpp.specified=true");

        // Get all the transaksiPulsaList where hpp is null
        defaultTransaksiPulsaShouldNotBeFound("hpp.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByLabaIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where laba equals to DEFAULT_LABA
        defaultTransaksiPulsaShouldBeFound("laba.equals=" + DEFAULT_LABA);

        // Get all the transaksiPulsaList where laba equals to UPDATED_LABA
        defaultTransaksiPulsaShouldNotBeFound("laba.equals=" + UPDATED_LABA);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByLabaIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where laba in DEFAULT_LABA or UPDATED_LABA
        defaultTransaksiPulsaShouldBeFound("laba.in=" + DEFAULT_LABA + "," + UPDATED_LABA);

        // Get all the transaksiPulsaList where laba equals to UPDATED_LABA
        defaultTransaksiPulsaShouldNotBeFound("laba.in=" + UPDATED_LABA);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByLabaIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where laba is not null
        defaultTransaksiPulsaShouldBeFound("laba.specified=true");

        // Get all the transaksiPulsaList where laba is null
        defaultTransaksiPulsaShouldNotBeFound("laba.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByComIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where com equals to DEFAULT_COM
        defaultTransaksiPulsaShouldBeFound("com.equals=" + DEFAULT_COM);

        // Get all the transaksiPulsaList where com equals to UPDATED_COM
        defaultTransaksiPulsaShouldNotBeFound("com.equals=" + UPDATED_COM);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByComIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where com in DEFAULT_COM or UPDATED_COM
        defaultTransaksiPulsaShouldBeFound("com.in=" + DEFAULT_COM + "," + UPDATED_COM);

        // Get all the transaksiPulsaList where com equals to UPDATED_COM
        defaultTransaksiPulsaShouldNotBeFound("com.in=" + UPDATED_COM);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByComIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where com is not null
        defaultTransaksiPulsaShouldBeFound("com.specified=true");

        // Get all the transaksiPulsaList where com is null
        defaultTransaksiPulsaShouldNotBeFound("com.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByAdmrptIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where admrpt equals to DEFAULT_ADMRPT
        defaultTransaksiPulsaShouldBeFound("admrpt.equals=" + DEFAULT_ADMRPT);

        // Get all the transaksiPulsaList where admrpt equals to UPDATED_ADMRPT
        defaultTransaksiPulsaShouldNotBeFound("admrpt.equals=" + UPDATED_ADMRPT);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByAdmrptIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where admrpt in DEFAULT_ADMRPT or UPDATED_ADMRPT
        defaultTransaksiPulsaShouldBeFound("admrpt.in=" + DEFAULT_ADMRPT + "," + UPDATED_ADMRPT);

        // Get all the transaksiPulsaList where admrpt equals to UPDATED_ADMRPT
        defaultTransaksiPulsaShouldNotBeFound("admrpt.in=" + UPDATED_ADMRPT);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByAdmrptIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where admrpt is not null
        defaultTransaksiPulsaShouldBeFound("admrpt.specified=true");

        // Get all the transaksiPulsaList where admrpt is null
        defaultTransaksiPulsaShouldNotBeFound("admrpt.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByUlangIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where ulang equals to DEFAULT_ULANG
        defaultTransaksiPulsaShouldBeFound("ulang.equals=" + DEFAULT_ULANG);

        // Get all the transaksiPulsaList where ulang equals to UPDATED_ULANG
        defaultTransaksiPulsaShouldNotBeFound("ulang.equals=" + UPDATED_ULANG);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByUlangIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where ulang in DEFAULT_ULANG or UPDATED_ULANG
        defaultTransaksiPulsaShouldBeFound("ulang.in=" + DEFAULT_ULANG + "," + UPDATED_ULANG);

        // Get all the transaksiPulsaList where ulang equals to UPDATED_ULANG
        defaultTransaksiPulsaShouldNotBeFound("ulang.in=" + UPDATED_ULANG);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByUlangIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where ulang is not null
        defaultTransaksiPulsaShouldBeFound("ulang.specified=true");

        // Get all the transaksiPulsaList where ulang is null
        defaultTransaksiPulsaShouldNotBeFound("ulang.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByUlangIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where ulang greater than or equals to DEFAULT_ULANG
        defaultTransaksiPulsaShouldBeFound("ulang.greaterOrEqualThan=" + DEFAULT_ULANG);

        // Get all the transaksiPulsaList where ulang greater than or equals to UPDATED_ULANG
        defaultTransaksiPulsaShouldNotBeFound("ulang.greaterOrEqualThan=" + UPDATED_ULANG);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByUlangIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where ulang less than or equals to DEFAULT_ULANG
        defaultTransaksiPulsaShouldNotBeFound("ulang.lessThan=" + DEFAULT_ULANG);

        // Get all the transaksiPulsaList where ulang less than or equals to UPDATED_ULANG
        defaultTransaksiPulsaShouldBeFound("ulang.lessThan=" + UPDATED_ULANG);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasByUlangTglIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where ulangTgl equals to DEFAULT_ULANG_TGL
        defaultTransaksiPulsaShouldBeFound("ulangTgl.equals=" + DEFAULT_ULANG_TGL);

        // Get all the transaksiPulsaList where ulangTgl equals to UPDATED_ULANG_TGL
        defaultTransaksiPulsaShouldNotBeFound("ulangTgl.equals=" + UPDATED_ULANG_TGL);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByUlangTglIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where ulangTgl in DEFAULT_ULANG_TGL or UPDATED_ULANG_TGL
        defaultTransaksiPulsaShouldBeFound("ulangTgl.in=" + DEFAULT_ULANG_TGL + "," + UPDATED_ULANG_TGL);

        // Get all the transaksiPulsaList where ulangTgl equals to UPDATED_ULANG_TGL
        defaultTransaksiPulsaShouldNotBeFound("ulangTgl.in=" + UPDATED_ULANG_TGL);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByUlangTglIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where ulangTgl is not null
        defaultTransaksiPulsaShouldBeFound("ulangTgl.specified=true");

        // Get all the transaksiPulsaList where ulangTgl is null
        defaultTransaksiPulsaShouldNotBeFound("ulangTgl.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByFisikIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where fisik equals to DEFAULT_FISIK
        defaultTransaksiPulsaShouldBeFound("fisik.equals=" + DEFAULT_FISIK);

        // Get all the transaksiPulsaList where fisik equals to UPDATED_FISIK
        defaultTransaksiPulsaShouldNotBeFound("fisik.equals=" + UPDATED_FISIK);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByFisikIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where fisik in DEFAULT_FISIK or UPDATED_FISIK
        defaultTransaksiPulsaShouldBeFound("fisik.in=" + DEFAULT_FISIK + "," + UPDATED_FISIK);

        // Get all the transaksiPulsaList where fisik equals to UPDATED_FISIK
        defaultTransaksiPulsaShouldNotBeFound("fisik.in=" + UPDATED_FISIK);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByFisikIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where fisik is not null
        defaultTransaksiPulsaShouldBeFound("fisik.specified=true");

        // Get all the transaksiPulsaList where fisik is null
        defaultTransaksiPulsaShouldNotBeFound("fisik.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByFisikIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where fisik greater than or equals to DEFAULT_FISIK
        defaultTransaksiPulsaShouldBeFound("fisik.greaterOrEqualThan=" + DEFAULT_FISIK);

        // Get all the transaksiPulsaList where fisik greater than or equals to UPDATED_FISIK
        defaultTransaksiPulsaShouldNotBeFound("fisik.greaterOrEqualThan=" + UPDATED_FISIK);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByFisikIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where fisik less than or equals to DEFAULT_FISIK
        defaultTransaksiPulsaShouldNotBeFound("fisik.lessThan=" + DEFAULT_FISIK);

        // Get all the transaksiPulsaList where fisik less than or equals to UPDATED_FISIK
        defaultTransaksiPulsaShouldBeFound("fisik.lessThan=" + UPDATED_FISIK);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasByManualIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where manual equals to DEFAULT_MANUAL
        defaultTransaksiPulsaShouldBeFound("manual.equals=" + DEFAULT_MANUAL);

        // Get all the transaksiPulsaList where manual equals to UPDATED_MANUAL
        defaultTransaksiPulsaShouldNotBeFound("manual.equals=" + UPDATED_MANUAL);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByManualIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where manual in DEFAULT_MANUAL or UPDATED_MANUAL
        defaultTransaksiPulsaShouldBeFound("manual.in=" + DEFAULT_MANUAL + "," + UPDATED_MANUAL);

        // Get all the transaksiPulsaList where manual equals to UPDATED_MANUAL
        defaultTransaksiPulsaShouldNotBeFound("manual.in=" + UPDATED_MANUAL);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByManualIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where manual is not null
        defaultTransaksiPulsaShouldBeFound("manual.specified=true");

        // Get all the transaksiPulsaList where manual is null
        defaultTransaksiPulsaShouldNotBeFound("manual.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByManualIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where manual greater than or equals to DEFAULT_MANUAL
        defaultTransaksiPulsaShouldBeFound("manual.greaterOrEqualThan=" + DEFAULT_MANUAL);

        // Get all the transaksiPulsaList where manual greater than or equals to UPDATED_MANUAL
        defaultTransaksiPulsaShouldNotBeFound("manual.greaterOrEqualThan=" + UPDATED_MANUAL);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByManualIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where manual less than or equals to DEFAULT_MANUAL
        defaultTransaksiPulsaShouldNotBeFound("manual.lessThan=" + DEFAULT_MANUAL);

        // Get all the transaksiPulsaList where manual less than or equals to UPDATED_MANUAL
        defaultTransaksiPulsaShouldBeFound("manual.lessThan=" + UPDATED_MANUAL);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasBySwitch1IsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where switch1 equals to DEFAULT_SWITCH_1
        defaultTransaksiPulsaShouldBeFound("switch1.equals=" + DEFAULT_SWITCH_1);

        // Get all the transaksiPulsaList where switch1 equals to UPDATED_SWITCH_1
        defaultTransaksiPulsaShouldNotBeFound("switch1.equals=" + UPDATED_SWITCH_1);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasBySwitch1IsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where switch1 in DEFAULT_SWITCH_1 or UPDATED_SWITCH_1
        defaultTransaksiPulsaShouldBeFound("switch1.in=" + DEFAULT_SWITCH_1 + "," + UPDATED_SWITCH_1);

        // Get all the transaksiPulsaList where switch1 equals to UPDATED_SWITCH_1
        defaultTransaksiPulsaShouldNotBeFound("switch1.in=" + UPDATED_SWITCH_1);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasBySwitch1IsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where switch1 is not null
        defaultTransaksiPulsaShouldBeFound("switch1.specified=true");

        // Get all the transaksiPulsaList where switch1 is null
        defaultTransaksiPulsaShouldNotBeFound("switch1.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasBySwitch1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where switch1 greater than or equals to DEFAULT_SWITCH_1
        defaultTransaksiPulsaShouldBeFound("switch1.greaterOrEqualThan=" + DEFAULT_SWITCH_1);

        // Get all the transaksiPulsaList where switch1 greater than or equals to UPDATED_SWITCH_1
        defaultTransaksiPulsaShouldNotBeFound("switch1.greaterOrEqualThan=" + UPDATED_SWITCH_1);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasBySwitch1IsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where switch1 less than or equals to DEFAULT_SWITCH_1
        defaultTransaksiPulsaShouldNotBeFound("switch1.lessThan=" + DEFAULT_SWITCH_1);

        // Get all the transaksiPulsaList where switch1 less than or equals to UPDATED_SWITCH_1
        defaultTransaksiPulsaShouldBeFound("switch1.lessThan=" + UPDATED_SWITCH_1);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasByKodeGagalIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where kodeGagal equals to DEFAULT_KODE_GAGAL
        defaultTransaksiPulsaShouldBeFound("kodeGagal.equals=" + DEFAULT_KODE_GAGAL);

        // Get all the transaksiPulsaList where kodeGagal equals to UPDATED_KODE_GAGAL
        defaultTransaksiPulsaShouldNotBeFound("kodeGagal.equals=" + UPDATED_KODE_GAGAL);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByKodeGagalIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where kodeGagal in DEFAULT_KODE_GAGAL or UPDATED_KODE_GAGAL
        defaultTransaksiPulsaShouldBeFound("kodeGagal.in=" + DEFAULT_KODE_GAGAL + "," + UPDATED_KODE_GAGAL);

        // Get all the transaksiPulsaList where kodeGagal equals to UPDATED_KODE_GAGAL
        defaultTransaksiPulsaShouldNotBeFound("kodeGagal.in=" + UPDATED_KODE_GAGAL);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByKodeGagalIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where kodeGagal is not null
        defaultTransaksiPulsaShouldBeFound("kodeGagal.specified=true");

        // Get all the transaksiPulsaList where kodeGagal is null
        defaultTransaksiPulsaShouldNotBeFound("kodeGagal.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByKodeGagalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where kodeGagal greater than or equals to DEFAULT_KODE_GAGAL
        defaultTransaksiPulsaShouldBeFound("kodeGagal.greaterOrEqualThan=" + DEFAULT_KODE_GAGAL);

        // Get all the transaksiPulsaList where kodeGagal greater than or equals to UPDATED_KODE_GAGAL
        defaultTransaksiPulsaShouldNotBeFound("kodeGagal.greaterOrEqualThan=" + UPDATED_KODE_GAGAL);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByKodeGagalIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where kodeGagal less than or equals to DEFAULT_KODE_GAGAL
        defaultTransaksiPulsaShouldNotBeFound("kodeGagal.lessThan=" + DEFAULT_KODE_GAGAL);

        // Get all the transaksiPulsaList where kodeGagal less than or equals to UPDATED_KODE_GAGAL
        defaultTransaksiPulsaShouldBeFound("kodeGagal.lessThan=" + UPDATED_KODE_GAGAL);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasByWaitSmsIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where waitSms equals to DEFAULT_WAIT_SMS
        defaultTransaksiPulsaShouldBeFound("waitSms.equals=" + DEFAULT_WAIT_SMS);

        // Get all the transaksiPulsaList where waitSms equals to UPDATED_WAIT_SMS
        defaultTransaksiPulsaShouldNotBeFound("waitSms.equals=" + UPDATED_WAIT_SMS);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByWaitSmsIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where waitSms in DEFAULT_WAIT_SMS or UPDATED_WAIT_SMS
        defaultTransaksiPulsaShouldBeFound("waitSms.in=" + DEFAULT_WAIT_SMS + "," + UPDATED_WAIT_SMS);

        // Get all the transaksiPulsaList where waitSms equals to UPDATED_WAIT_SMS
        defaultTransaksiPulsaShouldNotBeFound("waitSms.in=" + UPDATED_WAIT_SMS);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByWaitSmsIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where waitSms is not null
        defaultTransaksiPulsaShouldBeFound("waitSms.specified=true");

        // Get all the transaksiPulsaList where waitSms is null
        defaultTransaksiPulsaShouldNotBeFound("waitSms.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByWaitSmsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where waitSms greater than or equals to DEFAULT_WAIT_SMS
        defaultTransaksiPulsaShouldBeFound("waitSms.greaterOrEqualThan=" + DEFAULT_WAIT_SMS);

        // Get all the transaksiPulsaList where waitSms greater than or equals to UPDATED_WAIT_SMS
        defaultTransaksiPulsaShouldNotBeFound("waitSms.greaterOrEqualThan=" + UPDATED_WAIT_SMS);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByWaitSmsIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where waitSms less than or equals to DEFAULT_WAIT_SMS
        defaultTransaksiPulsaShouldNotBeFound("waitSms.lessThan=" + DEFAULT_WAIT_SMS);

        // Get all the transaksiPulsaList where waitSms less than or equals to UPDATED_WAIT_SMS
        defaultTransaksiPulsaShouldBeFound("waitSms.lessThan=" + UPDATED_WAIT_SMS);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasByHead2HeadIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where head2Head equals to DEFAULT_HEAD_2_HEAD
        defaultTransaksiPulsaShouldBeFound("head2Head.equals=" + DEFAULT_HEAD_2_HEAD);

        // Get all the transaksiPulsaList where head2Head equals to UPDATED_HEAD_2_HEAD
        defaultTransaksiPulsaShouldNotBeFound("head2Head.equals=" + UPDATED_HEAD_2_HEAD);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHead2HeadIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where head2Head in DEFAULT_HEAD_2_HEAD or UPDATED_HEAD_2_HEAD
        defaultTransaksiPulsaShouldBeFound("head2Head.in=" + DEFAULT_HEAD_2_HEAD + "," + UPDATED_HEAD_2_HEAD);

        // Get all the transaksiPulsaList where head2Head equals to UPDATED_HEAD_2_HEAD
        defaultTransaksiPulsaShouldNotBeFound("head2Head.in=" + UPDATED_HEAD_2_HEAD);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHead2HeadIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where head2Head is not null
        defaultTransaksiPulsaShouldBeFound("head2Head.specified=true");

        // Get all the transaksiPulsaList where head2Head is null
        defaultTransaksiPulsaShouldNotBeFound("head2Head.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHead2HeadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where head2Head greater than or equals to DEFAULT_HEAD_2_HEAD
        defaultTransaksiPulsaShouldBeFound("head2Head.greaterOrEqualThan=" + DEFAULT_HEAD_2_HEAD);

        // Get all the transaksiPulsaList where head2Head greater than or equals to UPDATED_HEAD_2_HEAD
        defaultTransaksiPulsaShouldNotBeFound("head2Head.greaterOrEqualThan=" + UPDATED_HEAD_2_HEAD);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHead2HeadIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where head2Head less than or equals to DEFAULT_HEAD_2_HEAD
        defaultTransaksiPulsaShouldNotBeFound("head2Head.lessThan=" + DEFAULT_HEAD_2_HEAD);

        // Get all the transaksiPulsaList where head2Head less than or equals to UPDATED_HEAD_2_HEAD
        defaultTransaksiPulsaShouldBeFound("head2Head.lessThan=" + UPDATED_HEAD_2_HEAD);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasByHpPembeliIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where hpPembeli equals to DEFAULT_HP_PEMBELI
        defaultTransaksiPulsaShouldBeFound("hpPembeli.equals=" + DEFAULT_HP_PEMBELI);

        // Get all the transaksiPulsaList where hpPembeli equals to UPDATED_HP_PEMBELI
        defaultTransaksiPulsaShouldNotBeFound("hpPembeli.equals=" + UPDATED_HP_PEMBELI);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHpPembeliIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where hpPembeli in DEFAULT_HP_PEMBELI or UPDATED_HP_PEMBELI
        defaultTransaksiPulsaShouldBeFound("hpPembeli.in=" + DEFAULT_HP_PEMBELI + "," + UPDATED_HP_PEMBELI);

        // Get all the transaksiPulsaList where hpPembeli equals to UPDATED_HP_PEMBELI
        defaultTransaksiPulsaShouldNotBeFound("hpPembeli.in=" + UPDATED_HP_PEMBELI);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByHpPembeliIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where hpPembeli is not null
        defaultTransaksiPulsaShouldBeFound("hpPembeli.specified=true");

        // Get all the transaksiPulsaList where hpPembeli is null
        defaultTransaksiPulsaShouldNotBeFound("hpPembeli.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByBeaAdminIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where beaAdmin equals to DEFAULT_BEA_ADMIN
        defaultTransaksiPulsaShouldBeFound("beaAdmin.equals=" + DEFAULT_BEA_ADMIN);

        // Get all the transaksiPulsaList where beaAdmin equals to UPDATED_BEA_ADMIN
        defaultTransaksiPulsaShouldNotBeFound("beaAdmin.equals=" + UPDATED_BEA_ADMIN);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByBeaAdminIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where beaAdmin in DEFAULT_BEA_ADMIN or UPDATED_BEA_ADMIN
        defaultTransaksiPulsaShouldBeFound("beaAdmin.in=" + DEFAULT_BEA_ADMIN + "," + UPDATED_BEA_ADMIN);

        // Get all the transaksiPulsaList where beaAdmin equals to UPDATED_BEA_ADMIN
        defaultTransaksiPulsaShouldNotBeFound("beaAdmin.in=" + UPDATED_BEA_ADMIN);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByBeaAdminIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where beaAdmin is not null
        defaultTransaksiPulsaShouldBeFound("beaAdmin.specified=true");

        // Get all the transaksiPulsaList where beaAdmin is null
        defaultTransaksiPulsaShouldNotBeFound("beaAdmin.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIsReportIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where isReport equals to DEFAULT_IS_REPORT
        defaultTransaksiPulsaShouldBeFound("isReport.equals=" + DEFAULT_IS_REPORT);

        // Get all the transaksiPulsaList where isReport equals to UPDATED_IS_REPORT
        defaultTransaksiPulsaShouldNotBeFound("isReport.equals=" + UPDATED_IS_REPORT);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIsReportIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where isReport in DEFAULT_IS_REPORT or UPDATED_IS_REPORT
        defaultTransaksiPulsaShouldBeFound("isReport.in=" + DEFAULT_IS_REPORT + "," + UPDATED_IS_REPORT);

        // Get all the transaksiPulsaList where isReport equals to UPDATED_IS_REPORT
        defaultTransaksiPulsaShouldNotBeFound("isReport.in=" + UPDATED_IS_REPORT);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIsReportIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where isReport is not null
        defaultTransaksiPulsaShouldBeFound("isReport.specified=true");

        // Get all the transaksiPulsaList where isReport is null
        defaultTransaksiPulsaShouldNotBeFound("isReport.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIsReportIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where isReport greater than or equals to DEFAULT_IS_REPORT
        defaultTransaksiPulsaShouldBeFound("isReport.greaterOrEqualThan=" + DEFAULT_IS_REPORT);

        // Get all the transaksiPulsaList where isReport greater than or equals to UPDATED_IS_REPORT
        defaultTransaksiPulsaShouldNotBeFound("isReport.greaterOrEqualThan=" + UPDATED_IS_REPORT);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIsReportIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where isReport less than or equals to DEFAULT_IS_REPORT
        defaultTransaksiPulsaShouldNotBeFound("isReport.lessThan=" + DEFAULT_IS_REPORT);

        // Get all the transaksiPulsaList where isReport less than or equals to UPDATED_IS_REPORT
        defaultTransaksiPulsaShouldBeFound("isReport.lessThan=" + UPDATED_IS_REPORT);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasBySuplierKeIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where suplierKe equals to DEFAULT_SUPLIER_KE
        defaultTransaksiPulsaShouldBeFound("suplierKe.equals=" + DEFAULT_SUPLIER_KE);

        // Get all the transaksiPulsaList where suplierKe equals to UPDATED_SUPLIER_KE
        defaultTransaksiPulsaShouldNotBeFound("suplierKe.equals=" + UPDATED_SUPLIER_KE);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasBySuplierKeIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where suplierKe in DEFAULT_SUPLIER_KE or UPDATED_SUPLIER_KE
        defaultTransaksiPulsaShouldBeFound("suplierKe.in=" + DEFAULT_SUPLIER_KE + "," + UPDATED_SUPLIER_KE);

        // Get all the transaksiPulsaList where suplierKe equals to UPDATED_SUPLIER_KE
        defaultTransaksiPulsaShouldNotBeFound("suplierKe.in=" + UPDATED_SUPLIER_KE);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasBySuplierKeIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where suplierKe is not null
        defaultTransaksiPulsaShouldBeFound("suplierKe.specified=true");

        // Get all the transaksiPulsaList where suplierKe is null
        defaultTransaksiPulsaShouldNotBeFound("suplierKe.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasBySuplierKeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where suplierKe greater than or equals to DEFAULT_SUPLIER_KE
        defaultTransaksiPulsaShouldBeFound("suplierKe.greaterOrEqualThan=" + DEFAULT_SUPLIER_KE);

        // Get all the transaksiPulsaList where suplierKe greater than or equals to UPDATED_SUPLIER_KE
        defaultTransaksiPulsaShouldNotBeFound("suplierKe.greaterOrEqualThan=" + UPDATED_SUPLIER_KE);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasBySuplierKeIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where suplierKe less than or equals to DEFAULT_SUPLIER_KE
        defaultTransaksiPulsaShouldNotBeFound("suplierKe.lessThan=" + DEFAULT_SUPLIER_KE);

        // Get all the transaksiPulsaList where suplierKe less than or equals to UPDATED_SUPLIER_KE
        defaultTransaksiPulsaShouldBeFound("suplierKe.lessThan=" + UPDATED_SUPLIER_KE);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdDistributorIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idDistributor equals to DEFAULT_ID_DISTRIBUTOR
        defaultTransaksiPulsaShouldBeFound("idDistributor.equals=" + DEFAULT_ID_DISTRIBUTOR);

        // Get all the transaksiPulsaList where idDistributor equals to UPDATED_ID_DISTRIBUTOR
        defaultTransaksiPulsaShouldNotBeFound("idDistributor.equals=" + UPDATED_ID_DISTRIBUTOR);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdDistributorIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idDistributor in DEFAULT_ID_DISTRIBUTOR or UPDATED_ID_DISTRIBUTOR
        defaultTransaksiPulsaShouldBeFound("idDistributor.in=" + DEFAULT_ID_DISTRIBUTOR + "," + UPDATED_ID_DISTRIBUTOR);

        // Get all the transaksiPulsaList where idDistributor equals to UPDATED_ID_DISTRIBUTOR
        defaultTransaksiPulsaShouldNotBeFound("idDistributor.in=" + UPDATED_ID_DISTRIBUTOR);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdDistributorIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idDistributor is not null
        defaultTransaksiPulsaShouldBeFound("idDistributor.specified=true");

        // Get all the transaksiPulsaList where idDistributor is null
        defaultTransaksiPulsaShouldNotBeFound("idDistributor.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdDistributorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idDistributor greater than or equals to DEFAULT_ID_DISTRIBUTOR
        defaultTransaksiPulsaShouldBeFound("idDistributor.greaterOrEqualThan=" + DEFAULT_ID_DISTRIBUTOR);

        // Get all the transaksiPulsaList where idDistributor greater than or equals to UPDATED_ID_DISTRIBUTOR
        defaultTransaksiPulsaShouldNotBeFound("idDistributor.greaterOrEqualThan=" + UPDATED_ID_DISTRIBUTOR);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdDistributorIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idDistributor less than or equals to DEFAULT_ID_DISTRIBUTOR
        defaultTransaksiPulsaShouldNotBeFound("idDistributor.lessThan=" + DEFAULT_ID_DISTRIBUTOR);

        // Get all the transaksiPulsaList where idDistributor less than or equals to UPDATED_ID_DISTRIBUTOR
        defaultTransaksiPulsaShouldBeFound("idDistributor.lessThan=" + UPDATED_ID_DISTRIBUTOR);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasBySnIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where sn equals to DEFAULT_SN
        defaultTransaksiPulsaShouldBeFound("sn.equals=" + DEFAULT_SN);

        // Get all the transaksiPulsaList where sn equals to UPDATED_SN
        defaultTransaksiPulsaShouldNotBeFound("sn.equals=" + UPDATED_SN);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasBySnIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where sn in DEFAULT_SN or UPDATED_SN
        defaultTransaksiPulsaShouldBeFound("sn.in=" + DEFAULT_SN + "," + UPDATED_SN);

        // Get all the transaksiPulsaList where sn equals to UPDATED_SN
        defaultTransaksiPulsaShouldNotBeFound("sn.in=" + UPDATED_SN);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasBySnIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where sn is not null
        defaultTransaksiPulsaShouldBeFound("sn.specified=true");

        // Get all the transaksiPulsaList where sn is null
        defaultTransaksiPulsaShouldNotBeFound("sn.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIpIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where ip equals to DEFAULT_IP
        defaultTransaksiPulsaShouldBeFound("ip.equals=" + DEFAULT_IP);

        // Get all the transaksiPulsaList where ip equals to UPDATED_IP
        defaultTransaksiPulsaShouldNotBeFound("ip.equals=" + UPDATED_IP);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIpIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where ip in DEFAULT_IP or UPDATED_IP
        defaultTransaksiPulsaShouldBeFound("ip.in=" + DEFAULT_IP + "," + UPDATED_IP);

        // Get all the transaksiPulsaList where ip equals to UPDATED_IP
        defaultTransaksiPulsaShouldNotBeFound("ip.in=" + UPDATED_IP);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIpIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where ip is not null
        defaultTransaksiPulsaShouldBeFound("ip.specified=true");

        // Get all the transaksiPulsaList where ip is null
        defaultTransaksiPulsaShouldNotBeFound("ip.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByPesankirimIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where pesankirim equals to DEFAULT_PESANKIRIM
        defaultTransaksiPulsaShouldBeFound("pesankirim.equals=" + DEFAULT_PESANKIRIM);

        // Get all the transaksiPulsaList where pesankirim equals to UPDATED_PESANKIRIM
        defaultTransaksiPulsaShouldNotBeFound("pesankirim.equals=" + UPDATED_PESANKIRIM);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByPesankirimIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where pesankirim in DEFAULT_PESANKIRIM or UPDATED_PESANKIRIM
        defaultTransaksiPulsaShouldBeFound("pesankirim.in=" + DEFAULT_PESANKIRIM + "," + UPDATED_PESANKIRIM);

        // Get all the transaksiPulsaList where pesankirim equals to UPDATED_PESANKIRIM
        defaultTransaksiPulsaShouldNotBeFound("pesankirim.in=" + UPDATED_PESANKIRIM);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByPesankirimIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where pesankirim is not null
        defaultTransaksiPulsaShouldBeFound("pesankirim.specified=true");

        // Get all the transaksiPulsaList where pesankirim is null
        defaultTransaksiPulsaShouldNotBeFound("pesankirim.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByMetodeIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where metode equals to DEFAULT_METODE
        defaultTransaksiPulsaShouldBeFound("metode.equals=" + DEFAULT_METODE);

        // Get all the transaksiPulsaList where metode equals to UPDATED_METODE
        defaultTransaksiPulsaShouldNotBeFound("metode.equals=" + UPDATED_METODE);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByMetodeIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where metode in DEFAULT_METODE or UPDATED_METODE
        defaultTransaksiPulsaShouldBeFound("metode.in=" + DEFAULT_METODE + "," + UPDATED_METODE);

        // Get all the transaksiPulsaList where metode equals to UPDATED_METODE
        defaultTransaksiPulsaShouldNotBeFound("metode.in=" + UPDATED_METODE);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByMetodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where metode is not null
        defaultTransaksiPulsaShouldBeFound("metode.specified=true");

        // Get all the transaksiPulsaList where metode is null
        defaultTransaksiPulsaShouldNotBeFound("metode.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByMetodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where metode greater than or equals to DEFAULT_METODE
        defaultTransaksiPulsaShouldBeFound("metode.greaterOrEqualThan=" + DEFAULT_METODE);

        // Get all the transaksiPulsaList where metode greater than or equals to UPDATED_METODE
        defaultTransaksiPulsaShouldNotBeFound("metode.greaterOrEqualThan=" + UPDATED_METODE);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByMetodeIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where metode less than or equals to DEFAULT_METODE
        defaultTransaksiPulsaShouldNotBeFound("metode.lessThan=" + DEFAULT_METODE);

        // Get all the transaksiPulsaList where metode less than or equals to UPDATED_METODE
        defaultTransaksiPulsaShouldBeFound("metode.lessThan=" + UPDATED_METODE);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasByToDistributorIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where toDistributor equals to DEFAULT_TO_DISTRIBUTOR
        defaultTransaksiPulsaShouldBeFound("toDistributor.equals=" + DEFAULT_TO_DISTRIBUTOR);

        // Get all the transaksiPulsaList where toDistributor equals to UPDATED_TO_DISTRIBUTOR
        defaultTransaksiPulsaShouldNotBeFound("toDistributor.equals=" + UPDATED_TO_DISTRIBUTOR);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByToDistributorIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where toDistributor in DEFAULT_TO_DISTRIBUTOR or UPDATED_TO_DISTRIBUTOR
        defaultTransaksiPulsaShouldBeFound("toDistributor.in=" + DEFAULT_TO_DISTRIBUTOR + "," + UPDATED_TO_DISTRIBUTOR);

        // Get all the transaksiPulsaList where toDistributor equals to UPDATED_TO_DISTRIBUTOR
        defaultTransaksiPulsaShouldNotBeFound("toDistributor.in=" + UPDATED_TO_DISTRIBUTOR);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByToDistributorIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where toDistributor is not null
        defaultTransaksiPulsaShouldBeFound("toDistributor.specified=true");

        // Get all the transaksiPulsaList where toDistributor is null
        defaultTransaksiPulsaShouldNotBeFound("toDistributor.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdPortipIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idPortip equals to DEFAULT_ID_PORTIP
        defaultTransaksiPulsaShouldBeFound("idPortip.equals=" + DEFAULT_ID_PORTIP);

        // Get all the transaksiPulsaList where idPortip equals to UPDATED_ID_PORTIP
        defaultTransaksiPulsaShouldNotBeFound("idPortip.equals=" + UPDATED_ID_PORTIP);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdPortipIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idPortip in DEFAULT_ID_PORTIP or UPDATED_ID_PORTIP
        defaultTransaksiPulsaShouldBeFound("idPortip.in=" + DEFAULT_ID_PORTIP + "," + UPDATED_ID_PORTIP);

        // Get all the transaksiPulsaList where idPortip equals to UPDATED_ID_PORTIP
        defaultTransaksiPulsaShouldNotBeFound("idPortip.in=" + UPDATED_ID_PORTIP);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdPortipIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idPortip is not null
        defaultTransaksiPulsaShouldBeFound("idPortip.specified=true");

        // Get all the transaksiPulsaList where idPortip is null
        defaultTransaksiPulsaShouldNotBeFound("idPortip.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdPortipIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idPortip greater than or equals to DEFAULT_ID_PORTIP
        defaultTransaksiPulsaShouldBeFound("idPortip.greaterOrEqualThan=" + DEFAULT_ID_PORTIP);

        // Get all the transaksiPulsaList where idPortip greater than or equals to UPDATED_ID_PORTIP
        defaultTransaksiPulsaShouldNotBeFound("idPortip.greaterOrEqualThan=" + UPDATED_ID_PORTIP);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdPortipIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idPortip less than or equals to DEFAULT_ID_PORTIP
        defaultTransaksiPulsaShouldNotBeFound("idPortip.lessThan=" + DEFAULT_ID_PORTIP);

        // Get all the transaksiPulsaList where idPortip less than or equals to UPDATED_ID_PORTIP
        defaultTransaksiPulsaShouldBeFound("idPortip.lessThan=" + UPDATED_ID_PORTIP);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasByTimeupdateIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where timeupdate equals to DEFAULT_TIMEUPDATE
        defaultTransaksiPulsaShouldBeFound("timeupdate.equals=" + DEFAULT_TIMEUPDATE);

        // Get all the transaksiPulsaList where timeupdate equals to UPDATED_TIMEUPDATE
        defaultTransaksiPulsaShouldNotBeFound("timeupdate.equals=" + UPDATED_TIMEUPDATE);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByTimeupdateIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where timeupdate in DEFAULT_TIMEUPDATE or UPDATED_TIMEUPDATE
        defaultTransaksiPulsaShouldBeFound("timeupdate.in=" + DEFAULT_TIMEUPDATE + "," + UPDATED_TIMEUPDATE);

        // Get all the transaksiPulsaList where timeupdate equals to UPDATED_TIMEUPDATE
        defaultTransaksiPulsaShouldNotBeFound("timeupdate.in=" + UPDATED_TIMEUPDATE);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByTimeupdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where timeupdate is not null
        defaultTransaksiPulsaShouldBeFound("timeupdate.specified=true");

        // Get all the transaksiPulsaList where timeupdate is null
        defaultTransaksiPulsaShouldNotBeFound("timeupdate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByTimeupdateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where timeupdate greater than or equals to DEFAULT_TIMEUPDATE
        defaultTransaksiPulsaShouldBeFound("timeupdate.greaterOrEqualThan=" + DEFAULT_TIMEUPDATE);

        // Get all the transaksiPulsaList where timeupdate greater than or equals to UPDATED_TIMEUPDATE
        defaultTransaksiPulsaShouldNotBeFound("timeupdate.greaterOrEqualThan=" + UPDATED_TIMEUPDATE);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByTimeupdateIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where timeupdate less than or equals to DEFAULT_TIMEUPDATE
        defaultTransaksiPulsaShouldNotBeFound("timeupdate.lessThan=" + DEFAULT_TIMEUPDATE);

        // Get all the transaksiPulsaList where timeupdate less than or equals to UPDATED_TIMEUPDATE
        defaultTransaksiPulsaShouldBeFound("timeupdate.lessThan=" + UPDATED_TIMEUPDATE);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdDistributorOldIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idDistributorOld equals to DEFAULT_ID_DISTRIBUTOR_OLD
        defaultTransaksiPulsaShouldBeFound("idDistributorOld.equals=" + DEFAULT_ID_DISTRIBUTOR_OLD);

        // Get all the transaksiPulsaList where idDistributorOld equals to UPDATED_ID_DISTRIBUTOR_OLD
        defaultTransaksiPulsaShouldNotBeFound("idDistributorOld.equals=" + UPDATED_ID_DISTRIBUTOR_OLD);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdDistributorOldIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idDistributorOld in DEFAULT_ID_DISTRIBUTOR_OLD or UPDATED_ID_DISTRIBUTOR_OLD
        defaultTransaksiPulsaShouldBeFound("idDistributorOld.in=" + DEFAULT_ID_DISTRIBUTOR_OLD + "," + UPDATED_ID_DISTRIBUTOR_OLD);

        // Get all the transaksiPulsaList where idDistributorOld equals to UPDATED_ID_DISTRIBUTOR_OLD
        defaultTransaksiPulsaShouldNotBeFound("idDistributorOld.in=" + UPDATED_ID_DISTRIBUTOR_OLD);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdDistributorOldIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idDistributorOld is not null
        defaultTransaksiPulsaShouldBeFound("idDistributorOld.specified=true");

        // Get all the transaksiPulsaList where idDistributorOld is null
        defaultTransaksiPulsaShouldNotBeFound("idDistributorOld.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdDistributorOldIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idDistributorOld greater than or equals to DEFAULT_ID_DISTRIBUTOR_OLD
        defaultTransaksiPulsaShouldBeFound("idDistributorOld.greaterOrEqualThan=" + DEFAULT_ID_DISTRIBUTOR_OLD);

        // Get all the transaksiPulsaList where idDistributorOld greater than or equals to UPDATED_ID_DISTRIBUTOR_OLD
        defaultTransaksiPulsaShouldNotBeFound("idDistributorOld.greaterOrEqualThan=" + UPDATED_ID_DISTRIBUTOR_OLD);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdDistributorOldIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idDistributorOld less than or equals to DEFAULT_ID_DISTRIBUTOR_OLD
        defaultTransaksiPulsaShouldNotBeFound("idDistributorOld.lessThan=" + DEFAULT_ID_DISTRIBUTOR_OLD);

        // Get all the transaksiPulsaList where idDistributorOld less than or equals to UPDATED_ID_DISTRIBUTOR_OLD
        defaultTransaksiPulsaShouldBeFound("idDistributorOld.lessThan=" + UPDATED_ID_DISTRIBUTOR_OLD);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdDistributorProdukIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idDistributorProduk equals to DEFAULT_ID_DISTRIBUTOR_PRODUK
        defaultTransaksiPulsaShouldBeFound("idDistributorProduk.equals=" + DEFAULT_ID_DISTRIBUTOR_PRODUK);

        // Get all the transaksiPulsaList where idDistributorProduk equals to UPDATED_ID_DISTRIBUTOR_PRODUK
        defaultTransaksiPulsaShouldNotBeFound("idDistributorProduk.equals=" + UPDATED_ID_DISTRIBUTOR_PRODUK);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdDistributorProdukIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idDistributorProduk in DEFAULT_ID_DISTRIBUTOR_PRODUK or UPDATED_ID_DISTRIBUTOR_PRODUK
        defaultTransaksiPulsaShouldBeFound("idDistributorProduk.in=" + DEFAULT_ID_DISTRIBUTOR_PRODUK + "," + UPDATED_ID_DISTRIBUTOR_PRODUK);

        // Get all the transaksiPulsaList where idDistributorProduk equals to UPDATED_ID_DISTRIBUTOR_PRODUK
        defaultTransaksiPulsaShouldNotBeFound("idDistributorProduk.in=" + UPDATED_ID_DISTRIBUTOR_PRODUK);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdDistributorProdukIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idDistributorProduk is not null
        defaultTransaksiPulsaShouldBeFound("idDistributorProduk.specified=true");

        // Get all the transaksiPulsaList where idDistributorProduk is null
        defaultTransaksiPulsaShouldNotBeFound("idDistributorProduk.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdDistributorProdukIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idDistributorProduk greater than or equals to DEFAULT_ID_DISTRIBUTOR_PRODUK
        defaultTransaksiPulsaShouldBeFound("idDistributorProduk.greaterOrEqualThan=" + DEFAULT_ID_DISTRIBUTOR_PRODUK);

        // Get all the transaksiPulsaList where idDistributorProduk greater than or equals to UPDATED_ID_DISTRIBUTOR_PRODUK
        defaultTransaksiPulsaShouldNotBeFound("idDistributorProduk.greaterOrEqualThan=" + UPDATED_ID_DISTRIBUTOR_PRODUK);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIdDistributorProdukIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where idDistributorProduk less than or equals to DEFAULT_ID_DISTRIBUTOR_PRODUK
        defaultTransaksiPulsaShouldNotBeFound("idDistributorProduk.lessThan=" + DEFAULT_ID_DISTRIBUTOR_PRODUK);

        // Get all the transaksiPulsaList where idDistributorProduk less than or equals to UPDATED_ID_DISTRIBUTOR_PRODUK
        defaultTransaksiPulsaShouldBeFound("idDistributorProduk.lessThan=" + UPDATED_ID_DISTRIBUTOR_PRODUK);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasBySaldoSupIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where saldoSup equals to DEFAULT_SALDO_SUP
        defaultTransaksiPulsaShouldBeFound("saldoSup.equals=" + DEFAULT_SALDO_SUP);

        // Get all the transaksiPulsaList where saldoSup equals to UPDATED_SALDO_SUP
        defaultTransaksiPulsaShouldNotBeFound("saldoSup.equals=" + UPDATED_SALDO_SUP);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasBySaldoSupIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where saldoSup in DEFAULT_SALDO_SUP or UPDATED_SALDO_SUP
        defaultTransaksiPulsaShouldBeFound("saldoSup.in=" + DEFAULT_SALDO_SUP + "," + UPDATED_SALDO_SUP);

        // Get all the transaksiPulsaList where saldoSup equals to UPDATED_SALDO_SUP
        defaultTransaksiPulsaShouldNotBeFound("saldoSup.in=" + UPDATED_SALDO_SUP);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasBySaldoSupIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where saldoSup is not null
        defaultTransaksiPulsaShouldBeFound("saldoSup.specified=true");

        // Get all the transaksiPulsaList where saldoSup is null
        defaultTransaksiPulsaShouldNotBeFound("saldoSup.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIsrebateIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where isrebate equals to DEFAULT_ISREBATE
        defaultTransaksiPulsaShouldBeFound("isrebate.equals=" + DEFAULT_ISREBATE);

        // Get all the transaksiPulsaList where isrebate equals to UPDATED_ISREBATE
        defaultTransaksiPulsaShouldNotBeFound("isrebate.equals=" + UPDATED_ISREBATE);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIsrebateIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where isrebate in DEFAULT_ISREBATE or UPDATED_ISREBATE
        defaultTransaksiPulsaShouldBeFound("isrebate.in=" + DEFAULT_ISREBATE + "," + UPDATED_ISREBATE);

        // Get all the transaksiPulsaList where isrebate equals to UPDATED_ISREBATE
        defaultTransaksiPulsaShouldNotBeFound("isrebate.in=" + UPDATED_ISREBATE);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIsrebateIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where isrebate is not null
        defaultTransaksiPulsaShouldBeFound("isrebate.specified=true");

        // Get all the transaksiPulsaList where isrebate is null
        defaultTransaksiPulsaShouldNotBeFound("isrebate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIsrebateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where isrebate greater than or equals to DEFAULT_ISREBATE
        defaultTransaksiPulsaShouldBeFound("isrebate.greaterOrEqualThan=" + DEFAULT_ISREBATE);

        // Get all the transaksiPulsaList where isrebate greater than or equals to UPDATED_ISREBATE
        defaultTransaksiPulsaShouldNotBeFound("isrebate.greaterOrEqualThan=" + UPDATED_ISREBATE);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIsrebateIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where isrebate less than or equals to DEFAULT_ISREBATE
        defaultTransaksiPulsaShouldNotBeFound("isrebate.lessThan=" + DEFAULT_ISREBATE);

        // Get all the transaksiPulsaList where isrebate less than or equals to UPDATED_ISREBATE
        defaultTransaksiPulsaShouldBeFound("isrebate.lessThan=" + UPDATED_ISREBATE);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasByEnginenameIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where enginename equals to DEFAULT_ENGINENAME
        defaultTransaksiPulsaShouldBeFound("enginename.equals=" + DEFAULT_ENGINENAME);

        // Get all the transaksiPulsaList where enginename equals to UPDATED_ENGINENAME
        defaultTransaksiPulsaShouldNotBeFound("enginename.equals=" + UPDATED_ENGINENAME);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByEnginenameIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where enginename in DEFAULT_ENGINENAME or UPDATED_ENGINENAME
        defaultTransaksiPulsaShouldBeFound("enginename.in=" + DEFAULT_ENGINENAME + "," + UPDATED_ENGINENAME);

        // Get all the transaksiPulsaList where enginename equals to UPDATED_ENGINENAME
        defaultTransaksiPulsaShouldNotBeFound("enginename.in=" + UPDATED_ENGINENAME);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByEnginenameIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where enginename is not null
        defaultTransaksiPulsaShouldBeFound("enginename.specified=true");

        // Get all the transaksiPulsaList where enginename is null
        defaultTransaksiPulsaShouldNotBeFound("enginename.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByTypemsgIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where typemsg equals to DEFAULT_TYPEMSG
        defaultTransaksiPulsaShouldBeFound("typemsg.equals=" + DEFAULT_TYPEMSG);

        // Get all the transaksiPulsaList where typemsg equals to UPDATED_TYPEMSG
        defaultTransaksiPulsaShouldNotBeFound("typemsg.equals=" + UPDATED_TYPEMSG);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByTypemsgIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where typemsg in DEFAULT_TYPEMSG or UPDATED_TYPEMSG
        defaultTransaksiPulsaShouldBeFound("typemsg.in=" + DEFAULT_TYPEMSG + "," + UPDATED_TYPEMSG);

        // Get all the transaksiPulsaList where typemsg equals to UPDATED_TYPEMSG
        defaultTransaksiPulsaShouldNotBeFound("typemsg.in=" + UPDATED_TYPEMSG);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByTypemsgIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where typemsg is not null
        defaultTransaksiPulsaShouldBeFound("typemsg.specified=true");

        // Get all the transaksiPulsaList where typemsg is null
        defaultTransaksiPulsaShouldNotBeFound("typemsg.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByTypemsgIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where typemsg greater than or equals to DEFAULT_TYPEMSG
        defaultTransaksiPulsaShouldBeFound("typemsg.greaterOrEqualThan=" + DEFAULT_TYPEMSG);

        // Get all the transaksiPulsaList where typemsg greater than or equals to UPDATED_TYPEMSG
        defaultTransaksiPulsaShouldNotBeFound("typemsg.greaterOrEqualThan=" + UPDATED_TYPEMSG);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByTypemsgIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where typemsg less than or equals to DEFAULT_TYPEMSG
        defaultTransaksiPulsaShouldNotBeFound("typemsg.lessThan=" + DEFAULT_TYPEMSG);

        // Get all the transaksiPulsaList where typemsg less than or equals to UPDATED_TYPEMSG
        defaultTransaksiPulsaShouldBeFound("typemsg.lessThan=" + UPDATED_TYPEMSG);
    }


    @Test
    @Transactional
    public void getAllTransaksiPulsasByIsroIsEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where isro equals to DEFAULT_ISRO
        defaultTransaksiPulsaShouldBeFound("isro.equals=" + DEFAULT_ISRO);

        // Get all the transaksiPulsaList where isro equals to UPDATED_ISRO
        defaultTransaksiPulsaShouldNotBeFound("isro.equals=" + UPDATED_ISRO);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIsroIsInShouldWork() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where isro in DEFAULT_ISRO or UPDATED_ISRO
        defaultTransaksiPulsaShouldBeFound("isro.in=" + DEFAULT_ISRO + "," + UPDATED_ISRO);

        // Get all the transaksiPulsaList where isro equals to UPDATED_ISRO
        defaultTransaksiPulsaShouldNotBeFound("isro.in=" + UPDATED_ISRO);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIsroIsNullOrNotNull() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where isro is not null
        defaultTransaksiPulsaShouldBeFound("isro.specified=true");

        // Get all the transaksiPulsaList where isro is null
        defaultTransaksiPulsaShouldNotBeFound("isro.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIsroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where isro greater than or equals to DEFAULT_ISRO
        defaultTransaksiPulsaShouldBeFound("isro.greaterOrEqualThan=" + DEFAULT_ISRO);

        // Get all the transaksiPulsaList where isro greater than or equals to UPDATED_ISRO
        defaultTransaksiPulsaShouldNotBeFound("isro.greaterOrEqualThan=" + UPDATED_ISRO);
    }

    @Test
    @Transactional
    public void getAllTransaksiPulsasByIsroIsLessThanSomething() throws Exception {
        // Initialize the database
        transaksiPulsaRepository.saveAndFlush(transaksiPulsa);

        // Get all the transaksiPulsaList where isro less than or equals to DEFAULT_ISRO
        defaultTransaksiPulsaShouldNotBeFound("isro.lessThan=" + DEFAULT_ISRO);

        // Get all the transaksiPulsaList where isro less than or equals to UPDATED_ISRO
        defaultTransaksiPulsaShouldBeFound("isro.lessThan=" + UPDATED_ISRO);
    }


    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTransaksiPulsaShouldBeFound(String filter) throws Exception {
        restTransaksiPulsaMockMvc.perform(get("/api/transaksi-pulsas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaksiPulsa.getId().intValue())))
            .andExpect(jsonPath("$.[*].kodeProduk").value(hasItem(DEFAULT_KODE_PRODUK.toString())))
            .andExpect(jsonPath("$.[*].hpTujuan").value(hasItem(DEFAULT_HP_TUJUAN.toString())))
            .andExpect(jsonPath("$.[*].hpMember").value(hasItem(DEFAULT_HP_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].hargaBeli").value(hasItem(DEFAULT_HARGA_BELI.intValue())))
            .andExpect(jsonPath("$.[*].hpp").value(hasItem(DEFAULT_HPP.intValue())))
            .andExpect(jsonPath("$.[*].laba").value(hasItem(DEFAULT_LABA.intValue())))
            .andExpect(jsonPath("$.[*].com").value(hasItem(DEFAULT_COM.toString())))
            .andExpect(jsonPath("$.[*].admrpt").value(hasItem(DEFAULT_ADMRPT.intValue())))
            .andExpect(jsonPath("$.[*].ulang").value(hasItem(DEFAULT_ULANG)))
            .andExpect(jsonPath("$.[*].ulangTgl").value(hasItem(DEFAULT_ULANG_TGL.toString())))
            .andExpect(jsonPath("$.[*].fisik").value(hasItem(DEFAULT_FISIK)))
            .andExpect(jsonPath("$.[*].manual").value(hasItem(DEFAULT_MANUAL)))
            .andExpect(jsonPath("$.[*].switch1").value(hasItem(DEFAULT_SWITCH_1)))
            .andExpect(jsonPath("$.[*].kodeGagal").value(hasItem(DEFAULT_KODE_GAGAL)))
            .andExpect(jsonPath("$.[*].waitSms").value(hasItem(DEFAULT_WAIT_SMS)))
            .andExpect(jsonPath("$.[*].head2Head").value(hasItem(DEFAULT_HEAD_2_HEAD)))
            .andExpect(jsonPath("$.[*].hpPembeli").value(hasItem(DEFAULT_HP_PEMBELI.toString())))
            .andExpect(jsonPath("$.[*].beaAdmin").value(hasItem(DEFAULT_BEA_ADMIN.intValue())))
            .andExpect(jsonPath("$.[*].isReport").value(hasItem(DEFAULT_IS_REPORT)))
            .andExpect(jsonPath("$.[*].suplierKe").value(hasItem(DEFAULT_SUPLIER_KE)))
            .andExpect(jsonPath("$.[*].idDistributor").value(hasItem(DEFAULT_ID_DISTRIBUTOR.intValue())))
            .andExpect(jsonPath("$.[*].sn").value(hasItem(DEFAULT_SN.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].pesankirim").value(hasItem(DEFAULT_PESANKIRIM.toString())))
            .andExpect(jsonPath("$.[*].metode").value(hasItem(DEFAULT_METODE)))
            .andExpect(jsonPath("$.[*].toDistributor").value(hasItem(DEFAULT_TO_DISTRIBUTOR.toString())))
            .andExpect(jsonPath("$.[*].idPortip").value(hasItem(DEFAULT_ID_PORTIP)))
            .andExpect(jsonPath("$.[*].timeupdate").value(hasItem(sameInstant(DEFAULT_TIMEUPDATE))))
            .andExpect(jsonPath("$.[*].idDistributorOld").value(hasItem(DEFAULT_ID_DISTRIBUTOR_OLD.intValue())))
            .andExpect(jsonPath("$.[*].idDistributorProduk").value(hasItem(DEFAULT_ID_DISTRIBUTOR_PRODUK.intValue())))
            .andExpect(jsonPath("$.[*].saldoSup").value(hasItem(DEFAULT_SALDO_SUP.intValue())))
            .andExpect(jsonPath("$.[*].isrebate").value(hasItem(DEFAULT_ISREBATE)))
            .andExpect(jsonPath("$.[*].enginename").value(hasItem(DEFAULT_ENGINENAME.toString())))
            .andExpect(jsonPath("$.[*].typemsg").value(hasItem(DEFAULT_TYPEMSG)))
            .andExpect(jsonPath("$.[*].isro").value(hasItem(DEFAULT_ISRO)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTransaksiPulsaShouldNotBeFound(String filter) throws Exception {
        restTransaksiPulsaMockMvc.perform(get("/api/transaksi-pulsas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingTransaksiPulsa() throws Exception {
        // Get the transaksiPulsa
        restTransaksiPulsaMockMvc.perform(get("/api/transaksi-pulsas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaksiPulsa() throws Exception {
        // Initialize the database
        transaksiPulsaService.save(transaksiPulsa);

        int databaseSizeBeforeUpdate = transaksiPulsaRepository.findAll().size();

        // Update the transaksiPulsa
        TransaksiPulsa updatedTransaksiPulsa = transaksiPulsaRepository.findOne(transaksiPulsa.getId());
        updatedTransaksiPulsa
            .kodeProduk(UPDATED_KODE_PRODUK)
            .hpTujuan(UPDATED_HP_TUJUAN)
            .hpMember(UPDATED_HP_MEMBER)
            .hargaBeli(UPDATED_HARGA_BELI)
            .hpp(UPDATED_HPP)
            .laba(UPDATED_LABA)
            .com(UPDATED_COM)
            .admrpt(UPDATED_ADMRPT)
            .ulang(UPDATED_ULANG)
            .ulangTgl(UPDATED_ULANG_TGL)
            .fisik(UPDATED_FISIK)
            .manual(UPDATED_MANUAL)
            .switch1(UPDATED_SWITCH_1)
            .kodeGagal(UPDATED_KODE_GAGAL)
            .waitSms(UPDATED_WAIT_SMS)
            .head2Head(UPDATED_HEAD_2_HEAD)
            .hpPembeli(UPDATED_HP_PEMBELI)
            .beaAdmin(UPDATED_BEA_ADMIN)
            .isReport(UPDATED_IS_REPORT)
            .suplierKe(UPDATED_SUPLIER_KE)
            .idDistributor(UPDATED_ID_DISTRIBUTOR)
            .sn(UPDATED_SN)
            .ip(UPDATED_IP)
            .pesankirim(UPDATED_PESANKIRIM)
            .metode(UPDATED_METODE)
            .toDistributor(UPDATED_TO_DISTRIBUTOR)
            .idPortip(UPDATED_ID_PORTIP)
            .timeupdate(UPDATED_TIMEUPDATE)
            .idDistributorOld(UPDATED_ID_DISTRIBUTOR_OLD)
            .idDistributorProduk(UPDATED_ID_DISTRIBUTOR_PRODUK)
            .saldoSup(UPDATED_SALDO_SUP)
            .isrebate(UPDATED_ISREBATE)
            .enginename(UPDATED_ENGINENAME)
            .typemsg(UPDATED_TYPEMSG)
            .isro(UPDATED_ISRO);

        restTransaksiPulsaMockMvc.perform(put("/api/transaksi-pulsas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransaksiPulsa)))
            .andExpect(status().isOk());

        // Validate the TransaksiPulsa in the database
        List<TransaksiPulsa> transaksiPulsaList = transaksiPulsaRepository.findAll();
        assertThat(transaksiPulsaList).hasSize(databaseSizeBeforeUpdate);
        TransaksiPulsa testTransaksiPulsa = transaksiPulsaList.get(transaksiPulsaList.size() - 1);
        assertThat(testTransaksiPulsa.getKodeProduk()).isEqualTo(UPDATED_KODE_PRODUK);
        assertThat(testTransaksiPulsa.getHpTujuan()).isEqualTo(UPDATED_HP_TUJUAN);
        assertThat(testTransaksiPulsa.getHpMember()).isEqualTo(UPDATED_HP_MEMBER);
        assertThat(testTransaksiPulsa.getHargaBeli()).isEqualTo(UPDATED_HARGA_BELI);
        assertThat(testTransaksiPulsa.getHpp()).isEqualTo(UPDATED_HPP);
        assertThat(testTransaksiPulsa.getLaba()).isEqualTo(UPDATED_LABA);
        assertThat(testTransaksiPulsa.getCom()).isEqualTo(UPDATED_COM);
        assertThat(testTransaksiPulsa.getAdmrpt()).isEqualTo(UPDATED_ADMRPT);
        assertThat(testTransaksiPulsa.getUlang()).isEqualTo(UPDATED_ULANG);
        assertThat(testTransaksiPulsa.getUlangTgl()).isEqualTo(UPDATED_ULANG_TGL);
        assertThat(testTransaksiPulsa.getFisik()).isEqualTo(UPDATED_FISIK);
        assertThat(testTransaksiPulsa.getManual()).isEqualTo(UPDATED_MANUAL);
        assertThat(testTransaksiPulsa.getSwitch1()).isEqualTo(UPDATED_SWITCH_1);
        assertThat(testTransaksiPulsa.getKodeGagal()).isEqualTo(UPDATED_KODE_GAGAL);
        assertThat(testTransaksiPulsa.getWaitSms()).isEqualTo(UPDATED_WAIT_SMS);
        assertThat(testTransaksiPulsa.getHead2Head()).isEqualTo(UPDATED_HEAD_2_HEAD);
        assertThat(testTransaksiPulsa.getHpPembeli()).isEqualTo(UPDATED_HP_PEMBELI);
        assertThat(testTransaksiPulsa.getBeaAdmin()).isEqualTo(UPDATED_BEA_ADMIN);
        assertThat(testTransaksiPulsa.getIsReport()).isEqualTo(UPDATED_IS_REPORT);
        assertThat(testTransaksiPulsa.getSuplierKe()).isEqualTo(UPDATED_SUPLIER_KE);
        assertThat(testTransaksiPulsa.getIdDistributor()).isEqualTo(UPDATED_ID_DISTRIBUTOR);
        assertThat(testTransaksiPulsa.getSn()).isEqualTo(UPDATED_SN);
        assertThat(testTransaksiPulsa.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testTransaksiPulsa.getPesankirim()).isEqualTo(UPDATED_PESANKIRIM);
        assertThat(testTransaksiPulsa.getMetode()).isEqualTo(UPDATED_METODE);
        assertThat(testTransaksiPulsa.getToDistributor()).isEqualTo(UPDATED_TO_DISTRIBUTOR);
        assertThat(testTransaksiPulsa.getIdPortip()).isEqualTo(UPDATED_ID_PORTIP);
        assertThat(testTransaksiPulsa.getTimeupdate()).isEqualTo(UPDATED_TIMEUPDATE);
        assertThat(testTransaksiPulsa.getIdDistributorOld()).isEqualTo(UPDATED_ID_DISTRIBUTOR_OLD);
        assertThat(testTransaksiPulsa.getIdDistributorProduk()).isEqualTo(UPDATED_ID_DISTRIBUTOR_PRODUK);
        assertThat(testTransaksiPulsa.getSaldoSup()).isEqualTo(UPDATED_SALDO_SUP);
        assertThat(testTransaksiPulsa.getIsrebate()).isEqualTo(UPDATED_ISREBATE);
        assertThat(testTransaksiPulsa.getEnginename()).isEqualTo(UPDATED_ENGINENAME);
        assertThat(testTransaksiPulsa.getTypemsg()).isEqualTo(UPDATED_TYPEMSG);
        assertThat(testTransaksiPulsa.getIsro()).isEqualTo(UPDATED_ISRO);

        // Validate the TransaksiPulsa in Elasticsearch
        TransaksiPulsa transaksiPulsaEs = transaksiPulsaSearchRepository.findOne(testTransaksiPulsa.getId());
        assertThat(transaksiPulsaEs).isEqualToComparingFieldByField(testTransaksiPulsa);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaksiPulsa() throws Exception {
        int databaseSizeBeforeUpdate = transaksiPulsaRepository.findAll().size();

        // Create the TransaksiPulsa

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTransaksiPulsaMockMvc.perform(put("/api/transaksi-pulsas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaksiPulsa)))
            .andExpect(status().isCreated());

        // Validate the TransaksiPulsa in the database
        List<TransaksiPulsa> transaksiPulsaList = transaksiPulsaRepository.findAll();
        assertThat(transaksiPulsaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTransaksiPulsa() throws Exception {
        // Initialize the database
        transaksiPulsaService.save(transaksiPulsa);

        int databaseSizeBeforeDelete = transaksiPulsaRepository.findAll().size();

        // Get the transaksiPulsa
        restTransaksiPulsaMockMvc.perform(delete("/api/transaksi-pulsas/{id}", transaksiPulsa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean transaksiPulsaExistsInEs = transaksiPulsaSearchRepository.exists(transaksiPulsa.getId());
        assertThat(transaksiPulsaExistsInEs).isFalse();

        // Validate the database is empty
        List<TransaksiPulsa> transaksiPulsaList = transaksiPulsaRepository.findAll();
        assertThat(transaksiPulsaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTransaksiPulsa() throws Exception {
        // Initialize the database
        transaksiPulsaService.save(transaksiPulsa);

        // Search the transaksiPulsa
        restTransaksiPulsaMockMvc.perform(get("/api/_search/transaksi-pulsas?query=id:" + transaksiPulsa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaksiPulsa.getId().intValue())))
            .andExpect(jsonPath("$.[*].kodeProduk").value(hasItem(DEFAULT_KODE_PRODUK.toString())))
            .andExpect(jsonPath("$.[*].hpTujuan").value(hasItem(DEFAULT_HP_TUJUAN.toString())))
            .andExpect(jsonPath("$.[*].hpMember").value(hasItem(DEFAULT_HP_MEMBER.toString())))
            .andExpect(jsonPath("$.[*].hargaBeli").value(hasItem(DEFAULT_HARGA_BELI.intValue())))
            .andExpect(jsonPath("$.[*].hpp").value(hasItem(DEFAULT_HPP.intValue())))
            .andExpect(jsonPath("$.[*].laba").value(hasItem(DEFAULT_LABA.intValue())))
            .andExpect(jsonPath("$.[*].com").value(hasItem(DEFAULT_COM.toString())))
            .andExpect(jsonPath("$.[*].admrpt").value(hasItem(DEFAULT_ADMRPT.intValue())))
            .andExpect(jsonPath("$.[*].ulang").value(hasItem(DEFAULT_ULANG)))
            .andExpect(jsonPath("$.[*].ulangTgl").value(hasItem(DEFAULT_ULANG_TGL.toString())))
            .andExpect(jsonPath("$.[*].fisik").value(hasItem(DEFAULT_FISIK)))
            .andExpect(jsonPath("$.[*].manual").value(hasItem(DEFAULT_MANUAL)))
            .andExpect(jsonPath("$.[*].switch1").value(hasItem(DEFAULT_SWITCH_1)))
            .andExpect(jsonPath("$.[*].kodeGagal").value(hasItem(DEFAULT_KODE_GAGAL)))
            .andExpect(jsonPath("$.[*].waitSms").value(hasItem(DEFAULT_WAIT_SMS)))
            .andExpect(jsonPath("$.[*].head2Head").value(hasItem(DEFAULT_HEAD_2_HEAD)))
            .andExpect(jsonPath("$.[*].hpPembeli").value(hasItem(DEFAULT_HP_PEMBELI.toString())))
            .andExpect(jsonPath("$.[*].beaAdmin").value(hasItem(DEFAULT_BEA_ADMIN.intValue())))
            .andExpect(jsonPath("$.[*].isReport").value(hasItem(DEFAULT_IS_REPORT)))
            .andExpect(jsonPath("$.[*].suplierKe").value(hasItem(DEFAULT_SUPLIER_KE)))
            .andExpect(jsonPath("$.[*].idDistributor").value(hasItem(DEFAULT_ID_DISTRIBUTOR.intValue())))
            .andExpect(jsonPath("$.[*].sn").value(hasItem(DEFAULT_SN.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].pesankirim").value(hasItem(DEFAULT_PESANKIRIM.toString())))
            .andExpect(jsonPath("$.[*].metode").value(hasItem(DEFAULT_METODE)))
            .andExpect(jsonPath("$.[*].toDistributor").value(hasItem(DEFAULT_TO_DISTRIBUTOR.toString())))
            .andExpect(jsonPath("$.[*].idPortip").value(hasItem(DEFAULT_ID_PORTIP)))
            .andExpect(jsonPath("$.[*].timeupdate").value(hasItem(sameInstant(DEFAULT_TIMEUPDATE))))
            .andExpect(jsonPath("$.[*].idDistributorOld").value(hasItem(DEFAULT_ID_DISTRIBUTOR_OLD.intValue())))
            .andExpect(jsonPath("$.[*].idDistributorProduk").value(hasItem(DEFAULT_ID_DISTRIBUTOR_PRODUK.intValue())))
            .andExpect(jsonPath("$.[*].saldoSup").value(hasItem(DEFAULT_SALDO_SUP.intValue())))
            .andExpect(jsonPath("$.[*].isrebate").value(hasItem(DEFAULT_ISREBATE)))
            .andExpect(jsonPath("$.[*].enginename").value(hasItem(DEFAULT_ENGINENAME.toString())))
            .andExpect(jsonPath("$.[*].typemsg").value(hasItem(DEFAULT_TYPEMSG)))
            .andExpect(jsonPath("$.[*].isro").value(hasItem(DEFAULT_ISRO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransaksiPulsa.class);
        TransaksiPulsa transaksiPulsa1 = new TransaksiPulsa();
        transaksiPulsa1.setId(1L);
        TransaksiPulsa transaksiPulsa2 = new TransaksiPulsa();
        transaksiPulsa2.setId(transaksiPulsa1.getId());
        assertThat(transaksiPulsa1).isEqualTo(transaksiPulsa2);
        transaksiPulsa2.setId(2L);
        assertThat(transaksiPulsa1).isNotEqualTo(transaksiPulsa2);
        transaksiPulsa1.setId(null);
        assertThat(transaksiPulsa1).isNotEqualTo(transaksiPulsa2);
    }
}
