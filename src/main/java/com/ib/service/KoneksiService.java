package com.ib.service;

import com.ib.domain.Koneksi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Koneksi.
 */
public interface KoneksiService {

    /**
     * Save a koneksi.
     *
     * @param koneksi the entity to save
     * @return the persisted entity
     */
    Koneksi save(Koneksi koneksi);

    /**
     *  Get all the koneksis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Koneksi> findAll(Pageable pageable);

    /**
     *  Get the "id" koneksi.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Koneksi findOne(Long id);

    /**
     *  Delete the "id" koneksi.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the koneksi corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Koneksi> search(String query, Pageable pageable);
}
