package com.ib.service;

import com.ib.domain.Kartu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Kartu.
 */
public interface KartuService {

    /**
     * Save a kartu.
     *
     * @param kartu the entity to save
     * @return the persisted entity
     */
    Kartu save(Kartu kartu);

    /**
     *  Get all the kartus.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Kartu> findAll(Pageable pageable);

    /**
     *  Get the "id" kartu.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Kartu findOne(Long id);

    /**
     *  Delete the "id" kartu.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the kartu corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Kartu> search(String query, Pageable pageable);
}
