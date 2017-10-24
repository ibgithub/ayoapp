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

import com.ib.domain.Harga;
import com.ib.domain.*; // for static metamodels
import com.ib.repository.HargaRepository;
import com.ib.repository.search.HargaSearchRepository;
import com.ib.service.dto.HargaCriteria;


/**
 * Service for executing complex queries for Harga entities in the database.
 * The main input is a {@link HargaCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Harga} or a {@link Page} of {%link Harga} which fullfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class HargaQueryService extends QueryService<Harga> {

    private final Logger log = LoggerFactory.getLogger(HargaQueryService.class);


    private final HargaRepository hargaRepository;

    private final HargaSearchRepository hargaSearchRepository;
    public HargaQueryService(HargaRepository hargaRepository, HargaSearchRepository hargaSearchRepository) {
        this.hargaRepository = hargaRepository;
        this.hargaSearchRepository = hargaSearchRepository;
    }

    /**
     * Return a {@link List} of {%link Harga} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Harga> findByCriteria(HargaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Harga> specification = createSpecification(criteria);
        return hargaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Harga} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Harga> findByCriteria(HargaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Harga> specification = createSpecification(criteria);
        return hargaRepository.findAll(specification, page);
    }

    /**
     * Function to convert HargaCriteria to a {@link Specifications}
     */
    private Specifications<Harga> createSpecification(HargaCriteria criteria) {
        Specifications<Harga> specification = Specifications.where(null);
        if (criteria != null) {
        	/*
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Harga_.id));
            }
            if (criteria.getIdHarga() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdHarga(), Harga_.idHarga));
            }
            if (criteria.getIdProduk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdProduk(), Harga_.idProduk));
            }
            if (criteria.getIdMember() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdMember(), Harga_.idMember));
            }
            if (criteria.getHargaJual() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHargaJual(), Harga_.hargaJual));
            }
            if (criteria.getTglInput() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTglInput(), Harga_.tglInput));
            }
            if (criteria.getUserInput() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserInput(), Harga_.userInput));
            }
            if (criteria.getHargaBefore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHargaBefore(), Harga_.hargaBefore));
            }
            */
        }
        return specification;
    }

}
