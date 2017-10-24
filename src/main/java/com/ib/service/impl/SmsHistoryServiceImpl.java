package com.ib.service.impl;

import com.ib.service.SmsHistoryService;
import com.ib.domain.SmsHistory;
import com.ib.repository.SmsHistoryRepository;
import com.ib.repository.search.SmsHistorySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SmsHistory.
 */
@Service
@Transactional
public class SmsHistoryServiceImpl implements SmsHistoryService{

    private final Logger log = LoggerFactory.getLogger(SmsHistoryServiceImpl.class);

    private final SmsHistoryRepository smsHistoryRepository;

    private final SmsHistorySearchRepository smsHistorySearchRepository;
    public SmsHistoryServiceImpl(SmsHistoryRepository smsHistoryRepository, SmsHistorySearchRepository smsHistorySearchRepository) {
        this.smsHistoryRepository = smsHistoryRepository;
        this.smsHistorySearchRepository = smsHistorySearchRepository;
    }

    /**
     * Save a smsHistory.
     *
     * @param smsHistory the entity to save
     * @return the persisted entity
     */
    @Override
    public SmsHistory save(SmsHistory smsHistory) {
        log.debug("Request to save SmsHistory : {}", smsHistory);
        SmsHistory result = smsHistoryRepository.save(smsHistory);
        smsHistorySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the smsHistories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SmsHistory> findAll(Pageable pageable) {
        log.debug("Request to get all SmsHistories");
        return smsHistoryRepository.findAll(pageable);
    }

    /**
     *  Get one smsHistory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SmsHistory findOne(Long id) {
        log.debug("Request to get SmsHistory : {}", id);
        return smsHistoryRepository.findOne(id);
    }

    /**
     *  Delete the  smsHistory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SmsHistory : {}", id);
        smsHistoryRepository.delete(id);
        smsHistorySearchRepository.delete(id);
    }

    /**
     * Search for the smsHistory corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SmsHistory> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SmsHistories for query {}", query);
        Page<SmsHistory> result = smsHistorySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
