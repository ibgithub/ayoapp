package com.ib.service;


import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.ib.domain.SmsHistory;
import com.ib.domain.*; // for static metamodels
import com.ib.repository.SmsHistoryRepository;
import com.ib.repository.search.SmsHistorySearchRepository;
import com.ib.service.dto.SmsHistoryCriteria;


/**
 * Service for executing complex queries for SmsHistory entities in the database.
 * The main input is a {@link SmsHistoryCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link SmsHistory} or a {@link Page} of {%link SmsHistory} which fullfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class SmsHistoryQueryService extends QueryService<SmsHistory> {

    private final Logger log = LoggerFactory.getLogger(SmsHistoryQueryService.class);


    private final SmsHistoryRepository smsHistoryRepository;

    private final SmsHistorySearchRepository smsHistorySearchRepository;
    public SmsHistoryQueryService(SmsHistoryRepository smsHistoryRepository, SmsHistorySearchRepository smsHistorySearchRepository) {
        this.smsHistoryRepository = smsHistoryRepository;
        this.smsHistorySearchRepository = smsHistorySearchRepository;
    }

    /**
     * Return a {@link List} of {%link SmsHistory} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SmsHistory> findByCriteria(SmsHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<SmsHistory> specification = createSpecification(criteria);
        return smsHistoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link SmsHistory} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsHistory> findByCriteria(SmsHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<SmsHistory> specification = createSpecification(criteria);
        return smsHistoryRepository.findAll(specification, page);
    }

    /**
     * Function to convert SmsHistoryCriteria to a {@link Specifications}
     */
    private Specifications<SmsHistory> createSpecification(SmsHistoryCriteria criteria) {
        Specifications<SmsHistory> specification = Specifications.where(null);
        if (criteria != null) {
        	/*
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SmsHistory_.id));
            }
            if (criteria.getTglInput() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTglInput(), SmsHistory_.tglInput));
            }
            if (criteria.getNoHp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoHp(), SmsHistory_.noHp));
            }
            if (criteria.getNama() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNama(), SmsHistory_.nama));
            }
            if (criteria.getPesan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPesan(), SmsHistory_.pesan));
            }
            if (criteria.getTipe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTipe(), SmsHistory_.tipe));
            }
            if (criteria.getTglSms() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTglSms(), SmsHistory_.tglSms));
            }
            if (criteria.getCom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCom(), SmsHistory_.com));
            }
            if (criteria.getReport() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReport(), SmsHistory_.report));
            }
            if (criteria.getTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrx(), SmsHistory_.trx));
            }
            if (criteria.getPosting() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPosting(), SmsHistory_.posting));
            }
            if (criteria.getRef() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRef(), SmsHistory_.ref));
            }
            if (criteria.getMsisdn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMsisdn(), SmsHistory_.msisdn));
            }
            if (criteria.getEnginename() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEnginename(), SmsHistory_.enginename));
            }
            if (criteria.getIp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIp(), SmsHistory_.ip));
            }
            if (criteria.getTypemsg() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTypemsg(), SmsHistory_.typemsg));
            }
            if (criteria.getIdMember() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdMember(), SmsHistory_.idMember));
            }
            */
        }
        return specification;
    }

}
