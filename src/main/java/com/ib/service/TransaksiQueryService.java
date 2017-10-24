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

import com.ib.domain.Transaksi;
import com.ib.domain.*; // for static metamodels
import com.ib.repository.TransaksiRepository;
import com.ib.repository.search.TransaksiSearchRepository;
import com.ib.service.dto.TransaksiCriteria;


/**
 * Service for executing complex queries for Transaksi entities in the database.
 * The main input is a {@link TransaksiCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Transaksi} or a {@link Page} of {%link Transaksi} which fullfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class TransaksiQueryService extends QueryService<Transaksi> {

    private final Logger log = LoggerFactory.getLogger(TransaksiQueryService.class);


    private final TransaksiRepository transaksiRepository;

    private final TransaksiSearchRepository transaksiSearchRepository;
    public TransaksiQueryService(TransaksiRepository transaksiRepository, TransaksiSearchRepository transaksiSearchRepository) {
        this.transaksiRepository = transaksiRepository;
        this.transaksiSearchRepository = transaksiSearchRepository;
    }

    /**
     * Return a {@link List} of {%link Transaksi} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Transaksi> findByCriteria(TransaksiCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Transaksi> specification = createSpecification(criteria);
        return transaksiRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Transaksi} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Transaksi> findByCriteria(TransaksiCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Transaksi> specification = createSpecification(criteria);
        return transaksiRepository.findAll(specification, page);
    }

    /**
     * Function to convert TransaksiCriteria to a {@link Specifications}
     */
    private Specifications<Transaksi> createSpecification(TransaksiCriteria criteria) {
        Specifications<Transaksi> specification = Specifications.where(null);
        if (criteria != null) {
            /*if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Transaksi_.id));
            }
            if (criteria.getTglTransaksi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTglTransaksi(), Transaksi_.tglTransaksi));
            }
            if (criteria.getIdMember() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdMember(), Transaksi_.idMember));
            }
            if (criteria.getNama() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNama(), Transaksi_.nama));
            }
            if (criteria.getJml() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJml(), Transaksi_.jml));
            }
            if (criteria.getKodeTrx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKodeTrx(), Transaksi_.kodeTrx));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), Transaksi_.status));
            }
            if (criteria.getSaldoAwal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSaldoAwal(), Transaksi_.saldoAwal));
            }
            if (criteria.getSaldoAkhir() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSaldoAkhir(), Transaksi_.saldoAkhir));
            }
            if (criteria.getKet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKet(), Transaksi_.ket));
            }
            if (criteria.getTglInput() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTglInput(), Transaksi_.tglInput));
            }
            if (criteria.getUserInput() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserInput(), Transaksi_.userInput));
            }
            if (criteria.getIsstok() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsstok(), Transaksi_.isstok));
            }*/
        }
        return specification;
    }

}
