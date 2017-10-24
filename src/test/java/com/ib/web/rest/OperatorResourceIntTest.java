package com.ib.web.rest;

import com.ib.AyoappApp;

import com.ib.domain.Operator;
import com.ib.repository.OperatorRepository;
import com.ib.service.OperatorService;
import com.ib.repository.search.OperatorSearchRepository;
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
 * Test class for the OperatorResource REST controller.
 *
 * @see OperatorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AyoappApp.class)
public class OperatorResourceIntTest {

    private static final Integer DEFAULT_ID_OPERATOR = 1;
    private static final Integer UPDATED_ID_OPERATOR = 2;

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private OperatorSearchRepository operatorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOperatorMockMvc;

    private Operator operator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperatorResource operatorResource = new OperatorResource(operatorService);
        this.restOperatorMockMvc = MockMvcBuilders.standaloneSetup(operatorResource)
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
    public static Operator createEntity(EntityManager em) {
        Operator operator = new Operator()
            .idOperator(DEFAULT_ID_OPERATOR)
            .nama(DEFAULT_NAMA);
        return operator;
    }

    @Before
    public void initTest() {
        operatorSearchRepository.deleteAll();
        operator = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperator() throws Exception {
        int databaseSizeBeforeCreate = operatorRepository.findAll().size();

        // Create the Operator
        restOperatorMockMvc.perform(post("/api/operators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operator)))
            .andExpect(status().isCreated());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeCreate + 1);
        Operator testOperator = operatorList.get(operatorList.size() - 1);
        assertThat(testOperator.getIdOperator()).isEqualTo(DEFAULT_ID_OPERATOR);
        assertThat(testOperator.getNama()).isEqualTo(DEFAULT_NAMA);

        // Validate the Operator in Elasticsearch
        Operator operatorEs = operatorSearchRepository.findOne(testOperator.getId());
        assertThat(operatorEs).isEqualToComparingFieldByField(testOperator);
    }

    @Test
    @Transactional
    public void createOperatorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operatorRepository.findAll().size();

        // Create the Operator with an existing ID
        operator.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperatorMockMvc.perform(post("/api/operators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operator)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOperators() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        // Get all the operatorList
        restOperatorMockMvc.perform(get("/api/operators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operator.getId().intValue())))
            .andExpect(jsonPath("$.[*].idOperator").value(hasItem(DEFAULT_ID_OPERATOR)))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())));
    }

    @Test
    @Transactional
    public void getOperator() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        // Get the operator
        restOperatorMockMvc.perform(get("/api/operators/{id}", operator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operator.getId().intValue()))
            .andExpect(jsonPath("$.idOperator").value(DEFAULT_ID_OPERATOR))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOperator() throws Exception {
        // Get the operator
        restOperatorMockMvc.perform(get("/api/operators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperator() throws Exception {
        // Initialize the database
        operatorService.save(operator);

        int databaseSizeBeforeUpdate = operatorRepository.findAll().size();

        // Update the operator
        Operator updatedOperator = operatorRepository.findOne(operator.getId());
        updatedOperator
            .idOperator(UPDATED_ID_OPERATOR)
            .nama(UPDATED_NAMA);

        restOperatorMockMvc.perform(put("/api/operators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOperator)))
            .andExpect(status().isOk());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeUpdate);
        Operator testOperator = operatorList.get(operatorList.size() - 1);
        assertThat(testOperator.getIdOperator()).isEqualTo(UPDATED_ID_OPERATOR);
        assertThat(testOperator.getNama()).isEqualTo(UPDATED_NAMA);

        // Validate the Operator in Elasticsearch
        Operator operatorEs = operatorSearchRepository.findOne(testOperator.getId());
        assertThat(operatorEs).isEqualToComparingFieldByField(testOperator);
    }

    @Test
    @Transactional
    public void updateNonExistingOperator() throws Exception {
        int databaseSizeBeforeUpdate = operatorRepository.findAll().size();

        // Create the Operator

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOperatorMockMvc.perform(put("/api/operators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operator)))
            .andExpect(status().isCreated());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOperator() throws Exception {
        // Initialize the database
        operatorService.save(operator);

        int databaseSizeBeforeDelete = operatorRepository.findAll().size();

        // Get the operator
        restOperatorMockMvc.perform(delete("/api/operators/{id}", operator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean operatorExistsInEs = operatorSearchRepository.exists(operator.getId());
        assertThat(operatorExistsInEs).isFalse();

        // Validate the database is empty
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOperator() throws Exception {
        // Initialize the database
        operatorService.save(operator);

        // Search the operator
        restOperatorMockMvc.perform(get("/api/_search/operators?query=id:" + operator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operator.getId().intValue())))
            .andExpect(jsonPath("$.[*].idOperator").value(hasItem(DEFAULT_ID_OPERATOR)))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operator.class);
        Operator operator1 = new Operator();
        operator1.setId(1L);
        Operator operator2 = new Operator();
        operator2.setId(operator1.getId());
        assertThat(operator1).isEqualTo(operator2);
        operator2.setId(2L);
        assertThat(operator1).isNotEqualTo(operator2);
        operator1.setId(null);
        assertThat(operator1).isNotEqualTo(operator2);
    }
}
