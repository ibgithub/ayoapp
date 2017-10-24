package com.ib.service;

import com.ib.domain.Rebate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Rebate.
 */
public interface RebateService {

    /**
     * Save a rebate.
     *
     * @param rebate the entity to save
     * @return the persisted entity
     */
    Rebate save(Rebate rebate);

    /**
     *  Get all the rebates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Rebate> findAll(Pageable pageable);

    /**
     *  Get the "id" rebate.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Rebate findOne(Long id);

    /**
     *  Delete the "id" rebate.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the rebate corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Rebate> search(String query, Pageable pageable);
}
