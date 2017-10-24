package com.ib.service.impl;

import com.ib.service.KartuService;
import com.ib.domain.Kartu;
import com.ib.repository.KartuRepository;
import com.ib.repository.search.KartuSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Kartu.
 */
@Service
@Transactional
public class KartuServiceImpl implements KartuService{

    private final Logger log = LoggerFactory.getLogger(KartuServiceImpl.class);

    private final KartuRepository kartuRepository;

    private final KartuSearchRepository kartuSearchRepository;
    public KartuServiceImpl(KartuRepository kartuRepository, KartuSearchRepository kartuSearchRepository) {
        this.kartuRepository = kartuRepository;
        this.kartuSearchRepository = kartuSearchRepository;
    }

    /**
     * Save a kartu.
     *
     * @param kartu the entity to save
     * @return the persisted entity
     */
    @Override
    public Kartu save(Kartu kartu) {
        log.debug("Request to save Kartu : {}", kartu);
        Kartu result = kartuRepository.save(kartu);
        kartuSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the kartus.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Kartu> findAll(Pageable pageable) {
        log.debug("Request to get all Kartus");
        return kartuRepository.findAll(pageable);
    }

    /**
     *  Get one kartu by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Kartu findOne(Long id) {
        log.debug("Request to get Kartu : {}", id);
        return kartuRepository.findOne(id);
    }

    /**
     *  Delete the  kartu by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Kartu : {}", id);
        kartuRepository.delete(id);
        kartuSearchRepository.delete(id);
    }

    /**
     * Search for the kartu corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Kartu> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Kartus for query {}", query);
        Page<Kartu> result = kartuSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
