package com.ib.service;


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

import com.ib.domain.Rebate;
import com.ib.domain.*; // for static metamodels
import com.ib.repository.RebateRepository;
import com.ib.repository.search.RebateSearchRepository;
import com.ib.service.dto.RebateCriteria;


/**
 * Service for executing complex queries for Rebate entities in the database.
 * The main input is a {@link RebateCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Rebate} or a {@link Page} of {%link Rebate} which fullfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class RebateQueryService extends QueryService<Rebate> {

    private final Logger log = LoggerFactory.getLogger(RebateQueryService.class);


    private final RebateRepository rebateRepository;

    private final RebateSearchRepository rebateSearchRepository;
    public RebateQueryService(RebateRepository rebateRepository, RebateSearchRepository rebateSearchRepository) {
        this.rebateRepository = rebateRepository;
        this.rebateSearchRepository = rebateSearchRepository;
    }

    /**
     * Return a {@link List} of {%link Rebate} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Rebate> findByCriteria(RebateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Rebate> specification = createSpecification(criteria);
        return rebateRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Rebate} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Rebate> findByCriteria(RebateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Rebate> specification = createSpecification(criteria);
        return rebateRepository.findAll(specification, page);
    }

    /**
     * Function to convert RebateCriteria to a {@link Specifications}
     */
    private Specifications<Rebate> createSpecification(RebateCriteria criteria) {
        Specifications<Rebate> specification = Specifications.where(null);
        if (criteria != null) {
        	/*
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Rebate_.id));
            }
            if (criteria.getIdTransaksi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdTransaksi(), Rebate_.idTransaksi));
            }
            if (criteria.getJml() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJml(), Rebate_.jml));
            }
            if (criteria.getHargaProduk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHargaProduk(), Rebate_.hargaProduk));
            }
            if (criteria.getIdMember() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdMember(), Rebate_.idMember));
            }
            if (criteria.getLevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLevel(), Rebate_.level));
            }
            if (criteria.getBulan() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBulan(), Rebate_.bulan));
            }
            if (criteria.getTahun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTahun(), Rebate_.tahun));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), Rebate_.status));
            }
            */
        }
        return specification;
    }

}
