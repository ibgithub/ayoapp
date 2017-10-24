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

import com.ib.domain.Distributor;
import com.ib.domain.*; // for static metamodels
import com.ib.repository.DistributorRepository;
import com.ib.repository.search.DistributorSearchRepository;
import com.ib.service.dto.DistributorCriteria;


/**
 * Service for executing complex queries for Distributor entities in the database.
 * The main input is a {@link DistributorCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Distributor} or a {@link Page} of {%link Distributor} which fullfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class DistributorQueryService extends QueryService<Distributor> {

    private final Logger log = LoggerFactory.getLogger(DistributorQueryService.class);


    private final DistributorRepository distributorRepository;

    private final DistributorSearchRepository distributorSearchRepository;
    public DistributorQueryService(DistributorRepository distributorRepository, DistributorSearchRepository distributorSearchRepository) {
        this.distributorRepository = distributorRepository;
        this.distributorSearchRepository = distributorSearchRepository;
    }

    /**
     * Return a {@link List} of {%link Distributor} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Distributor> findByCriteria(DistributorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Distributor> specification = createSpecification(criteria);
        return distributorRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Distributor} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Distributor> findByCriteria(DistributorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Distributor> specification = createSpecification(criteria);
        return distributorRepository.findAll(specification, page);
    }

    /**
     * Function to convert DistributorCriteria to a {@link Specifications}
     */
    private Specifications<Distributor> createSpecification(DistributorCriteria criteria) {
        Specifications<Distributor> specification = Specifications.where(null);
        if (criteria != null) {
        	/*
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Distributor_.id));
            }
            if (criteria.getIdDistributor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdDistributor(), Distributor_.idDistributor));
            }
            if (criteria.getNama() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNama(), Distributor_.nama));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), Distributor_.status));
            }
            if (criteria.getKodeId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKodeId(), Distributor_.kodeId));
            }
            if (criteria.getPin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPin(), Distributor_.pin));
            }
            if (criteria.getCom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCom(), Distributor_.com));
            }
            if (criteria.getNoKontak() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoKontak(), Distributor_.noKontak));
            }
            if (criteria.getMetode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMetode(), Distributor_.metode));
            }
            if (criteria.getKodeParsing() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKodeParsing(), Distributor_.kodeParsing));
            }
            if (criteria.getKodeParsing2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKodeParsing2(), Distributor_.kodeParsing2));
            }
            if (criteria.getReplyno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReplyno(), Distributor_.replyno));
            }
            if (criteria.getTglInput() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTglInput(), Distributor_.tglInput));
            }
            if (criteria.getUserInput() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserInput(), Distributor_.userInput));
            }
            if (criteria.getTglUpdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTglUpdate(), Distributor_.tglUpdate));
            }
            if (criteria.getUserUpdate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserUpdate(), Distributor_.userUpdate));
            }
            if (criteria.getIp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIp(), Distributor_.ip));
            }
            if (criteria.getIsvre() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsvre(), Distributor_.isvre));
            }
            if (criteria.getIsgtw() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsgtw(), Distributor_.isgtw));
            }
            if (criteria.getUgtw() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUgtw(), Distributor_.ugtw));
            }
            if (criteria.getIsfilter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsfilter(), Distributor_.isfilter));
            }
            if (criteria.getParseSaldo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParseSaldo(), Distributor_.parseSaldo));
            }
            if (criteria.getParseHarga() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParseHarga(), Distributor_.parseHarga));
            }
            if (criteria.getTiketWrap() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTiketWrap(), Distributor_.tiketWrap));
            }
            if (criteria.getIstiketsend() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIstiketsend(), Distributor_.istiketsend));
            }
            if (criteria.getPesanTiket() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPesanTiket(), Distributor_.pesanTiket));
            }
            if (criteria.getSaldoSupwarn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSaldoSupwarn(), Distributor_.saldoSupwarn));
            }
            if (criteria.getIssortby() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIssortby(), Distributor_.issortby));
            }
            if (criteria.getParseUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParseUnit(), Distributor_.parseUnit));
            }
            if (criteria.getIsulangim() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsulangim(), Distributor_.isulangim));
            }
            if (criteria.getIshlr() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIshlr(), Distributor_.ishlr));
            }
            if (criteria.getKodeParsing3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKodeParsing3(), Distributor_.kodeParsing3));
            }
            if (criteria.getIdHistory() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdHistory(), Distributor_.idHistory));
            }
            if (criteria.getKodeParsing4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKodeParsing4(), Distributor_.kodeParsing4));
            }
            if (criteria.getSelisihSupwarn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSelisihSupwarn(), Distributor_.selisihSupwarn));
            }
            if (criteria.getTimeon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTimeon(), Distributor_.timeon));
            }
            if (criteria.getTimeoff() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTimeoff(), Distributor_.timeoff));
            }
            */
        }
        return specification;
    }

}
