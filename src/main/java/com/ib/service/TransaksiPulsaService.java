package com.ib.service;

import com.ib.domain.TransaksiPulsa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TransaksiPulsa.
 */
public interface TransaksiPulsaService {

    /**
     * Save a transaksiPulsa.
     *
     * @param transaksiPulsa the entity to save
     * @return the persisted entity
     */
    TransaksiPulsa save(TransaksiPulsa transaksiPulsa);

    /**
     *  Get all the transaksiPulsas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TransaksiPulsa> findAll(Pageable pageable);

    /**
     *  Get the "id" transaksiPulsa.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TransaksiPulsa findOne(Long id);

    /**
     *  Delete the "id" transaksiPulsa.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the transaksiPulsa corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TransaksiPulsa> search(String query, Pageable pageable);
}
