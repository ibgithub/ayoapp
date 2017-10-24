package com.ib.web.rest;

import com.ib.AyoappApp;

import com.ib.domain.Produk;
import com.ib.repository.ProdukRepository;
import com.ib.service.ProdukService;
import com.ib.repository.search.ProdukSearchRepository;
import com.ib.web.rest.errors.ExceptionTranslator;
import com.ib.service.dto.ProdukCriteria;
import com.ib.service.ProdukQueryService;

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
 * Test class for the ProdukResource REST controller.
 *
 * @see ProdukResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AyoappApp.class)
public class ProdukResourceIntTest {

    private static final Integer DEFAULT_ID_PRODUK = 1;
    private static final Integer UPDATED_ID_PRODUK = 2;

    private static final String DEFAULT_KODE_PRODUK = "AAAAAAAAAA";
    private static final String UPDATED_KODE_PRODUK = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_KARTU = 1;
    private static final Integer UPDATED_ID_KARTU = 2;

    private static final BigDecimal DEFAULT_DENOM = new BigDecimal(1);
    private static final BigDecimal UPDATED_DENOM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_HPP = new BigDecimal(1);
    private static final BigDecimal UPDATED_HPP = new BigDecimal(2);

    private static final BigDecimal DEFAULT_HARGA_MAN = new BigDecimal(1);
    private static final BigDecimal UPDATED_HARGA_MAN = new BigDecimal(2);

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Integer DEFAULT_GANGGUAN = 1;
    private static final Integer UPDATED_GANGGUAN = 2;

    private static final Long DEFAULT_ID_DISTRIBUTOR = 1L;
    private static final Long UPDATED_ID_DISTRIBUTOR = 2L;

    private static final Integer DEFAULT_FISIK = 1;
    private static final Integer UPDATED_FISIK = 2;

    private static final ZonedDateTime DEFAULT_TGL_UPDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TGL_UPDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_USER_UPDATE = "AAAAAAAAAA";
    private static final String UPDATED_USER_UPDATE = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_DISTRIBUTOR_2 = 1L;
    private static final Long UPDATED_ID_DISTRIBUTOR_2 = 2L;

    private static final BigDecimal DEFAULT_KONVERSI_SALDO = new BigDecimal(1);
    private static final BigDecimal UPDATED_KONVERSI_SALDO = new BigDecimal(2);

    private static final Integer DEFAULT_ISREPORT = 1;
    private static final Integer UPDATED_ISREPORT = 2;

    private static final Integer DEFAULT_ISSPLIT = 1;
    private static final Integer UPDATED_ISSPLIT = 2;

    private static final String DEFAULT_OTOTIMEOPEN = "AAAAAAAAAA";
    private static final String UPDATED_OTOTIMEOPEN = "BBBBBBBBBB";

    private static final String DEFAULT_OTOTIMECLOSE = "AAAAAAAAAA";
    private static final String UPDATED_OTOTIMECLOSE = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_DISTRIBUTOR_3 = 1L;
    private static final Long UPDATED_ID_DISTRIBUTOR_3 = 2L;

    private static final Integer DEFAULT_ISSTOK = 1;
    private static final Integer UPDATED_ISSTOK = 2;

    private static final Integer DEFAULT_OTOCLOSESTATUS = 1;
    private static final Integer UPDATED_OTOCLOSESTATUS = 2;

    private static final BigDecimal DEFAULT_SALDO_MIN = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALDO_MIN = new BigDecimal(2);

    private static final Integer DEFAULT_AKSES = 1;
    private static final Integer UPDATED_AKSES = 2;

    private static final Integer DEFAULT_HLR = 1;
    private static final Integer UPDATED_HLR = 2;

    private static final Integer DEFAULT_ISULANG = 1;
    private static final Integer UPDATED_ISULANG = 2;

    private static final Integer DEFAULT_ISURUT = 1;
    private static final Integer UPDATED_ISURUT = 2;

    private static final Integer DEFAULT_FORMATPPOB = 1;
    private static final Integer UPDATED_FORMATPPOB = 2;

    private static final Integer DEFAULT_JENISPPOB = 1;
    private static final Integer UPDATED_JENISPPOB = 2;

    private static final String DEFAULT_KETPRODUK = "AAAAAAAAAA";
    private static final String UPDATED_KETPRODUK = "BBBBBBBBBB";

    @Autowired
    private ProdukRepository produkRepository;

    @Autowired
    private ProdukService produkService;

    @Autowired
    private ProdukSearchRepository produkSearchRepository;

    @Autowired
    private ProdukQueryService produkQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProdukMockMvc;

    private Produk produk;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProdukResource produkResource = new ProdukResource(produkService, produkQueryService);
        this.restProdukMockMvc = MockMvcBuilders.standaloneSetup(produkResource)
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
    public static Produk createEntity(EntityManager em) {
        Produk produk = new Produk()
            .idProduk(DEFAULT_ID_PRODUK)
            .kodeProduk(DEFAULT_KODE_PRODUK)
            .idKartu(DEFAULT_ID_KARTU)
            .denom(DEFAULT_DENOM)
            .hpp(DEFAULT_HPP)
            .hargaMan(DEFAULT_HARGA_MAN)
            .status(DEFAULT_STATUS)
            .gangguan(DEFAULT_GANGGUAN)
            .idDistributor(DEFAULT_ID_DISTRIBUTOR)
            .fisik(DEFAULT_FISIK)
            .tglUpdate(DEFAULT_TGL_UPDATE)
            .userUpdate(DEFAULT_USER_UPDATE)
            .idDistributor2(DEFAULT_ID_DISTRIBUTOR_2)
            .konversiSaldo(DEFAULT_KONVERSI_SALDO)
            .isreport(DEFAULT_ISREPORT)
            .issplit(DEFAULT_ISSPLIT)
            .ototimeopen(DEFAULT_OTOTIMEOPEN)
            .ototimeclose(DEFAULT_OTOTIMECLOSE)
            .idDistributor3(DEFAULT_ID_DISTRIBUTOR_3)
            .isstok(DEFAULT_ISSTOK)
            .otoclosestatus(DEFAULT_OTOCLOSESTATUS)
            .saldoMin(DEFAULT_SALDO_MIN)
            .akses(DEFAULT_AKSES)
            .hlr(DEFAULT_HLR)
            .isulang(DEFAULT_ISULANG)
            .isurut(DEFAULT_ISURUT)
            .formatppob(DEFAULT_FORMATPPOB)
            .jenisppob(DEFAULT_JENISPPOB)
            .ketproduk(DEFAULT_KETPRODUK);
        return produk;
    }

    @Before
    public void initTest() {
        produkSearchRepository.deleteAll();
        produk = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduk() throws Exception {
        int databaseSizeBeforeCreate = produkRepository.findAll().size();

        // Create the Produk
        restProdukMockMvc.perform(post("/api/produks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produk)))
            .andExpect(status().isCreated());

        // Validate the Produk in the database
        List<Produk> produkList = produkRepository.findAll();
        assertThat(produkList).hasSize(databaseSizeBeforeCreate + 1);
        Produk testProduk = produkList.get(produkList.size() - 1);
        assertThat(testProduk.getIdProduk()).isEqualTo(DEFAULT_ID_PRODUK);
        assertThat(testProduk.getKodeProduk()).isEqualTo(DEFAULT_KODE_PRODUK);
        assertThat(testProduk.getIdKartu()).isEqualTo(DEFAULT_ID_KARTU);
        assertThat(testProduk.getDenom()).isEqualTo(DEFAULT_DENOM);
        assertThat(testProduk.getHpp()).isEqualTo(DEFAULT_HPP);
        assertThat(testProduk.getHargaMan()).isEqualTo(DEFAULT_HARGA_MAN);
        assertThat(testProduk.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProduk.getGangguan()).isEqualTo(DEFAULT_GANGGUAN);
        assertThat(testProduk.getIdDistributor()).isEqualTo(DEFAULT_ID_DISTRIBUTOR);
        assertThat(testProduk.getFisik()).isEqualTo(DEFAULT_FISIK);
        assertThat(testProduk.getTglUpdate()).isEqualTo(DEFAULT_TGL_UPDATE);
        assertThat(testProduk.getUserUpdate()).isEqualTo(DEFAULT_USER_UPDATE);
        assertThat(testProduk.getIdDistributor2()).isEqualTo(DEFAULT_ID_DISTRIBUTOR_2);
        assertThat(testProduk.getKonversiSaldo()).isEqualTo(DEFAULT_KONVERSI_SALDO);
        assertThat(testProduk.getIsreport()).isEqualTo(DEFAULT_ISREPORT);
        assertThat(testProduk.getIssplit()).isEqualTo(DEFAULT_ISSPLIT);
        assertThat(testProduk.getOtotimeopen()).isEqualTo(DEFAULT_OTOTIMEOPEN);
        assertThat(testProduk.getOtotimeclose()).isEqualTo(DEFAULT_OTOTIMECLOSE);
        assertThat(testProduk.getIdDistributor3()).isEqualTo(DEFAULT_ID_DISTRIBUTOR_3);
        assertThat(testProduk.getIsstok()).isEqualTo(DEFAULT_ISSTOK);
        assertThat(testProduk.getOtoclosestatus()).isEqualTo(DEFAULT_OTOCLOSESTATUS);
        assertThat(testProduk.getSaldoMin()).isEqualTo(DEFAULT_SALDO_MIN);
        assertThat(testProduk.getAkses()).isEqualTo(DEFAULT_AKSES);
        assertThat(testProduk.getHlr()).isEqualTo(DEFAULT_HLR);
        assertThat(testProduk.getIsulang()).isEqualTo(DEFAULT_ISULANG);
        assertThat(testProduk.getIsurut()).isEqualTo(DEFAULT_ISURUT);
        assertThat(testProduk.getFormatppob()).isEqualTo(DEFAULT_FORMATPPOB);
        assertThat(testProduk.getJenisppob()).isEqualTo(DEFAULT_JENISPPOB);
        assertThat(testProduk.getKetproduk()).isEqualTo(DEFAULT_KETPRODUK);

        // Validate the Produk in Elasticsearch
        Produk produkEs = produkSearchRepository.findOne(testProduk.getId());
        assertThat(produkEs).isEqualToComparingFieldByField(testProduk);
    }

    @Test
    @Transactional
    public void createProdukWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produkRepository.findAll().size();

        // Create the Produk with an existing ID
        produk.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdukMockMvc.perform(post("/api/produks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produk)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Produk> produkList = produkRepository.findAll();
        assertThat(produkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProduks() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList
        restProdukMockMvc.perform(get("/api/produks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produk.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProduk").value(hasItem(DEFAULT_ID_PRODUK)))
            .andExpect(jsonPath("$.[*].kodeProduk").value(hasItem(DEFAULT_KODE_PRODUK.toString())))
            .andExpect(jsonPath("$.[*].idKartu").value(hasItem(DEFAULT_ID_KARTU)))
            .andExpect(jsonPath("$.[*].denom").value(hasItem(DEFAULT_DENOM.intValue())))
            .andExpect(jsonPath("$.[*].hpp").value(hasItem(DEFAULT_HPP.intValue())))
            .andExpect(jsonPath("$.[*].hargaMan").value(hasItem(DEFAULT_HARGA_MAN.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].gangguan").value(hasItem(DEFAULT_GANGGUAN)))
            .andExpect(jsonPath("$.[*].idDistributor").value(hasItem(DEFAULT_ID_DISTRIBUTOR.intValue())))
            .andExpect(jsonPath("$.[*].fisik").value(hasItem(DEFAULT_FISIK)))
            .andExpect(jsonPath("$.[*].tglUpdate").value(hasItem(sameInstant(DEFAULT_TGL_UPDATE))))
            .andExpect(jsonPath("$.[*].userUpdate").value(hasItem(DEFAULT_USER_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].idDistributor2").value(hasItem(DEFAULT_ID_DISTRIBUTOR_2.intValue())))
            .andExpect(jsonPath("$.[*].konversiSaldo").value(hasItem(DEFAULT_KONVERSI_SALDO.intValue())))
            .andExpect(jsonPath("$.[*].isreport").value(hasItem(DEFAULT_ISREPORT)))
            .andExpect(jsonPath("$.[*].issplit").value(hasItem(DEFAULT_ISSPLIT)))
            .andExpect(jsonPath("$.[*].ototimeopen").value(hasItem(DEFAULT_OTOTIMEOPEN.toString())))
            .andExpect(jsonPath("$.[*].ototimeclose").value(hasItem(DEFAULT_OTOTIMECLOSE.toString())))
            .andExpect(jsonPath("$.[*].idDistributor3").value(hasItem(DEFAULT_ID_DISTRIBUTOR_3.intValue())))
            .andExpect(jsonPath("$.[*].isstok").value(hasItem(DEFAULT_ISSTOK)))
            .andExpect(jsonPath("$.[*].otoclosestatus").value(hasItem(DEFAULT_OTOCLOSESTATUS)))
            .andExpect(jsonPath("$.[*].saldoMin").value(hasItem(DEFAULT_SALDO_MIN.intValue())))
            .andExpect(jsonPath("$.[*].akses").value(hasItem(DEFAULT_AKSES)))
            .andExpect(jsonPath("$.[*].hlr").value(hasItem(DEFAULT_HLR)))
            .andExpect(jsonPath("$.[*].isulang").value(hasItem(DEFAULT_ISULANG)))
            .andExpect(jsonPath("$.[*].isurut").value(hasItem(DEFAULT_ISURUT)))
            .andExpect(jsonPath("$.[*].formatppob").value(hasItem(DEFAULT_FORMATPPOB)))
            .andExpect(jsonPath("$.[*].jenisppob").value(hasItem(DEFAULT_JENISPPOB)))
            .andExpect(jsonPath("$.[*].ketproduk").value(hasItem(DEFAULT_KETPRODUK.toString())));
    }

    @Test
    @Transactional
    public void getProduk() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get the produk
        restProdukMockMvc.perform(get("/api/produks/{id}", produk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produk.getId().intValue()))
            .andExpect(jsonPath("$.idProduk").value(DEFAULT_ID_PRODUK))
            .andExpect(jsonPath("$.kodeProduk").value(DEFAULT_KODE_PRODUK.toString()))
            .andExpect(jsonPath("$.idKartu").value(DEFAULT_ID_KARTU))
            .andExpect(jsonPath("$.denom").value(DEFAULT_DENOM.intValue()))
            .andExpect(jsonPath("$.hpp").value(DEFAULT_HPP.intValue()))
            .andExpect(jsonPath("$.hargaMan").value(DEFAULT_HARGA_MAN.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.gangguan").value(DEFAULT_GANGGUAN))
            .andExpect(jsonPath("$.idDistributor").value(DEFAULT_ID_DISTRIBUTOR.intValue()))
            .andExpect(jsonPath("$.fisik").value(DEFAULT_FISIK))
            .andExpect(jsonPath("$.tglUpdate").value(sameInstant(DEFAULT_TGL_UPDATE)))
            .andExpect(jsonPath("$.userUpdate").value(DEFAULT_USER_UPDATE.toString()))
            .andExpect(jsonPath("$.idDistributor2").value(DEFAULT_ID_DISTRIBUTOR_2.intValue()))
            .andExpect(jsonPath("$.konversiSaldo").value(DEFAULT_KONVERSI_SALDO.intValue()))
            .andExpect(jsonPath("$.isreport").value(DEFAULT_ISREPORT))
            .andExpect(jsonPath("$.issplit").value(DEFAULT_ISSPLIT))
            .andExpect(jsonPath("$.ototimeopen").value(DEFAULT_OTOTIMEOPEN.toString()))
            .andExpect(jsonPath("$.ototimeclose").value(DEFAULT_OTOTIMECLOSE.toString()))
            .andExpect(jsonPath("$.idDistributor3").value(DEFAULT_ID_DISTRIBUTOR_3.intValue()))
            .andExpect(jsonPath("$.isstok").value(DEFAULT_ISSTOK))
            .andExpect(jsonPath("$.otoclosestatus").value(DEFAULT_OTOCLOSESTATUS))
            .andExpect(jsonPath("$.saldoMin").value(DEFAULT_SALDO_MIN.intValue()))
            .andExpect(jsonPath("$.akses").value(DEFAULT_AKSES))
            .andExpect(jsonPath("$.hlr").value(DEFAULT_HLR))
            .andExpect(jsonPath("$.isulang").value(DEFAULT_ISULANG))
            .andExpect(jsonPath("$.isurut").value(DEFAULT_ISURUT))
            .andExpect(jsonPath("$.formatppob").value(DEFAULT_FORMATPPOB))
            .andExpect(jsonPath("$.jenisppob").value(DEFAULT_JENISPPOB))
            .andExpect(jsonPath("$.ketproduk").value(DEFAULT_KETPRODUK.toString()));
    }

    @Test
    @Transactional
    public void getAllProduksByIdProdukIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idProduk equals to DEFAULT_ID_PRODUK
        defaultProdukShouldBeFound("idProduk.equals=" + DEFAULT_ID_PRODUK);

        // Get all the produkList where idProduk equals to UPDATED_ID_PRODUK
        defaultProdukShouldNotBeFound("idProduk.equals=" + UPDATED_ID_PRODUK);
    }

    @Test
    @Transactional
    public void getAllProduksByIdProdukIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idProduk in DEFAULT_ID_PRODUK or UPDATED_ID_PRODUK
        defaultProdukShouldBeFound("idProduk.in=" + DEFAULT_ID_PRODUK + "," + UPDATED_ID_PRODUK);

        // Get all the produkList where idProduk equals to UPDATED_ID_PRODUK
        defaultProdukShouldNotBeFound("idProduk.in=" + UPDATED_ID_PRODUK);
    }

    @Test
    @Transactional
    public void getAllProduksByIdProdukIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idProduk is not null
        defaultProdukShouldBeFound("idProduk.specified=true");

        // Get all the produkList where idProduk is null
        defaultProdukShouldNotBeFound("idProduk.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByIdProdukIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idProduk greater than or equals to DEFAULT_ID_PRODUK
        defaultProdukShouldBeFound("idProduk.greaterOrEqualThan=" + DEFAULT_ID_PRODUK);

        // Get all the produkList where idProduk greater than or equals to UPDATED_ID_PRODUK
        defaultProdukShouldNotBeFound("idProduk.greaterOrEqualThan=" + UPDATED_ID_PRODUK);
    }

    @Test
    @Transactional
    public void getAllProduksByIdProdukIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idProduk less than or equals to DEFAULT_ID_PRODUK
        defaultProdukShouldNotBeFound("idProduk.lessThan=" + DEFAULT_ID_PRODUK);

        // Get all the produkList where idProduk less than or equals to UPDATED_ID_PRODUK
        defaultProdukShouldBeFound("idProduk.lessThan=" + UPDATED_ID_PRODUK);
    }


    @Test
    @Transactional
    public void getAllProduksByKodeProdukIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where kodeProduk equals to DEFAULT_KODE_PRODUK
        defaultProdukShouldBeFound("kodeProduk.equals=" + DEFAULT_KODE_PRODUK);

        // Get all the produkList where kodeProduk equals to UPDATED_KODE_PRODUK
        defaultProdukShouldNotBeFound("kodeProduk.equals=" + UPDATED_KODE_PRODUK);
    }

    @Test
    @Transactional
    public void getAllProduksByKodeProdukIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where kodeProduk in DEFAULT_KODE_PRODUK or UPDATED_KODE_PRODUK
        defaultProdukShouldBeFound("kodeProduk.in=" + DEFAULT_KODE_PRODUK + "," + UPDATED_KODE_PRODUK);

        // Get all the produkList where kodeProduk equals to UPDATED_KODE_PRODUK
        defaultProdukShouldNotBeFound("kodeProduk.in=" + UPDATED_KODE_PRODUK);
    }

    @Test
    @Transactional
    public void getAllProduksByKodeProdukIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where kodeProduk is not null
        defaultProdukShouldBeFound("kodeProduk.specified=true");

        // Get all the produkList where kodeProduk is null
        defaultProdukShouldNotBeFound("kodeProduk.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByIdKartuIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idKartu equals to DEFAULT_ID_KARTU
        defaultProdukShouldBeFound("idKartu.equals=" + DEFAULT_ID_KARTU);

        // Get all the produkList where idKartu equals to UPDATED_ID_KARTU
        defaultProdukShouldNotBeFound("idKartu.equals=" + UPDATED_ID_KARTU);
    }

    @Test
    @Transactional
    public void getAllProduksByIdKartuIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idKartu in DEFAULT_ID_KARTU or UPDATED_ID_KARTU
        defaultProdukShouldBeFound("idKartu.in=" + DEFAULT_ID_KARTU + "," + UPDATED_ID_KARTU);

        // Get all the produkList where idKartu equals to UPDATED_ID_KARTU
        defaultProdukShouldNotBeFound("idKartu.in=" + UPDATED_ID_KARTU);
    }

    @Test
    @Transactional
    public void getAllProduksByIdKartuIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idKartu is not null
        defaultProdukShouldBeFound("idKartu.specified=true");

        // Get all the produkList where idKartu is null
        defaultProdukShouldNotBeFound("idKartu.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByIdKartuIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idKartu greater than or equals to DEFAULT_ID_KARTU
        defaultProdukShouldBeFound("idKartu.greaterOrEqualThan=" + DEFAULT_ID_KARTU);

        // Get all the produkList where idKartu greater than or equals to UPDATED_ID_KARTU
        defaultProdukShouldNotBeFound("idKartu.greaterOrEqualThan=" + UPDATED_ID_KARTU);
    }

    @Test
    @Transactional
    public void getAllProduksByIdKartuIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idKartu less than or equals to DEFAULT_ID_KARTU
        defaultProdukShouldNotBeFound("idKartu.lessThan=" + DEFAULT_ID_KARTU);

        // Get all the produkList where idKartu less than or equals to UPDATED_ID_KARTU
        defaultProdukShouldBeFound("idKartu.lessThan=" + UPDATED_ID_KARTU);
    }


    @Test
    @Transactional
    public void getAllProduksByDenomIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where denom equals to DEFAULT_DENOM
        defaultProdukShouldBeFound("denom.equals=" + DEFAULT_DENOM);

        // Get all the produkList where denom equals to UPDATED_DENOM
        defaultProdukShouldNotBeFound("denom.equals=" + UPDATED_DENOM);
    }

    @Test
    @Transactional
    public void getAllProduksByDenomIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where denom in DEFAULT_DENOM or UPDATED_DENOM
        defaultProdukShouldBeFound("denom.in=" + DEFAULT_DENOM + "," + UPDATED_DENOM);

        // Get all the produkList where denom equals to UPDATED_DENOM
        defaultProdukShouldNotBeFound("denom.in=" + UPDATED_DENOM);
    }

    @Test
    @Transactional
    public void getAllProduksByDenomIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where denom is not null
        defaultProdukShouldBeFound("denom.specified=true");

        // Get all the produkList where denom is null
        defaultProdukShouldNotBeFound("denom.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByHppIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where hpp equals to DEFAULT_HPP
        defaultProdukShouldBeFound("hpp.equals=" + DEFAULT_HPP);

        // Get all the produkList where hpp equals to UPDATED_HPP
        defaultProdukShouldNotBeFound("hpp.equals=" + UPDATED_HPP);
    }

    @Test
    @Transactional
    public void getAllProduksByHppIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where hpp in DEFAULT_HPP or UPDATED_HPP
        defaultProdukShouldBeFound("hpp.in=" + DEFAULT_HPP + "," + UPDATED_HPP);

        // Get all the produkList where hpp equals to UPDATED_HPP
        defaultProdukShouldNotBeFound("hpp.in=" + UPDATED_HPP);
    }

    @Test
    @Transactional
    public void getAllProduksByHppIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where hpp is not null
        defaultProdukShouldBeFound("hpp.specified=true");

        // Get all the produkList where hpp is null
        defaultProdukShouldNotBeFound("hpp.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByHargaManIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where hargaMan equals to DEFAULT_HARGA_MAN
        defaultProdukShouldBeFound("hargaMan.equals=" + DEFAULT_HARGA_MAN);

        // Get all the produkList where hargaMan equals to UPDATED_HARGA_MAN
        defaultProdukShouldNotBeFound("hargaMan.equals=" + UPDATED_HARGA_MAN);
    }

    @Test
    @Transactional
    public void getAllProduksByHargaManIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where hargaMan in DEFAULT_HARGA_MAN or UPDATED_HARGA_MAN
        defaultProdukShouldBeFound("hargaMan.in=" + DEFAULT_HARGA_MAN + "," + UPDATED_HARGA_MAN);

        // Get all the produkList where hargaMan equals to UPDATED_HARGA_MAN
        defaultProdukShouldNotBeFound("hargaMan.in=" + UPDATED_HARGA_MAN);
    }

    @Test
    @Transactional
    public void getAllProduksByHargaManIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where hargaMan is not null
        defaultProdukShouldBeFound("hargaMan.specified=true");

        // Get all the produkList where hargaMan is null
        defaultProdukShouldNotBeFound("hargaMan.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where status equals to DEFAULT_STATUS
        defaultProdukShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the produkList where status equals to UPDATED_STATUS
        defaultProdukShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllProduksByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultProdukShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the produkList where status equals to UPDATED_STATUS
        defaultProdukShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllProduksByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where status is not null
        defaultProdukShouldBeFound("status.specified=true");

        // Get all the produkList where status is null
        defaultProdukShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where status greater than or equals to DEFAULT_STATUS
        defaultProdukShouldBeFound("status.greaterOrEqualThan=" + DEFAULT_STATUS);

        // Get all the produkList where status greater than or equals to UPDATED_STATUS
        defaultProdukShouldNotBeFound("status.greaterOrEqualThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllProduksByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where status less than or equals to DEFAULT_STATUS
        defaultProdukShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the produkList where status less than or equals to UPDATED_STATUS
        defaultProdukShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllProduksByGangguanIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where gangguan equals to DEFAULT_GANGGUAN
        defaultProdukShouldBeFound("gangguan.equals=" + DEFAULT_GANGGUAN);

        // Get all the produkList where gangguan equals to UPDATED_GANGGUAN
        defaultProdukShouldNotBeFound("gangguan.equals=" + UPDATED_GANGGUAN);
    }

    @Test
    @Transactional
    public void getAllProduksByGangguanIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where gangguan in DEFAULT_GANGGUAN or UPDATED_GANGGUAN
        defaultProdukShouldBeFound("gangguan.in=" + DEFAULT_GANGGUAN + "," + UPDATED_GANGGUAN);

        // Get all the produkList where gangguan equals to UPDATED_GANGGUAN
        defaultProdukShouldNotBeFound("gangguan.in=" + UPDATED_GANGGUAN);
    }

    @Test
    @Transactional
    public void getAllProduksByGangguanIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where gangguan is not null
        defaultProdukShouldBeFound("gangguan.specified=true");

        // Get all the produkList where gangguan is null
        defaultProdukShouldNotBeFound("gangguan.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByGangguanIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where gangguan greater than or equals to DEFAULT_GANGGUAN
        defaultProdukShouldBeFound("gangguan.greaterOrEqualThan=" + DEFAULT_GANGGUAN);

        // Get all the produkList where gangguan greater than or equals to UPDATED_GANGGUAN
        defaultProdukShouldNotBeFound("gangguan.greaterOrEqualThan=" + UPDATED_GANGGUAN);
    }

    @Test
    @Transactional
    public void getAllProduksByGangguanIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where gangguan less than or equals to DEFAULT_GANGGUAN
        defaultProdukShouldNotBeFound("gangguan.lessThan=" + DEFAULT_GANGGUAN);

        // Get all the produkList where gangguan less than or equals to UPDATED_GANGGUAN
        defaultProdukShouldBeFound("gangguan.lessThan=" + UPDATED_GANGGUAN);
    }


    @Test
    @Transactional
    public void getAllProduksByIdDistributorIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idDistributor equals to DEFAULT_ID_DISTRIBUTOR
        defaultProdukShouldBeFound("idDistributor.equals=" + DEFAULT_ID_DISTRIBUTOR);

        // Get all the produkList where idDistributor equals to UPDATED_ID_DISTRIBUTOR
        defaultProdukShouldNotBeFound("idDistributor.equals=" + UPDATED_ID_DISTRIBUTOR);
    }

    @Test
    @Transactional
    public void getAllProduksByIdDistributorIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idDistributor in DEFAULT_ID_DISTRIBUTOR or UPDATED_ID_DISTRIBUTOR
        defaultProdukShouldBeFound("idDistributor.in=" + DEFAULT_ID_DISTRIBUTOR + "," + UPDATED_ID_DISTRIBUTOR);

        // Get all the produkList where idDistributor equals to UPDATED_ID_DISTRIBUTOR
        defaultProdukShouldNotBeFound("idDistributor.in=" + UPDATED_ID_DISTRIBUTOR);
    }

    @Test
    @Transactional
    public void getAllProduksByIdDistributorIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idDistributor is not null
        defaultProdukShouldBeFound("idDistributor.specified=true");

        // Get all the produkList where idDistributor is null
        defaultProdukShouldNotBeFound("idDistributor.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByIdDistributorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idDistributor greater than or equals to DEFAULT_ID_DISTRIBUTOR
        defaultProdukShouldBeFound("idDistributor.greaterOrEqualThan=" + DEFAULT_ID_DISTRIBUTOR);

        // Get all the produkList where idDistributor greater than or equals to UPDATED_ID_DISTRIBUTOR
        defaultProdukShouldNotBeFound("idDistributor.greaterOrEqualThan=" + UPDATED_ID_DISTRIBUTOR);
    }

    @Test
    @Transactional
    public void getAllProduksByIdDistributorIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idDistributor less than or equals to DEFAULT_ID_DISTRIBUTOR
        defaultProdukShouldNotBeFound("idDistributor.lessThan=" + DEFAULT_ID_DISTRIBUTOR);

        // Get all the produkList where idDistributor less than or equals to UPDATED_ID_DISTRIBUTOR
        defaultProdukShouldBeFound("idDistributor.lessThan=" + UPDATED_ID_DISTRIBUTOR);
    }


    @Test
    @Transactional
    public void getAllProduksByFisikIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where fisik equals to DEFAULT_FISIK
        defaultProdukShouldBeFound("fisik.equals=" + DEFAULT_FISIK);

        // Get all the produkList where fisik equals to UPDATED_FISIK
        defaultProdukShouldNotBeFound("fisik.equals=" + UPDATED_FISIK);
    }

    @Test
    @Transactional
    public void getAllProduksByFisikIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where fisik in DEFAULT_FISIK or UPDATED_FISIK
        defaultProdukShouldBeFound("fisik.in=" + DEFAULT_FISIK + "," + UPDATED_FISIK);

        // Get all the produkList where fisik equals to UPDATED_FISIK
        defaultProdukShouldNotBeFound("fisik.in=" + UPDATED_FISIK);
    }

    @Test
    @Transactional
    public void getAllProduksByFisikIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where fisik is not null
        defaultProdukShouldBeFound("fisik.specified=true");

        // Get all the produkList where fisik is null
        defaultProdukShouldNotBeFound("fisik.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByFisikIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where fisik greater than or equals to DEFAULT_FISIK
        defaultProdukShouldBeFound("fisik.greaterOrEqualThan=" + DEFAULT_FISIK);

        // Get all the produkList where fisik greater than or equals to UPDATED_FISIK
        defaultProdukShouldNotBeFound("fisik.greaterOrEqualThan=" + UPDATED_FISIK);
    }

    @Test
    @Transactional
    public void getAllProduksByFisikIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where fisik less than or equals to DEFAULT_FISIK
        defaultProdukShouldNotBeFound("fisik.lessThan=" + DEFAULT_FISIK);

        // Get all the produkList where fisik less than or equals to UPDATED_FISIK
        defaultProdukShouldBeFound("fisik.lessThan=" + UPDATED_FISIK);
    }


    @Test
    @Transactional
    public void getAllProduksByTglUpdateIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where tglUpdate equals to DEFAULT_TGL_UPDATE
        defaultProdukShouldBeFound("tglUpdate.equals=" + DEFAULT_TGL_UPDATE);

        // Get all the produkList where tglUpdate equals to UPDATED_TGL_UPDATE
        defaultProdukShouldNotBeFound("tglUpdate.equals=" + UPDATED_TGL_UPDATE);
    }

    @Test
    @Transactional
    public void getAllProduksByTglUpdateIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where tglUpdate in DEFAULT_TGL_UPDATE or UPDATED_TGL_UPDATE
        defaultProdukShouldBeFound("tglUpdate.in=" + DEFAULT_TGL_UPDATE + "," + UPDATED_TGL_UPDATE);

        // Get all the produkList where tglUpdate equals to UPDATED_TGL_UPDATE
        defaultProdukShouldNotBeFound("tglUpdate.in=" + UPDATED_TGL_UPDATE);
    }

    @Test
    @Transactional
    public void getAllProduksByTglUpdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where tglUpdate is not null
        defaultProdukShouldBeFound("tglUpdate.specified=true");

        // Get all the produkList where tglUpdate is null
        defaultProdukShouldNotBeFound("tglUpdate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByTglUpdateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where tglUpdate greater than or equals to DEFAULT_TGL_UPDATE
        defaultProdukShouldBeFound("tglUpdate.greaterOrEqualThan=" + DEFAULT_TGL_UPDATE);

        // Get all the produkList where tglUpdate greater than or equals to UPDATED_TGL_UPDATE
        defaultProdukShouldNotBeFound("tglUpdate.greaterOrEqualThan=" + UPDATED_TGL_UPDATE);
    }

    @Test
    @Transactional
    public void getAllProduksByTglUpdateIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where tglUpdate less than or equals to DEFAULT_TGL_UPDATE
        defaultProdukShouldNotBeFound("tglUpdate.lessThan=" + DEFAULT_TGL_UPDATE);

        // Get all the produkList where tglUpdate less than or equals to UPDATED_TGL_UPDATE
        defaultProdukShouldBeFound("tglUpdate.lessThan=" + UPDATED_TGL_UPDATE);
    }


    @Test
    @Transactional
    public void getAllProduksByUserUpdateIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where userUpdate equals to DEFAULT_USER_UPDATE
        defaultProdukShouldBeFound("userUpdate.equals=" + DEFAULT_USER_UPDATE);

        // Get all the produkList where userUpdate equals to UPDATED_USER_UPDATE
        defaultProdukShouldNotBeFound("userUpdate.equals=" + UPDATED_USER_UPDATE);
    }

    @Test
    @Transactional
    public void getAllProduksByUserUpdateIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where userUpdate in DEFAULT_USER_UPDATE or UPDATED_USER_UPDATE
        defaultProdukShouldBeFound("userUpdate.in=" + DEFAULT_USER_UPDATE + "," + UPDATED_USER_UPDATE);

        // Get all the produkList where userUpdate equals to UPDATED_USER_UPDATE
        defaultProdukShouldNotBeFound("userUpdate.in=" + UPDATED_USER_UPDATE);
    }

    @Test
    @Transactional
    public void getAllProduksByUserUpdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where userUpdate is not null
        defaultProdukShouldBeFound("userUpdate.specified=true");

        // Get all the produkList where userUpdate is null
        defaultProdukShouldNotBeFound("userUpdate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByIdDistributor2IsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idDistributor2 equals to DEFAULT_ID_DISTRIBUTOR_2
        defaultProdukShouldBeFound("idDistributor2.equals=" + DEFAULT_ID_DISTRIBUTOR_2);

        // Get all the produkList where idDistributor2 equals to UPDATED_ID_DISTRIBUTOR_2
        defaultProdukShouldNotBeFound("idDistributor2.equals=" + UPDATED_ID_DISTRIBUTOR_2);
    }

    @Test
    @Transactional
    public void getAllProduksByIdDistributor2IsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idDistributor2 in DEFAULT_ID_DISTRIBUTOR_2 or UPDATED_ID_DISTRIBUTOR_2
        defaultProdukShouldBeFound("idDistributor2.in=" + DEFAULT_ID_DISTRIBUTOR_2 + "," + UPDATED_ID_DISTRIBUTOR_2);

        // Get all the produkList where idDistributor2 equals to UPDATED_ID_DISTRIBUTOR_2
        defaultProdukShouldNotBeFound("idDistributor2.in=" + UPDATED_ID_DISTRIBUTOR_2);
    }

    @Test
    @Transactional
    public void getAllProduksByIdDistributor2IsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idDistributor2 is not null
        defaultProdukShouldBeFound("idDistributor2.specified=true");

        // Get all the produkList where idDistributor2 is null
        defaultProdukShouldNotBeFound("idDistributor2.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByIdDistributor2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idDistributor2 greater than or equals to DEFAULT_ID_DISTRIBUTOR_2
        defaultProdukShouldBeFound("idDistributor2.greaterOrEqualThan=" + DEFAULT_ID_DISTRIBUTOR_2);

        // Get all the produkList where idDistributor2 greater than or equals to UPDATED_ID_DISTRIBUTOR_2
        defaultProdukShouldNotBeFound("idDistributor2.greaterOrEqualThan=" + UPDATED_ID_DISTRIBUTOR_2);
    }

    @Test
    @Transactional
    public void getAllProduksByIdDistributor2IsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idDistributor2 less than or equals to DEFAULT_ID_DISTRIBUTOR_2
        defaultProdukShouldNotBeFound("idDistributor2.lessThan=" + DEFAULT_ID_DISTRIBUTOR_2);

        // Get all the produkList where idDistributor2 less than or equals to UPDATED_ID_DISTRIBUTOR_2
        defaultProdukShouldBeFound("idDistributor2.lessThan=" + UPDATED_ID_DISTRIBUTOR_2);
    }


    @Test
    @Transactional
    public void getAllProduksByKonversiSaldoIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where konversiSaldo equals to DEFAULT_KONVERSI_SALDO
        defaultProdukShouldBeFound("konversiSaldo.equals=" + DEFAULT_KONVERSI_SALDO);

        // Get all the produkList where konversiSaldo equals to UPDATED_KONVERSI_SALDO
        defaultProdukShouldNotBeFound("konversiSaldo.equals=" + UPDATED_KONVERSI_SALDO);
    }

    @Test
    @Transactional
    public void getAllProduksByKonversiSaldoIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where konversiSaldo in DEFAULT_KONVERSI_SALDO or UPDATED_KONVERSI_SALDO
        defaultProdukShouldBeFound("konversiSaldo.in=" + DEFAULT_KONVERSI_SALDO + "," + UPDATED_KONVERSI_SALDO);

        // Get all the produkList where konversiSaldo equals to UPDATED_KONVERSI_SALDO
        defaultProdukShouldNotBeFound("konversiSaldo.in=" + UPDATED_KONVERSI_SALDO);
    }

    @Test
    @Transactional
    public void getAllProduksByKonversiSaldoIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where konversiSaldo is not null
        defaultProdukShouldBeFound("konversiSaldo.specified=true");

        // Get all the produkList where konversiSaldo is null
        defaultProdukShouldNotBeFound("konversiSaldo.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByIsreportIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isreport equals to DEFAULT_ISREPORT
        defaultProdukShouldBeFound("isreport.equals=" + DEFAULT_ISREPORT);

        // Get all the produkList where isreport equals to UPDATED_ISREPORT
        defaultProdukShouldNotBeFound("isreport.equals=" + UPDATED_ISREPORT);
    }

    @Test
    @Transactional
    public void getAllProduksByIsreportIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isreport in DEFAULT_ISREPORT or UPDATED_ISREPORT
        defaultProdukShouldBeFound("isreport.in=" + DEFAULT_ISREPORT + "," + UPDATED_ISREPORT);

        // Get all the produkList where isreport equals to UPDATED_ISREPORT
        defaultProdukShouldNotBeFound("isreport.in=" + UPDATED_ISREPORT);
    }

    @Test
    @Transactional
    public void getAllProduksByIsreportIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isreport is not null
        defaultProdukShouldBeFound("isreport.specified=true");

        // Get all the produkList where isreport is null
        defaultProdukShouldNotBeFound("isreport.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByIsreportIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isreport greater than or equals to DEFAULT_ISREPORT
        defaultProdukShouldBeFound("isreport.greaterOrEqualThan=" + DEFAULT_ISREPORT);

        // Get all the produkList where isreport greater than or equals to UPDATED_ISREPORT
        defaultProdukShouldNotBeFound("isreport.greaterOrEqualThan=" + UPDATED_ISREPORT);
    }

    @Test
    @Transactional
    public void getAllProduksByIsreportIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isreport less than or equals to DEFAULT_ISREPORT
        defaultProdukShouldNotBeFound("isreport.lessThan=" + DEFAULT_ISREPORT);

        // Get all the produkList where isreport less than or equals to UPDATED_ISREPORT
        defaultProdukShouldBeFound("isreport.lessThan=" + UPDATED_ISREPORT);
    }


    @Test
    @Transactional
    public void getAllProduksByIssplitIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where issplit equals to DEFAULT_ISSPLIT
        defaultProdukShouldBeFound("issplit.equals=" + DEFAULT_ISSPLIT);

        // Get all the produkList where issplit equals to UPDATED_ISSPLIT
        defaultProdukShouldNotBeFound("issplit.equals=" + UPDATED_ISSPLIT);
    }

    @Test
    @Transactional
    public void getAllProduksByIssplitIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where issplit in DEFAULT_ISSPLIT or UPDATED_ISSPLIT
        defaultProdukShouldBeFound("issplit.in=" + DEFAULT_ISSPLIT + "," + UPDATED_ISSPLIT);

        // Get all the produkList where issplit equals to UPDATED_ISSPLIT
        defaultProdukShouldNotBeFound("issplit.in=" + UPDATED_ISSPLIT);
    }

    @Test
    @Transactional
    public void getAllProduksByIssplitIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where issplit is not null
        defaultProdukShouldBeFound("issplit.specified=true");

        // Get all the produkList where issplit is null
        defaultProdukShouldNotBeFound("issplit.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByIssplitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where issplit greater than or equals to DEFAULT_ISSPLIT
        defaultProdukShouldBeFound("issplit.greaterOrEqualThan=" + DEFAULT_ISSPLIT);

        // Get all the produkList where issplit greater than or equals to UPDATED_ISSPLIT
        defaultProdukShouldNotBeFound("issplit.greaterOrEqualThan=" + UPDATED_ISSPLIT);
    }

    @Test
    @Transactional
    public void getAllProduksByIssplitIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where issplit less than or equals to DEFAULT_ISSPLIT
        defaultProdukShouldNotBeFound("issplit.lessThan=" + DEFAULT_ISSPLIT);

        // Get all the produkList where issplit less than or equals to UPDATED_ISSPLIT
        defaultProdukShouldBeFound("issplit.lessThan=" + UPDATED_ISSPLIT);
    }


    @Test
    @Transactional
    public void getAllProduksByOtotimeopenIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where ototimeopen equals to DEFAULT_OTOTIMEOPEN
        defaultProdukShouldBeFound("ototimeopen.equals=" + DEFAULT_OTOTIMEOPEN);

        // Get all the produkList where ototimeopen equals to UPDATED_OTOTIMEOPEN
        defaultProdukShouldNotBeFound("ototimeopen.equals=" + UPDATED_OTOTIMEOPEN);
    }

    @Test
    @Transactional
    public void getAllProduksByOtotimeopenIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where ototimeopen in DEFAULT_OTOTIMEOPEN or UPDATED_OTOTIMEOPEN
        defaultProdukShouldBeFound("ototimeopen.in=" + DEFAULT_OTOTIMEOPEN + "," + UPDATED_OTOTIMEOPEN);

        // Get all the produkList where ototimeopen equals to UPDATED_OTOTIMEOPEN
        defaultProdukShouldNotBeFound("ototimeopen.in=" + UPDATED_OTOTIMEOPEN);
    }

    @Test
    @Transactional
    public void getAllProduksByOtotimeopenIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where ototimeopen is not null
        defaultProdukShouldBeFound("ototimeopen.specified=true");

        // Get all the produkList where ototimeopen is null
        defaultProdukShouldNotBeFound("ototimeopen.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByOtotimecloseIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where ototimeclose equals to DEFAULT_OTOTIMECLOSE
        defaultProdukShouldBeFound("ototimeclose.equals=" + DEFAULT_OTOTIMECLOSE);

        // Get all the produkList where ototimeclose equals to UPDATED_OTOTIMECLOSE
        defaultProdukShouldNotBeFound("ototimeclose.equals=" + UPDATED_OTOTIMECLOSE);
    }

    @Test
    @Transactional
    public void getAllProduksByOtotimecloseIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where ototimeclose in DEFAULT_OTOTIMECLOSE or UPDATED_OTOTIMECLOSE
        defaultProdukShouldBeFound("ototimeclose.in=" + DEFAULT_OTOTIMECLOSE + "," + UPDATED_OTOTIMECLOSE);

        // Get all the produkList where ototimeclose equals to UPDATED_OTOTIMECLOSE
        defaultProdukShouldNotBeFound("ototimeclose.in=" + UPDATED_OTOTIMECLOSE);
    }

    @Test
    @Transactional
    public void getAllProduksByOtotimecloseIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where ototimeclose is not null
        defaultProdukShouldBeFound("ototimeclose.specified=true");

        // Get all the produkList where ototimeclose is null
        defaultProdukShouldNotBeFound("ototimeclose.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByIdDistributor3IsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idDistributor3 equals to DEFAULT_ID_DISTRIBUTOR_3
        defaultProdukShouldBeFound("idDistributor3.equals=" + DEFAULT_ID_DISTRIBUTOR_3);

        // Get all the produkList where idDistributor3 equals to UPDATED_ID_DISTRIBUTOR_3
        defaultProdukShouldNotBeFound("idDistributor3.equals=" + UPDATED_ID_DISTRIBUTOR_3);
    }

    @Test
    @Transactional
    public void getAllProduksByIdDistributor3IsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idDistributor3 in DEFAULT_ID_DISTRIBUTOR_3 or UPDATED_ID_DISTRIBUTOR_3
        defaultProdukShouldBeFound("idDistributor3.in=" + DEFAULT_ID_DISTRIBUTOR_3 + "," + UPDATED_ID_DISTRIBUTOR_3);

        // Get all the produkList where idDistributor3 equals to UPDATED_ID_DISTRIBUTOR_3
        defaultProdukShouldNotBeFound("idDistributor3.in=" + UPDATED_ID_DISTRIBUTOR_3);
    }

    @Test
    @Transactional
    public void getAllProduksByIdDistributor3IsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idDistributor3 is not null
        defaultProdukShouldBeFound("idDistributor3.specified=true");

        // Get all the produkList where idDistributor3 is null
        defaultProdukShouldNotBeFound("idDistributor3.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByIdDistributor3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idDistributor3 greater than or equals to DEFAULT_ID_DISTRIBUTOR_3
        defaultProdukShouldBeFound("idDistributor3.greaterOrEqualThan=" + DEFAULT_ID_DISTRIBUTOR_3);

        // Get all the produkList where idDistributor3 greater than or equals to UPDATED_ID_DISTRIBUTOR_3
        defaultProdukShouldNotBeFound("idDistributor3.greaterOrEqualThan=" + UPDATED_ID_DISTRIBUTOR_3);
    }

    @Test
    @Transactional
    public void getAllProduksByIdDistributor3IsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where idDistributor3 less than or equals to DEFAULT_ID_DISTRIBUTOR_3
        defaultProdukShouldNotBeFound("idDistributor3.lessThan=" + DEFAULT_ID_DISTRIBUTOR_3);

        // Get all the produkList where idDistributor3 less than or equals to UPDATED_ID_DISTRIBUTOR_3
        defaultProdukShouldBeFound("idDistributor3.lessThan=" + UPDATED_ID_DISTRIBUTOR_3);
    }


    @Test
    @Transactional
    public void getAllProduksByIsstokIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isstok equals to DEFAULT_ISSTOK
        defaultProdukShouldBeFound("isstok.equals=" + DEFAULT_ISSTOK);

        // Get all the produkList where isstok equals to UPDATED_ISSTOK
        defaultProdukShouldNotBeFound("isstok.equals=" + UPDATED_ISSTOK);
    }

    @Test
    @Transactional
    public void getAllProduksByIsstokIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isstok in DEFAULT_ISSTOK or UPDATED_ISSTOK
        defaultProdukShouldBeFound("isstok.in=" + DEFAULT_ISSTOK + "," + UPDATED_ISSTOK);

        // Get all the produkList where isstok equals to UPDATED_ISSTOK
        defaultProdukShouldNotBeFound("isstok.in=" + UPDATED_ISSTOK);
    }

    @Test
    @Transactional
    public void getAllProduksByIsstokIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isstok is not null
        defaultProdukShouldBeFound("isstok.specified=true");

        // Get all the produkList where isstok is null
        defaultProdukShouldNotBeFound("isstok.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByIsstokIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isstok greater than or equals to DEFAULT_ISSTOK
        defaultProdukShouldBeFound("isstok.greaterOrEqualThan=" + DEFAULT_ISSTOK);

        // Get all the produkList where isstok greater than or equals to UPDATED_ISSTOK
        defaultProdukShouldNotBeFound("isstok.greaterOrEqualThan=" + UPDATED_ISSTOK);
    }

    @Test
    @Transactional
    public void getAllProduksByIsstokIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isstok less than or equals to DEFAULT_ISSTOK
        defaultProdukShouldNotBeFound("isstok.lessThan=" + DEFAULT_ISSTOK);

        // Get all the produkList where isstok less than or equals to UPDATED_ISSTOK
        defaultProdukShouldBeFound("isstok.lessThan=" + UPDATED_ISSTOK);
    }


    @Test
    @Transactional
    public void getAllProduksByOtoclosestatusIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where otoclosestatus equals to DEFAULT_OTOCLOSESTATUS
        defaultProdukShouldBeFound("otoclosestatus.equals=" + DEFAULT_OTOCLOSESTATUS);

        // Get all the produkList where otoclosestatus equals to UPDATED_OTOCLOSESTATUS
        defaultProdukShouldNotBeFound("otoclosestatus.equals=" + UPDATED_OTOCLOSESTATUS);
    }

    @Test
    @Transactional
    public void getAllProduksByOtoclosestatusIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where otoclosestatus in DEFAULT_OTOCLOSESTATUS or UPDATED_OTOCLOSESTATUS
        defaultProdukShouldBeFound("otoclosestatus.in=" + DEFAULT_OTOCLOSESTATUS + "," + UPDATED_OTOCLOSESTATUS);

        // Get all the produkList where otoclosestatus equals to UPDATED_OTOCLOSESTATUS
        defaultProdukShouldNotBeFound("otoclosestatus.in=" + UPDATED_OTOCLOSESTATUS);
    }

    @Test
    @Transactional
    public void getAllProduksByOtoclosestatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where otoclosestatus is not null
        defaultProdukShouldBeFound("otoclosestatus.specified=true");

        // Get all the produkList where otoclosestatus is null
        defaultProdukShouldNotBeFound("otoclosestatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByOtoclosestatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where otoclosestatus greater than or equals to DEFAULT_OTOCLOSESTATUS
        defaultProdukShouldBeFound("otoclosestatus.greaterOrEqualThan=" + DEFAULT_OTOCLOSESTATUS);

        // Get all the produkList where otoclosestatus greater than or equals to UPDATED_OTOCLOSESTATUS
        defaultProdukShouldNotBeFound("otoclosestatus.greaterOrEqualThan=" + UPDATED_OTOCLOSESTATUS);
    }

    @Test
    @Transactional
    public void getAllProduksByOtoclosestatusIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where otoclosestatus less than or equals to DEFAULT_OTOCLOSESTATUS
        defaultProdukShouldNotBeFound("otoclosestatus.lessThan=" + DEFAULT_OTOCLOSESTATUS);

        // Get all the produkList where otoclosestatus less than or equals to UPDATED_OTOCLOSESTATUS
        defaultProdukShouldBeFound("otoclosestatus.lessThan=" + UPDATED_OTOCLOSESTATUS);
    }


    @Test
    @Transactional
    public void getAllProduksBySaldoMinIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where saldoMin equals to DEFAULT_SALDO_MIN
        defaultProdukShouldBeFound("saldoMin.equals=" + DEFAULT_SALDO_MIN);

        // Get all the produkList where saldoMin equals to UPDATED_SALDO_MIN
        defaultProdukShouldNotBeFound("saldoMin.equals=" + UPDATED_SALDO_MIN);
    }

    @Test
    @Transactional
    public void getAllProduksBySaldoMinIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where saldoMin in DEFAULT_SALDO_MIN or UPDATED_SALDO_MIN
        defaultProdukShouldBeFound("saldoMin.in=" + DEFAULT_SALDO_MIN + "," + UPDATED_SALDO_MIN);

        // Get all the produkList where saldoMin equals to UPDATED_SALDO_MIN
        defaultProdukShouldNotBeFound("saldoMin.in=" + UPDATED_SALDO_MIN);
    }

    @Test
    @Transactional
    public void getAllProduksBySaldoMinIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where saldoMin is not null
        defaultProdukShouldBeFound("saldoMin.specified=true");

        // Get all the produkList where saldoMin is null
        defaultProdukShouldNotBeFound("saldoMin.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByAksesIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where akses equals to DEFAULT_AKSES
        defaultProdukShouldBeFound("akses.equals=" + DEFAULT_AKSES);

        // Get all the produkList where akses equals to UPDATED_AKSES
        defaultProdukShouldNotBeFound("akses.equals=" + UPDATED_AKSES);
    }

    @Test
    @Transactional
    public void getAllProduksByAksesIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where akses in DEFAULT_AKSES or UPDATED_AKSES
        defaultProdukShouldBeFound("akses.in=" + DEFAULT_AKSES + "," + UPDATED_AKSES);

        // Get all the produkList where akses equals to UPDATED_AKSES
        defaultProdukShouldNotBeFound("akses.in=" + UPDATED_AKSES);
    }

    @Test
    @Transactional
    public void getAllProduksByAksesIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where akses is not null
        defaultProdukShouldBeFound("akses.specified=true");

        // Get all the produkList where akses is null
        defaultProdukShouldNotBeFound("akses.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByAksesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where akses greater than or equals to DEFAULT_AKSES
        defaultProdukShouldBeFound("akses.greaterOrEqualThan=" + DEFAULT_AKSES);

        // Get all the produkList where akses greater than or equals to UPDATED_AKSES
        defaultProdukShouldNotBeFound("akses.greaterOrEqualThan=" + UPDATED_AKSES);
    }

    @Test
    @Transactional
    public void getAllProduksByAksesIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where akses less than or equals to DEFAULT_AKSES
        defaultProdukShouldNotBeFound("akses.lessThan=" + DEFAULT_AKSES);

        // Get all the produkList where akses less than or equals to UPDATED_AKSES
        defaultProdukShouldBeFound("akses.lessThan=" + UPDATED_AKSES);
    }


    @Test
    @Transactional
    public void getAllProduksByHlrIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where hlr equals to DEFAULT_HLR
        defaultProdukShouldBeFound("hlr.equals=" + DEFAULT_HLR);

        // Get all the produkList where hlr equals to UPDATED_HLR
        defaultProdukShouldNotBeFound("hlr.equals=" + UPDATED_HLR);
    }

    @Test
    @Transactional
    public void getAllProduksByHlrIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where hlr in DEFAULT_HLR or UPDATED_HLR
        defaultProdukShouldBeFound("hlr.in=" + DEFAULT_HLR + "," + UPDATED_HLR);

        // Get all the produkList where hlr equals to UPDATED_HLR
        defaultProdukShouldNotBeFound("hlr.in=" + UPDATED_HLR);
    }

    @Test
    @Transactional
    public void getAllProduksByHlrIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where hlr is not null
        defaultProdukShouldBeFound("hlr.specified=true");

        // Get all the produkList where hlr is null
        defaultProdukShouldNotBeFound("hlr.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByHlrIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where hlr greater than or equals to DEFAULT_HLR
        defaultProdukShouldBeFound("hlr.greaterOrEqualThan=" + DEFAULT_HLR);

        // Get all the produkList where hlr greater than or equals to UPDATED_HLR
        defaultProdukShouldNotBeFound("hlr.greaterOrEqualThan=" + UPDATED_HLR);
    }

    @Test
    @Transactional
    public void getAllProduksByHlrIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where hlr less than or equals to DEFAULT_HLR
        defaultProdukShouldNotBeFound("hlr.lessThan=" + DEFAULT_HLR);

        // Get all the produkList where hlr less than or equals to UPDATED_HLR
        defaultProdukShouldBeFound("hlr.lessThan=" + UPDATED_HLR);
    }


    @Test
    @Transactional
    public void getAllProduksByIsulangIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isulang equals to DEFAULT_ISULANG
        defaultProdukShouldBeFound("isulang.equals=" + DEFAULT_ISULANG);

        // Get all the produkList where isulang equals to UPDATED_ISULANG
        defaultProdukShouldNotBeFound("isulang.equals=" + UPDATED_ISULANG);
    }

    @Test
    @Transactional
    public void getAllProduksByIsulangIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isulang in DEFAULT_ISULANG or UPDATED_ISULANG
        defaultProdukShouldBeFound("isulang.in=" + DEFAULT_ISULANG + "," + UPDATED_ISULANG);

        // Get all the produkList where isulang equals to UPDATED_ISULANG
        defaultProdukShouldNotBeFound("isulang.in=" + UPDATED_ISULANG);
    }

    @Test
    @Transactional
    public void getAllProduksByIsulangIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isulang is not null
        defaultProdukShouldBeFound("isulang.specified=true");

        // Get all the produkList where isulang is null
        defaultProdukShouldNotBeFound("isulang.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByIsulangIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isulang greater than or equals to DEFAULT_ISULANG
        defaultProdukShouldBeFound("isulang.greaterOrEqualThan=" + DEFAULT_ISULANG);

        // Get all the produkList where isulang greater than or equals to UPDATED_ISULANG
        defaultProdukShouldNotBeFound("isulang.greaterOrEqualThan=" + UPDATED_ISULANG);
    }

    @Test
    @Transactional
    public void getAllProduksByIsulangIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isulang less than or equals to DEFAULT_ISULANG
        defaultProdukShouldNotBeFound("isulang.lessThan=" + DEFAULT_ISULANG);

        // Get all the produkList where isulang less than or equals to UPDATED_ISULANG
        defaultProdukShouldBeFound("isulang.lessThan=" + UPDATED_ISULANG);
    }


    @Test
    @Transactional
    public void getAllProduksByIsurutIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isurut equals to DEFAULT_ISURUT
        defaultProdukShouldBeFound("isurut.equals=" + DEFAULT_ISURUT);

        // Get all the produkList where isurut equals to UPDATED_ISURUT
        defaultProdukShouldNotBeFound("isurut.equals=" + UPDATED_ISURUT);
    }

    @Test
    @Transactional
    public void getAllProduksByIsurutIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isurut in DEFAULT_ISURUT or UPDATED_ISURUT
        defaultProdukShouldBeFound("isurut.in=" + DEFAULT_ISURUT + "," + UPDATED_ISURUT);

        // Get all the produkList where isurut equals to UPDATED_ISURUT
        defaultProdukShouldNotBeFound("isurut.in=" + UPDATED_ISURUT);
    }

    @Test
    @Transactional
    public void getAllProduksByIsurutIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isurut is not null
        defaultProdukShouldBeFound("isurut.specified=true");

        // Get all the produkList where isurut is null
        defaultProdukShouldNotBeFound("isurut.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByIsurutIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isurut greater than or equals to DEFAULT_ISURUT
        defaultProdukShouldBeFound("isurut.greaterOrEqualThan=" + DEFAULT_ISURUT);

        // Get all the produkList where isurut greater than or equals to UPDATED_ISURUT
        defaultProdukShouldNotBeFound("isurut.greaterOrEqualThan=" + UPDATED_ISURUT);
    }

    @Test
    @Transactional
    public void getAllProduksByIsurutIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where isurut less than or equals to DEFAULT_ISURUT
        defaultProdukShouldNotBeFound("isurut.lessThan=" + DEFAULT_ISURUT);

        // Get all the produkList where isurut less than or equals to UPDATED_ISURUT
        defaultProdukShouldBeFound("isurut.lessThan=" + UPDATED_ISURUT);
    }


    @Test
    @Transactional
    public void getAllProduksByFormatppobIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where formatppob equals to DEFAULT_FORMATPPOB
        defaultProdukShouldBeFound("formatppob.equals=" + DEFAULT_FORMATPPOB);

        // Get all the produkList where formatppob equals to UPDATED_FORMATPPOB
        defaultProdukShouldNotBeFound("formatppob.equals=" + UPDATED_FORMATPPOB);
    }

    @Test
    @Transactional
    public void getAllProduksByFormatppobIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where formatppob in DEFAULT_FORMATPPOB or UPDATED_FORMATPPOB
        defaultProdukShouldBeFound("formatppob.in=" + DEFAULT_FORMATPPOB + "," + UPDATED_FORMATPPOB);

        // Get all the produkList where formatppob equals to UPDATED_FORMATPPOB
        defaultProdukShouldNotBeFound("formatppob.in=" + UPDATED_FORMATPPOB);
    }

    @Test
    @Transactional
    public void getAllProduksByFormatppobIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where formatppob is not null
        defaultProdukShouldBeFound("formatppob.specified=true");

        // Get all the produkList where formatppob is null
        defaultProdukShouldNotBeFound("formatppob.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByFormatppobIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where formatppob greater than or equals to DEFAULT_FORMATPPOB
        defaultProdukShouldBeFound("formatppob.greaterOrEqualThan=" + DEFAULT_FORMATPPOB);

        // Get all the produkList where formatppob greater than or equals to UPDATED_FORMATPPOB
        defaultProdukShouldNotBeFound("formatppob.greaterOrEqualThan=" + UPDATED_FORMATPPOB);
    }

    @Test
    @Transactional
    public void getAllProduksByFormatppobIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where formatppob less than or equals to DEFAULT_FORMATPPOB
        defaultProdukShouldNotBeFound("formatppob.lessThan=" + DEFAULT_FORMATPPOB);

        // Get all the produkList where formatppob less than or equals to UPDATED_FORMATPPOB
        defaultProdukShouldBeFound("formatppob.lessThan=" + UPDATED_FORMATPPOB);
    }


    @Test
    @Transactional
    public void getAllProduksByJenisppobIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where jenisppob equals to DEFAULT_JENISPPOB
        defaultProdukShouldBeFound("jenisppob.equals=" + DEFAULT_JENISPPOB);

        // Get all the produkList where jenisppob equals to UPDATED_JENISPPOB
        defaultProdukShouldNotBeFound("jenisppob.equals=" + UPDATED_JENISPPOB);
    }

    @Test
    @Transactional
    public void getAllProduksByJenisppobIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where jenisppob in DEFAULT_JENISPPOB or UPDATED_JENISPPOB
        defaultProdukShouldBeFound("jenisppob.in=" + DEFAULT_JENISPPOB + "," + UPDATED_JENISPPOB);

        // Get all the produkList where jenisppob equals to UPDATED_JENISPPOB
        defaultProdukShouldNotBeFound("jenisppob.in=" + UPDATED_JENISPPOB);
    }

    @Test
    @Transactional
    public void getAllProduksByJenisppobIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where jenisppob is not null
        defaultProdukShouldBeFound("jenisppob.specified=true");

        // Get all the produkList where jenisppob is null
        defaultProdukShouldNotBeFound("jenisppob.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduksByJenisppobIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where jenisppob greater than or equals to DEFAULT_JENISPPOB
        defaultProdukShouldBeFound("jenisppob.greaterOrEqualThan=" + DEFAULT_JENISPPOB);

        // Get all the produkList where jenisppob greater than or equals to UPDATED_JENISPPOB
        defaultProdukShouldNotBeFound("jenisppob.greaterOrEqualThan=" + UPDATED_JENISPPOB);
    }

    @Test
    @Transactional
    public void getAllProduksByJenisppobIsLessThanSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where jenisppob less than or equals to DEFAULT_JENISPPOB
        defaultProdukShouldNotBeFound("jenisppob.lessThan=" + DEFAULT_JENISPPOB);

        // Get all the produkList where jenisppob less than or equals to UPDATED_JENISPPOB
        defaultProdukShouldBeFound("jenisppob.lessThan=" + UPDATED_JENISPPOB);
    }


    @Test
    @Transactional
    public void getAllProduksByKetprodukIsEqualToSomething() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where ketproduk equals to DEFAULT_KETPRODUK
        defaultProdukShouldBeFound("ketproduk.equals=" + DEFAULT_KETPRODUK);

        // Get all the produkList where ketproduk equals to UPDATED_KETPRODUK
        defaultProdukShouldNotBeFound("ketproduk.equals=" + UPDATED_KETPRODUK);
    }

    @Test
    @Transactional
    public void getAllProduksByKetprodukIsInShouldWork() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where ketproduk in DEFAULT_KETPRODUK or UPDATED_KETPRODUK
        defaultProdukShouldBeFound("ketproduk.in=" + DEFAULT_KETPRODUK + "," + UPDATED_KETPRODUK);

        // Get all the produkList where ketproduk equals to UPDATED_KETPRODUK
        defaultProdukShouldNotBeFound("ketproduk.in=" + UPDATED_KETPRODUK);
    }

    @Test
    @Transactional
    public void getAllProduksByKetprodukIsNullOrNotNull() throws Exception {
        // Initialize the database
        produkRepository.saveAndFlush(produk);

        // Get all the produkList where ketproduk is not null
        defaultProdukShouldBeFound("ketproduk.specified=true");

        // Get all the produkList where ketproduk is null
        defaultProdukShouldNotBeFound("ketproduk.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProdukShouldBeFound(String filter) throws Exception {
        restProdukMockMvc.perform(get("/api/produks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produk.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProduk").value(hasItem(DEFAULT_ID_PRODUK)))
            .andExpect(jsonPath("$.[*].kodeProduk").value(hasItem(DEFAULT_KODE_PRODUK.toString())))
            .andExpect(jsonPath("$.[*].idKartu").value(hasItem(DEFAULT_ID_KARTU)))
            .andExpect(jsonPath("$.[*].denom").value(hasItem(DEFAULT_DENOM.intValue())))
            .andExpect(jsonPath("$.[*].hpp").value(hasItem(DEFAULT_HPP.intValue())))
            .andExpect(jsonPath("$.[*].hargaMan").value(hasItem(DEFAULT_HARGA_MAN.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].gangguan").value(hasItem(DEFAULT_GANGGUAN)))
            .andExpect(jsonPath("$.[*].idDistributor").value(hasItem(DEFAULT_ID_DISTRIBUTOR.intValue())))
            .andExpect(jsonPath("$.[*].fisik").value(hasItem(DEFAULT_FISIK)))
            .andExpect(jsonPath("$.[*].tglUpdate").value(hasItem(sameInstant(DEFAULT_TGL_UPDATE))))
            .andExpect(jsonPath("$.[*].userUpdate").value(hasItem(DEFAULT_USER_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].idDistributor2").value(hasItem(DEFAULT_ID_DISTRIBUTOR_2.intValue())))
            .andExpect(jsonPath("$.[*].konversiSaldo").value(hasItem(DEFAULT_KONVERSI_SALDO.intValue())))
            .andExpect(jsonPath("$.[*].isreport").value(hasItem(DEFAULT_ISREPORT)))
            .andExpect(jsonPath("$.[*].issplit").value(hasItem(DEFAULT_ISSPLIT)))
            .andExpect(jsonPath("$.[*].ototimeopen").value(hasItem(DEFAULT_OTOTIMEOPEN.toString())))
            .andExpect(jsonPath("$.[*].ototimeclose").value(hasItem(DEFAULT_OTOTIMECLOSE.toString())))
            .andExpect(jsonPath("$.[*].idDistributor3").value(hasItem(DEFAULT_ID_DISTRIBUTOR_3.intValue())))
            .andExpect(jsonPath("$.[*].isstok").value(hasItem(DEFAULT_ISSTOK)))
            .andExpect(jsonPath("$.[*].otoclosestatus").value(hasItem(DEFAULT_OTOCLOSESTATUS)))
            .andExpect(jsonPath("$.[*].saldoMin").value(hasItem(DEFAULT_SALDO_MIN.intValue())))
            .andExpect(jsonPath("$.[*].akses").value(hasItem(DEFAULT_AKSES)))
            .andExpect(jsonPath("$.[*].hlr").value(hasItem(DEFAULT_HLR)))
            .andExpect(jsonPath("$.[*].isulang").value(hasItem(DEFAULT_ISULANG)))
            .andExpect(jsonPath("$.[*].isurut").value(hasItem(DEFAULT_ISURUT)))
            .andExpect(jsonPath("$.[*].formatppob").value(hasItem(DEFAULT_FORMATPPOB)))
            .andExpect(jsonPath("$.[*].jenisppob").value(hasItem(DEFAULT_JENISPPOB)))
            .andExpect(jsonPath("$.[*].ketproduk").value(hasItem(DEFAULT_KETPRODUK.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProdukShouldNotBeFound(String filter) throws Exception {
        restProdukMockMvc.perform(get("/api/produks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingProduk() throws Exception {
        // Get the produk
        restProdukMockMvc.perform(get("/api/produks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduk() throws Exception {
        // Initialize the database
        produkService.save(produk);

        int databaseSizeBeforeUpdate = produkRepository.findAll().size();

        // Update the produk
        Produk updatedProduk = produkRepository.findOne(produk.getId());
        updatedProduk
            .idProduk(UPDATED_ID_PRODUK)
            .kodeProduk(UPDATED_KODE_PRODUK)
            .idKartu(UPDATED_ID_KARTU)
            .denom(UPDATED_DENOM)
            .hpp(UPDATED_HPP)
            .hargaMan(UPDATED_HARGA_MAN)
            .status(UPDATED_STATUS)
            .gangguan(UPDATED_GANGGUAN)
            .idDistributor(UPDATED_ID_DISTRIBUTOR)
            .fisik(UPDATED_FISIK)
            .tglUpdate(UPDATED_TGL_UPDATE)
            .userUpdate(UPDATED_USER_UPDATE)
            .idDistributor2(UPDATED_ID_DISTRIBUTOR_2)
            .konversiSaldo(UPDATED_KONVERSI_SALDO)
            .isreport(UPDATED_ISREPORT)
            .issplit(UPDATED_ISSPLIT)
            .ototimeopen(UPDATED_OTOTIMEOPEN)
            .ototimeclose(UPDATED_OTOTIMECLOSE)
            .idDistributor3(UPDATED_ID_DISTRIBUTOR_3)
            .isstok(UPDATED_ISSTOK)
            .otoclosestatus(UPDATED_OTOCLOSESTATUS)
            .saldoMin(UPDATED_SALDO_MIN)
            .akses(UPDATED_AKSES)
            .hlr(UPDATED_HLR)
            .isulang(UPDATED_ISULANG)
            .isurut(UPDATED_ISURUT)
            .formatppob(UPDATED_FORMATPPOB)
            .jenisppob(UPDATED_JENISPPOB)
            .ketproduk(UPDATED_KETPRODUK);

        restProdukMockMvc.perform(put("/api/produks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduk)))
            .andExpect(status().isOk());

        // Validate the Produk in the database
        List<Produk> produkList = produkRepository.findAll();
        assertThat(produkList).hasSize(databaseSizeBeforeUpdate);
        Produk testProduk = produkList.get(produkList.size() - 1);
        assertThat(testProduk.getIdProduk()).isEqualTo(UPDATED_ID_PRODUK);
        assertThat(testProduk.getKodeProduk()).isEqualTo(UPDATED_KODE_PRODUK);
        assertThat(testProduk.getIdKartu()).isEqualTo(UPDATED_ID_KARTU);
        assertThat(testProduk.getDenom()).isEqualTo(UPDATED_DENOM);
        assertThat(testProduk.getHpp()).isEqualTo(UPDATED_HPP);
        assertThat(testProduk.getHargaMan()).isEqualTo(UPDATED_HARGA_MAN);
        assertThat(testProduk.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProduk.getGangguan()).isEqualTo(UPDATED_GANGGUAN);
        assertThat(testProduk.getIdDistributor()).isEqualTo(UPDATED_ID_DISTRIBUTOR);
        assertThat(testProduk.getFisik()).isEqualTo(UPDATED_FISIK);
        assertThat(testProduk.getTglUpdate()).isEqualTo(UPDATED_TGL_UPDATE);
        assertThat(testProduk.getUserUpdate()).isEqualTo(UPDATED_USER_UPDATE);
        assertThat(testProduk.getIdDistributor2()).isEqualTo(UPDATED_ID_DISTRIBUTOR_2);
        assertThat(testProduk.getKonversiSaldo()).isEqualTo(UPDATED_KONVERSI_SALDO);
        assertThat(testProduk.getIsreport()).isEqualTo(UPDATED_ISREPORT);
        assertThat(testProduk.getIssplit()).isEqualTo(UPDATED_ISSPLIT);
        assertThat(testProduk.getOtotimeopen()).isEqualTo(UPDATED_OTOTIMEOPEN);
        assertThat(testProduk.getOtotimeclose()).isEqualTo(UPDATED_OTOTIMECLOSE);
        assertThat(testProduk.getIdDistributor3()).isEqualTo(UPDATED_ID_DISTRIBUTOR_3);
        assertThat(testProduk.getIsstok()).isEqualTo(UPDATED_ISSTOK);
        assertThat(testProduk.getOtoclosestatus()).isEqualTo(UPDATED_OTOCLOSESTATUS);
        assertThat(testProduk.getSaldoMin()).isEqualTo(UPDATED_SALDO_MIN);
        assertThat(testProduk.getAkses()).isEqualTo(UPDATED_AKSES);
        assertThat(testProduk.getHlr()).isEqualTo(UPDATED_HLR);
        assertThat(testProduk.getIsulang()).isEqualTo(UPDATED_ISULANG);
        assertThat(testProduk.getIsurut()).isEqualTo(UPDATED_ISURUT);
        assertThat(testProduk.getFormatppob()).isEqualTo(UPDATED_FORMATPPOB);
        assertThat(testProduk.getJenisppob()).isEqualTo(UPDATED_JENISPPOB);
        assertThat(testProduk.getKetproduk()).isEqualTo(UPDATED_KETPRODUK);

        // Validate the Produk in Elasticsearch
        Produk produkEs = produkSearchRepository.findOne(testProduk.getId());
        assertThat(produkEs).isEqualToComparingFieldByField(testProduk);
    }

    @Test
    @Transactional
    public void updateNonExistingProduk() throws Exception {
        int databaseSizeBeforeUpdate = produkRepository.findAll().size();

        // Create the Produk

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProdukMockMvc.perform(put("/api/produks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produk)))
            .andExpect(status().isCreated());

        // Validate the Produk in the database
        List<Produk> produkList = produkRepository.findAll();
        assertThat(produkList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProduk() throws Exception {
        // Initialize the database
        produkService.save(produk);

        int databaseSizeBeforeDelete = produkRepository.findAll().size();

        // Get the produk
        restProdukMockMvc.perform(delete("/api/produks/{id}", produk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean produkExistsInEs = produkSearchRepository.exists(produk.getId());
        assertThat(produkExistsInEs).isFalse();

        // Validate the database is empty
        List<Produk> produkList = produkRepository.findAll();
        assertThat(produkList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProduk() throws Exception {
        // Initialize the database
        produkService.save(produk);

        // Search the produk
        restProdukMockMvc.perform(get("/api/_search/produks?query=id:" + produk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produk.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProduk").value(hasItem(DEFAULT_ID_PRODUK)))
            .andExpect(jsonPath("$.[*].kodeProduk").value(hasItem(DEFAULT_KODE_PRODUK.toString())))
            .andExpect(jsonPath("$.[*].idKartu").value(hasItem(DEFAULT_ID_KARTU)))
            .andExpect(jsonPath("$.[*].denom").value(hasItem(DEFAULT_DENOM.intValue())))
            .andExpect(jsonPath("$.[*].hpp").value(hasItem(DEFAULT_HPP.intValue())))
            .andExpect(jsonPath("$.[*].hargaMan").value(hasItem(DEFAULT_HARGA_MAN.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].gangguan").value(hasItem(DEFAULT_GANGGUAN)))
            .andExpect(jsonPath("$.[*].idDistributor").value(hasItem(DEFAULT_ID_DISTRIBUTOR.intValue())))
            .andExpect(jsonPath("$.[*].fisik").value(hasItem(DEFAULT_FISIK)))
            .andExpect(jsonPath("$.[*].tglUpdate").value(hasItem(sameInstant(DEFAULT_TGL_UPDATE))))
            .andExpect(jsonPath("$.[*].userUpdate").value(hasItem(DEFAULT_USER_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].idDistributor2").value(hasItem(DEFAULT_ID_DISTRIBUTOR_2.intValue())))
            .andExpect(jsonPath("$.[*].konversiSaldo").value(hasItem(DEFAULT_KONVERSI_SALDO.intValue())))
            .andExpect(jsonPath("$.[*].isreport").value(hasItem(DEFAULT_ISREPORT)))
            .andExpect(jsonPath("$.[*].issplit").value(hasItem(DEFAULT_ISSPLIT)))
            .andExpect(jsonPath("$.[*].ototimeopen").value(hasItem(DEFAULT_OTOTIMEOPEN.toString())))
            .andExpect(jsonPath("$.[*].ototimeclose").value(hasItem(DEFAULT_OTOTIMECLOSE.toString())))
            .andExpect(jsonPath("$.[*].idDistributor3").value(hasItem(DEFAULT_ID_DISTRIBUTOR_3.intValue())))
            .andExpect(jsonPath("$.[*].isstok").value(hasItem(DEFAULT_ISSTOK)))
            .andExpect(jsonPath("$.[*].otoclosestatus").value(hasItem(DEFAULT_OTOCLOSESTATUS)))
            .andExpect(jsonPath("$.[*].saldoMin").value(hasItem(DEFAULT_SALDO_MIN.intValue())))
            .andExpect(jsonPath("$.[*].akses").value(hasItem(DEFAULT_AKSES)))
            .andExpect(jsonPath("$.[*].hlr").value(hasItem(DEFAULT_HLR)))
            .andExpect(jsonPath("$.[*].isulang").value(hasItem(DEFAULT_ISULANG)))
            .andExpect(jsonPath("$.[*].isurut").value(hasItem(DEFAULT_ISURUT)))
            .andExpect(jsonPath("$.[*].formatppob").value(hasItem(DEFAULT_FORMATPPOB)))
            .andExpect(jsonPath("$.[*].jenisppob").value(hasItem(DEFAULT_JENISPPOB)))
            .andExpect(jsonPath("$.[*].ketproduk").value(hasItem(DEFAULT_KETPRODUK.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Produk.class);
        Produk produk1 = new Produk();
        produk1.setId(1L);
        Produk produk2 = new Produk();
        produk2.setId(produk1.getId());
        assertThat(produk1).isEqualTo(produk2);
        produk2.setId(2L);
        assertThat(produk1).isNotEqualTo(produk2);
        produk1.setId(null);
        assertThat(produk1).isNotEqualTo(produk2);
    }
}
