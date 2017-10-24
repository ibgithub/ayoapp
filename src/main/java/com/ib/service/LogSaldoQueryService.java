package com.ib.service;


import java.time.ZonedDateTime;
import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.ib.domain.LogSaldo;
import com.ib.domain.*; // for static metamodels
import com.ib.repository.LogSaldoRepository;
import com.ib.repository.search.LogSaldoSearchRepository;
import com.ib.service.dto.LogSaldoCriteria;


/**
 * Service for executing complex queries for LogSaldo entities in the database.
 * The main input is a {@link LogSaldoCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link LogSaldo} or a {@link Page} of {%link LogSaldo} which fullfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class LogSaldoQueryService extends QueryService<LogSaldo> {

    private final Logger log = LoggerFactory.getLogger(LogSaldoQueryService.class);


    private final LogSaldoRepository logSaldoRepository;

    private final LogSaldoSearchRepository logSaldoSearchRepository;
    public LogSaldoQueryService(LogSaldoRepository logSaldoRepository, LogSaldoSearchRepository logSaldoSearchRepository) {
        this.logSaldoRepository = logSaldoRepository;
        this.logSaldoSearchRepository = logSaldoSearchRepository;
    }

    /**
     * Return a {@link List} of {%link LogSaldo} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LogSaldo> findByCriteria(LogSaldoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<LogSaldo> specification = createSpecification(criteria);
        return logSaldoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link LogSaldo} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LogSaldo> findByCriteria(LogSaldoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<LogSaldo> specification = createSpecification(criteria);
        return logSaldoRepository.findAll(specification, page);
    }

    /**
     * Function to convert LogSaldoCriteria to a {@link Specifications}
     */
    private Specifications<LogSaldo> createSpecification(LogSaldoCriteria criteria) {
        Specifications<LogSaldo> specification = Specifications.where(null);
        if (criteria != null) {
        	/*
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), LogSaldo_.id));
            }
            if (criteria.getIdMember() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdMember(), LogSaldo_.idMember));
            }
            if (criteria.getSaldo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSaldo(), LogSaldo_.saldo));
            }
            if (criteria.getAct() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAct(), LogSaldo_.act));
            }
            if (criteria.getTgl() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTgl(), LogSaldo_.tgl));
            }
            if (criteria.getKet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKet(), LogSaldo_.ket));
            }
            if (criteria.getRef() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRef(), LogSaldo_.ref));
            }
            if (criteria.getTkode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTkode(), LogSaldo_.tkode));
            }
            if (criteria.getKodetrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKodetrx(), LogSaldo_.kodetrx));
            }
            if (criteria.getMsg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMsg(), LogSaldo_.msg));
            }
            if (criteria.getUserInput() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserInput(), LogSaldo_.userInput));
            }
            if (criteria.getIsstok() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsstok(), LogSaldo_.isstok));
            }
            */
        }
        return specification;
    }

}
