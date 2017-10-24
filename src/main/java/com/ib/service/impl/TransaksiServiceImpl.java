package com.ib.service.impl;

import com.ib.service.TransaksiService;
import com.ib.domain.Transaksi;
import com.ib.repository.TransaksiRepository;
import com.ib.repository.search.TransaksiSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Transaksi.
 */
@Service
@Transactional
public class TransaksiServiceImpl implements TransaksiService{

    private final Logger log = LoggerFactory.getLogger(TransaksiServiceImpl.class);

    private final TransaksiRepository transaksiRepository;

    private final TransaksiSearchRepository transaksiSearchRepository;
    public TransaksiServiceImpl(TransaksiRepository transaksiRepository, TransaksiSearchRepository transaksiSearchRepository) {
        this.transaksiRepository = transaksiRepository;
        this.transaksiSearchRepository = transaksiSearchRepository;
    }

    /**
     * Save a transaksi.
     *
     * @param transaksi the entity to save
     * @return the persisted entity
     */
    @Override
    public Transaksi save(Transaksi transaksi) {
        log.debug("Request to save Transaksi : {}", transaksi);
        Transaksi result = transaksiRepository.save(transaksi);
        transaksiSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the transaksis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Transaksi> findAll(Pageable pageable) {
        log.debug("Request to get all Transaksis");
        return transaksiRepository.findAll(pageable);
    }

    /**
     *  Get one transaksi by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Transaksi findOne(Long id) {
        log.debug("Request to get Transaksi : {}", id);
        return transaksiRepository.findOne(id);
    }

    /**
     *  Delete the  transaksi by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Transaksi : {}", id);
        transaksiRepository.delete(id);
        transaksiSearchRepository.delete(id);
    }

    /**
     * Search for the transaksi corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Transaksi> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Transaksis for query {}", query);
        Page<Transaksi> result = transaksiSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
