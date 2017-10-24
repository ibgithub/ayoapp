package com.ib.service;

import com.ib.domain.Operator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Operator.
 */
public interface OperatorService {

    /**
     * Save a operator.
     *
     * @param operator the entity to save
     * @return the persisted entity
     */
    Operator save(Operator operator);

    /**
     *  Get all the operators.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Operator> findAll(Pageable pageable);

    /**
     *  Get the "id" operator.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Operator findOne(Long id);

    /**
     *  Delete the "id" operator.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the operator corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Operator> search(String query, Pageable pageable);
}
