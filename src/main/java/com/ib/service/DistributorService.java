package com.ib.service;

import com.ib.domain.Distributor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Distributor.
 */
public interface DistributorService {

    /**
     * Save a distributor.
     *
     * @param distributor the entity to save
     * @return the persisted entity
     */
    Distributor save(Distributor distributor);

    /**
     *  Get all the distributors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Distributor> findAll(Pageable pageable);

    /**
     *  Get the "id" distributor.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Distributor findOne(Long id);

    /**
     *  Delete the "id" distributor.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the distributor corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Distributor> search(String query, Pageable pageable);
}
