package com.ib.service.impl;

import com.ib.service.KoneksiService;
import com.ib.domain.Koneksi;
import com.ib.repository.KoneksiRepository;
import com.ib.repository.search.KoneksiSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Koneksi.
 */
@Service
@Transactional
public class KoneksiServiceImpl implements KoneksiService{

    private final Logger log = LoggerFactory.getLogger(KoneksiServiceImpl.class);

    private final KoneksiRepository koneksiRepository;

    private final KoneksiSearchRepository koneksiSearchRepository;
    public KoneksiServiceImpl(KoneksiRepository koneksiRepository, KoneksiSearchRepository koneksiSearchRepository) {
        this.koneksiRepository = koneksiRepository;
        this.koneksiSearchRepository = koneksiSearchRepository;
    }

    /**
     * Save a koneksi.
     *
     * @param koneksi the entity to save
     * @return the persisted entity
     */
    @Override
    public Koneksi save(Koneksi koneksi) {
        log.debug("Request to save Koneksi : {}", koneksi);
        Koneksi result = koneksiRepository.save(koneksi);
        koneksiSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the koneksis.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Koneksi> findAll(Pageable pageable) {
        log.debug("Request to get all Koneksis");
        return koneksiRepository.findAll(pageable);
    }

    /**
     *  Get one koneksi by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Koneksi findOne(Long id) {
        log.debug("Request to get Koneksi : {}", id);
        return koneksiRepository.findOne(id);
    }

    /**
     *  Delete the  koneksi by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Koneksi : {}", id);
        koneksiRepository.delete(id);
        koneksiSearchRepository.delete(id);
    }

    /**
     * Search for the koneksi corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Koneksi> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Koneksis for query {}", query);
        Page<Koneksi> result = koneksiSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
