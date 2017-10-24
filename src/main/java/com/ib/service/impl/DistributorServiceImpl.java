package com.ib.service.impl;

import com.ib.service.DistributorService;
import com.ib.domain.Distributor;
import com.ib.repository.DistributorRepository;
import com.ib.repository.search.DistributorSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Distributor.
 */
@Service
@Transactional
public class DistributorServiceImpl implements DistributorService{

    private final Logger log = LoggerFactory.getLogger(DistributorServiceImpl.class);

    private final DistributorRepository distributorRepository;

    private final DistributorSearchRepository distributorSearchRepository;
    public DistributorServiceImpl(DistributorRepository distributorRepository, DistributorSearchRepository distributorSearchRepository) {
        this.distributorRepository = distributorRepository;
        this.distributorSearchRepository = distributorSearchRepository;
    }

    /**
     * Save a distributor.
     *
     * @param distributor the entity to save
     * @return the persisted entity
     */
    @Override
    public Distributor save(Distributor distributor) {
        log.debug("Request to save Distributor : {}", distributor);
        Distributor result = distributorRepository.save(distributor);
        distributorSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the distributors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Distributor> findAll(Pageable pageable) {
        log.debug("Request to get all Distributors");
        return distributorRepository.findAll(pageable);
    }

    /**
     *  Get one distributor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Distributor findOne(Long id) {
        log.debug("Request to get Distributor : {}", id);
        return distributorRepository.findOne(id);
    }

    /**
     *  Delete the  distributor by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Distributor : {}", id);
        distributorRepository.delete(id);
        distributorSearchRepository.delete(id);
    }

    /**
     * Search for the distributor corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Distributor> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Distributors for query {}", query);
        Page<Distributor> result = distributorSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
