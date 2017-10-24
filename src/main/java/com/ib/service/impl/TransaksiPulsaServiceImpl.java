package com.ib.service.impl;

import com.ib.service.TransaksiPulsaService;
import com.ib.domain.TransaksiPulsa;
import com.ib.repository.TransaksiPulsaRepository;
import com.ib.repository.search.TransaksiPulsaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TransaksiPulsa.
 */
@Service
@Transactional
public class TransaksiPulsaServiceImpl implements TransaksiPulsaService{

    private final Logger log = LoggerFactory.getLogger(TransaksiPulsaServiceImpl.class);

    private final TransaksiPulsaRepository transaksiPulsaRepository;

    private final TransaksiPulsaSearchRepository transaksiPulsaSearchRepository;
    public TransaksiPulsaServiceImpl(TransaksiPulsaRepository transaksiPulsaRepository, TransaksiPulsaSearchRepository transaksiPulsaSearchRepository) {
        this.transaksiPulsaRepository = transaksiPulsaRepository;
        this.transaksiPulsaSearchRepository = transaksiPulsaSearchRepository;
    }

    /**
     * Save a transaksiPulsa.
     *
     * @param transaksiPulsa the entity to save
     * @return the persisted entity
     */
    @Override
    public TransaksiPulsa save(TransaksiPulsa transaksiPulsa) {
        log.debug("Request to save TransaksiPulsa : {}", transaksiPulsa);
        TransaksiPulsa result = transaksiPulsaRepository.save(transaksiPulsa);
        transaksiPulsaSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the transaksiPulsas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransaksiPulsa> findAll(Pageable pageable) {
        log.debug("Request to get all TransaksiPulsas");
        return transaksiPulsaRepository.findAll(pageable);
    }

    /**
     *  Get one transaksiPulsa by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TransaksiPulsa findOne(Long id) {
        log.debug("Request to get TransaksiPulsa : {}", id);
        return transaksiPulsaRepository.findOne(id);
    }

    /**
     *  Delete the  transaksiPulsa by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransaksiPulsa : {}", id);
        transaksiPulsaRepository.delete(id);
        transaksiPulsaSearchRepository.delete(id);
    }

    /**
     * Search for the transaksiPulsa corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransaksiPulsa> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TransaksiPulsas for query {}", query);
        Page<TransaksiPulsa> result = transaksiPulsaSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
