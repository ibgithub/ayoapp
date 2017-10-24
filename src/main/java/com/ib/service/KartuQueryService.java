package com.ib.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.ib.domain.Kartu;
import com.ib.domain.*; // for static metamodels
import com.ib.repository.KartuRepository;
import com.ib.repository.search.KartuSearchRepository;
import com.ib.service.dto.KartuCriteria;


/**
 * Service for executing complex queries for Kartu entities in the database.
 * The main input is a {@link KartuCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Kartu} or a {@link Page} of {%link Kartu} which fullfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class KartuQueryService extends QueryService<Kartu> {

    private final Logger log = LoggerFactory.getLogger(KartuQueryService.class);


    private final KartuRepository kartuRepository;

    private final KartuSearchRepository kartuSearchRepository;
    public KartuQueryService(KartuRepository kartuRepository, KartuSearchRepository kartuSearchRepository) {
        this.kartuRepository = kartuRepository;
        this.kartuSearchRepository = kartuSearchRepository;
    }

    /**
     * Return a {@link List} of {%link Kartu} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Kartu> findByCriteria(KartuCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Kartu> specification = createSpecification(criteria);
        return kartuRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Kartu} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Kartu> findByCriteria(KartuCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Kartu> specification = createSpecification(criteria);
        return kartuRepository.findAll(specification, page);
    }

    /**
     * Function to convert KartuCriteria to a {@link Specifications}
     */
    private Specifications<Kartu> createSpecification(KartuCriteria criteria) {
        Specifications<Kartu> specification = Specifications.where(null);
        if (criteria != null) {
        	/*
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Kartu_.id));
            }
            if (criteria.getIdKartu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdKartu(), Kartu_.idKartu));
            }
            if (criteria.getNama() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNama(), Kartu_.nama));
            }
            if (criteria.getIdOperator() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdOperator(), Kartu_.idOperator));
            }
            if (criteria.getCekHlr() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCekHlr(), Kartu_.cekHlr));
            }
            if (criteria.getKetkartu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKetkartu(), Kartu_.ketkartu));
            }
            */
        }
        return specification;
    }

}
