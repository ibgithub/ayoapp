package com.ib.service;

import com.ib.domain.Denom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Denom.
 */
public interface DenomService {

    /**
     * Save a denom.
     *
     * @param denom the entity to save
     * @return the persisted entity
     */
    Denom save(Denom denom);

    /**
     *  Get all the denoms.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Denom> findAll(Pageable pageable);

    /**
     *  Get the "id" denom.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Denom findOne(Long id);

    /**
     *  Delete the "id" denom.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the denom corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Denom> search(String query, Pageable pageable);
}
