package com.ib.service.impl;

import com.ib.service.DenomService;
import com.ib.domain.Denom;
import com.ib.repository.DenomRepository;
import com.ib.repository.search.DenomSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Denom.
 */
@Service
@Transactional
public class DenomServiceImpl implements DenomService{

    private final Logger log = LoggerFactory.getLogger(DenomServiceImpl.class);

    private final DenomRepository denomRepository;

    private final DenomSearchRepository denomSearchRepository;
    public DenomServiceImpl(DenomRepository denomRepository, DenomSearchRepository denomSearchRepository) {
        this.denomRepository = denomRepository;
        this.denomSearchRepository = denomSearchRepository;
    }

    /**
     * Save a denom.
     *
     * @param denom the entity to save
     * @return the persisted entity
     */
    @Override
    public Denom save(Denom denom) {
        log.debug("Request to save Denom : {}", denom);
        Denom result = denomRepository.save(denom);
        denomSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the denoms.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Denom> findAll(Pageable pageable) {
        log.debug("Request to get all Denoms");
        return denomRepository.findAll(pageable);
    }

    /**
     *  Get one denom by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Denom findOne(Long id) {
        log.debug("Request to get Denom : {}", id);
        return denomRepository.findOne(id);
    }

    /**
     *  Delete the  denom by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Denom : {}", id);
        denomRepository.delete(id);
        denomSearchRepository.delete(id);
    }

    /**
     * Search for the denom corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Denom> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Denoms for query {}", query);
        Page<Denom> result = denomSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
