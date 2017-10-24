package com.ib.service.impl;

import com.ib.service.RebateService;
import com.ib.domain.Rebate;
import com.ib.repository.RebateRepository;
import com.ib.repository.search.RebateSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Rebate.
 */
@Service
@Transactional
public class RebateServiceImpl implements RebateService{

    private final Logger log = LoggerFactory.getLogger(RebateServiceImpl.class);

    private final RebateRepository rebateRepository;

    private final RebateSearchRepository rebateSearchRepository;
    public RebateServiceImpl(RebateRepository rebateRepository, RebateSearchRepository rebateSearchRepository) {
        this.rebateRepository = rebateRepository;
        this.rebateSearchRepository = rebateSearchRepository;
    }

    /**
     * Save a rebate.
     *
     * @param rebate the entity to save
     * @return the persisted entity
     */
    @Override
    public Rebate save(Rebate rebate) {
        log.debug("Request to save Rebate : {}", rebate);
        Rebate result = rebateRepository.save(rebate);
        rebateSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the rebates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Rebate> findAll(Pageable pageable) {
        log.debug("Request to get all Rebates");
        return rebateRepository.findAll(pageable);
    }

    /**
     *  Get one rebate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Rebate findOne(Long id) {
        log.debug("Request to get Rebate : {}", id);
        return rebateRepository.findOne(id);
    }

    /**
     *  Delete the  rebate by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rebate : {}", id);
        rebateRepository.delete(id);
        rebateSearchRepository.delete(id);
    }

    /**
     * Search for the rebate corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Rebate> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Rebates for query {}", query);
        Page<Rebate> result = rebateSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
