package com.ib.service;

import com.ib.domain.LogSaldo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing LogSaldo.
 */
public interface LogSaldoService {

    /**
     * Save a logSaldo.
     *
     * @param logSaldo the entity to save
     * @return the persisted entity
     */
    LogSaldo save(LogSaldo logSaldo);

    /**
     *  Get all the logSaldos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LogSaldo> findAll(Pageable pageable);

    /**
     *  Get the "id" logSaldo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LogSaldo findOne(Long id);

    /**
     *  Delete the "id" logSaldo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the logSaldo corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LogSaldo> search(String query, Pageable pageable);
}
