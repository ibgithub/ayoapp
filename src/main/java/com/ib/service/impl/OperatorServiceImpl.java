package com.ib.service.impl;

import com.ib.service.OperatorService;
import com.ib.domain.Operator;
import com.ib.repository.OperatorRepository;
import com.ib.repository.search.OperatorSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Operator.
 */
@Service
@Transactional
public class OperatorServiceImpl implements OperatorService{

    private final Logger log = LoggerFactory.getLogger(OperatorServiceImpl.class);

    private final OperatorRepository operatorRepository;

    private final OperatorSearchRepository operatorSearchRepository;
    public OperatorServiceImpl(OperatorRepository operatorRepository, OperatorSearchRepository operatorSearchRepository) {
        this.operatorRepository = operatorRepository;
        this.operatorSearchRepository = operatorSearchRepository;
    }

    /**
     * Save a operator.
     *
     * @param operator the entity to save
     * @return the persisted entity
     */
    @Override
    public Operator save(Operator operator) {
        log.debug("Request to save Operator : {}", operator);
        Operator result = operatorRepository.save(operator);
        operatorSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the operators.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Operator> findAll(Pageable pageable) {
        log.debug("Request to get all Operators");
        return operatorRepository.findAll(pageable);
    }

    /**
     *  Get one operator by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Operator findOne(Long id) {
        log.debug("Request to get Operator : {}", id);
        return operatorRepository.findOne(id);
    }

    /**
     *  Delete the  operator by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Operator : {}", id);
        operatorRepository.delete(id);
        operatorSearchRepository.delete(id);
    }

    /**
     * Search for the operator corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Operator> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Operators for query {}", query);
        Page<Operator> result = operatorSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
