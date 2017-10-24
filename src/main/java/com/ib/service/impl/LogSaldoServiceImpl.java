package com.ib.service.impl;

import com.ib.service.LogSaldoService;
import com.ib.domain.LogSaldo;
import com.ib.repository.LogSaldoRepository;
import com.ib.repository.search.LogSaldoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing LogSaldo.
 */
@Service
@Transactional
public class LogSaldoServiceImpl implements LogSaldoService{

    private final Logger log = LoggerFactory.getLogger(LogSaldoServiceImpl.class);

    private final LogSaldoRepository logSaldoRepository;

    private final LogSaldoSearchRepository logSaldoSearchRepository;
    public LogSaldoServiceImpl(LogSaldoRepository logSaldoRepository, LogSaldoSearchRepository logSaldoSearchRepository) {
        this.logSaldoRepository = logSaldoRepository;
        this.logSaldoSearchRepository = logSaldoSearchRepository;
    }

    /**
     * Save a logSaldo.
     *
     * @param logSaldo the entity to save
     * @return the persisted entity
     */
    @Override
    public LogSaldo save(LogSaldo logSaldo) {
        log.debug("Request to save LogSaldo : {}", logSaldo);
        LogSaldo result = logSaldoRepository.save(logSaldo);
        logSaldoSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the logSaldos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LogSaldo> findAll(Pageable pageable) {
        log.debug("Request to get all LogSaldos");
        return logSaldoRepository.findAll(pageable);
    }

    /**
     *  Get one logSaldo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LogSaldo findOne(Long id) {
        log.debug("Request to get LogSaldo : {}", id);
        return logSaldoRepository.findOne(id);
    }

    /**
     *  Delete the  logSaldo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LogSaldo : {}", id);
        logSaldoRepository.delete(id);
        logSaldoSearchRepository.delete(id);
    }

    /**
     * Search for the logSaldo corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LogSaldo> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LogSaldos for query {}", query);
        Page<LogSaldo> result = logSaldoSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
