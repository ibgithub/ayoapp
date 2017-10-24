package com.ib.service;

import com.ib.domain.Produk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Produk.
 */
public interface ProdukService {

    /**
     * Save a produk.
     *
     * @param produk the entity to save
     * @return the persisted entity
     */
    Produk save(Produk produk);

    /**
     *  Get all the produks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Produk> findAll(Pageable pageable);

    /**
     *  Get the "id" produk.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Produk findOne(Long id);

    /**
     *  Delete the "id" produk.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the produk corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Produk> search(String query, Pageable pageable);
}
