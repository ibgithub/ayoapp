package com.ib.service;

import com.ib.domain.Harga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Harga.
 */
public interface HargaService {

    /**
     * Save a harga.
     *
     * @param harga the entity to save
     * @return the persisted entity
     */
    Harga save(Harga harga);

    /**
     *  Get all the hargas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Harga> findAll(Pageable pageable);

    /**
     *  Get the "id" harga.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Harga findOne(Long id);

    /**
     *  Delete the "id" harga.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the harga corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Harga> search(String query, Pageable pageable);
}
