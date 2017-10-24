package com.ib.service;

import com.ib.domain.Transaksi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Transaksi.
 */
public interface TransaksiService {

    /**
     * Save a transaksi.
     *
     * @param transaksi the entity to save
     * @return the persisted entity
     */
    Transaksi save(Transaksi transaksi);

    /**
     *  Get all the transaksis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Transaksi> findAll(Pageable pageable);

    /**
     *  Get the "id" transaksi.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Transaksi findOne(Long id);

    /**
     *  Delete the "id" transaksi.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the transaksi corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Transaksi> search(String query, Pageable pageable);
}
