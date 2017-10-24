package com.ib.service;

import com.ib.domain.SmsHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SmsHistory.
 */
public interface SmsHistoryService {

    /**
     * Save a smsHistory.
     *
     * @param smsHistory the entity to save
     * @return the persisted entity
     */
    SmsHistory save(SmsHistory smsHistory);

    /**
     *  Get all the smsHistories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SmsHistory> findAll(Pageable pageable);

    /**
     *  Get the "id" smsHistory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SmsHistory findOne(Long id);

    /**
     *  Delete the "id" smsHistory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the smsHistory corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SmsHistory> search(String query, Pageable pageable);
}
