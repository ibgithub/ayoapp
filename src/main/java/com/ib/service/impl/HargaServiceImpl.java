package com.ib.service.impl;

import com.ib.service.HargaService;
import com.ib.domain.Harga;
import com.ib.repository.HargaRepository;
import com.ib.repository.search.HargaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Harga.
 */
@Service
@Transactional
public class HargaServiceImpl implements HargaService{

    private final Logger log = LoggerFactory.getLogger(HargaServiceImpl.class);

    private final HargaRepository hargaRepository;

    private final HargaSearchRepository hargaSearchRepository;
    public HargaServiceImpl(HargaRepository hargaRepository, HargaSearchRepository hargaSearchRepository) {
        this.hargaRepository = hargaRepository;
        this.hargaSearchRepository = hargaSearchRepository;
    }

    /**
     * Save a harga.
     *
     * @param harga the entity to save
     * @return the persisted entity
     */
    @Override
    public Harga save(Harga harga) {
        log.debug("Request to save Harga : {}", harga);
        Harga result = hargaRepository.save(harga);
        hargaSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the hargas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Harga> findAll(Pageable pageable) {
        log.debug("Request to get all Hargas");
        return hargaRepository.findAll(pageable);
    }

    /**
     *  Get one harga by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Harga findOne(Long id) {
        log.debug("Request to get Harga : {}", id);
        return hargaRepository.findOne(id);
    }

    /**
     *  Delete the  harga by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Harga : {}", id);
        hargaRepository.delete(id);
        hargaSearchRepository.delete(id);
    }

    /**
     * Search for the harga corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Harga> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Hargas for query {}", query);
        Page<Harga> result = hargaSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
