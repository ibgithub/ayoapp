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

import com.ib.domain.TransaksiPulsa;
import com.ib.domain.*; // for static metamodels
import com.ib.repository.TransaksiPulsaRepository;
import com.ib.repository.search.TransaksiPulsaSearchRepository;
import com.ib.service.dto.TransaksiPulsaCriteria;


/**
 * Service for executing complex queries for TransaksiPulsa entities in the database.
 * The main input is a {@link TransaksiPulsaCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link TransaksiPulsa} or a {@link Page} of {%link TransaksiPulsa} which fullfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class TransaksiPulsaQueryService extends QueryService<TransaksiPulsa> {

    private final Logger log = LoggerFactory.getLogger(TransaksiPulsaQueryService.class);


    private final TransaksiPulsaRepository transaksiPulsaRepository;

    private final TransaksiPulsaSearchRepository transaksiPulsaSearchRepository;
    public TransaksiPulsaQueryService(TransaksiPulsaRepository transaksiPulsaRepository, TransaksiPulsaSearchRepository transaksiPulsaSearchRepository) {
        this.transaksiPulsaRepository = transaksiPulsaRepository;
        this.transaksiPulsaSearchRepository = transaksiPulsaSearchRepository;
    }

    /**
     * Return a {@link List} of {%link TransaksiPulsa} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransaksiPulsa> findByCriteria(TransaksiPulsaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<TransaksiPulsa> specification = createSpecification(criteria);
        return transaksiPulsaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link TransaksiPulsa} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransaksiPulsa> findByCriteria(TransaksiPulsaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<TransaksiPulsa> specification = createSpecification(criteria);
        return transaksiPulsaRepository.findAll(specification, page);
    }

    /**
     * Function to convert TransaksiPulsaCriteria to a {@link Specifications}
     */
    private Specifications<TransaksiPulsa> createSpecification(TransaksiPulsaCriteria criteria) {
        Specifications<TransaksiPulsa> specification = Specifications.where(null);
        if (criteria != null) {
        	/*
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TransaksiPulsa_.id));
            }
            if (criteria.getKodeProduk() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKodeProduk(), TransaksiPulsa_.kodeProduk));
            }
            if (criteria.getHpTujuan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHpTujuan(), TransaksiPulsa_.hpTujuan));
            }
            if (criteria.getHpMember() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHpMember(), TransaksiPulsa_.hpMember));
            }
            if (criteria.getHargaBeli() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHargaBeli(), TransaksiPulsa_.hargaBeli));
            }
            if (criteria.getHpp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHpp(), TransaksiPulsa_.hpp));
            }
            if (criteria.getLaba() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLaba(), TransaksiPulsa_.laba));
            }
            if (criteria.getCom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCom(), TransaksiPulsa_.com));
            }
            if (criteria.getAdmrpt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdmrpt(), TransaksiPulsa_.admrpt));
            }
            if (criteria.getUlang() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUlang(), TransaksiPulsa_.ulang));
            }
            if (criteria.getUlangTgl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUlangTgl(), TransaksiPulsa_.ulangTgl));
            }
            if (criteria.getFisik() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFisik(), TransaksiPulsa_.fisik));
            }
            if (criteria.getManual() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getManual(), TransaksiPulsa_.manual));
            }
            if (criteria.getSwitch1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSwitch1(), TransaksiPulsa_.switch1));
            }
            if (criteria.getKodeGagal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKodeGagal(), TransaksiPulsa_.kodeGagal));
            }
            if (criteria.getWaitSms() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWaitSms(), TransaksiPulsa_.waitSms));
            }
            if (criteria.getHead2Head() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHead2Head(), TransaksiPulsa_.head2Head));
            }
            if (criteria.getHpPembeli() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHpPembeli(), TransaksiPulsa_.hpPembeli));
            }
            if (criteria.getBeaAdmin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBeaAdmin(), TransaksiPulsa_.beaAdmin));
            }
            if (criteria.getIsReport() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsReport(), TransaksiPulsa_.isReport));
            }
            if (criteria.getSuplierKe() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSuplierKe(), TransaksiPulsa_.suplierKe));
            }
            if (criteria.getIdDistributor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdDistributor(), TransaksiPulsa_.idDistributor));
            }
            if (criteria.getSn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSn(), TransaksiPulsa_.sn));
            }
            if (criteria.getIp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIp(), TransaksiPulsa_.ip));
            }
            if (criteria.getPesankirim() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPesankirim(), TransaksiPulsa_.pesankirim));
            }
            if (criteria.getMetode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMetode(), TransaksiPulsa_.metode));
            }
            if (criteria.getToDistributor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getToDistributor(), TransaksiPulsa_.toDistributor));
            }
            if (criteria.getIdPortip() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdPortip(), TransaksiPulsa_.idPortip));
            }
            if (criteria.getTimeupdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeupdate(), TransaksiPulsa_.timeupdate));
            }
            if (criteria.getIdDistributorOld() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdDistributorOld(), TransaksiPulsa_.idDistributorOld));
            }
            if (criteria.getIdDistributorProduk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdDistributorProduk(), TransaksiPulsa_.idDistributorProduk));
            }
            if (criteria.getSaldoSup() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSaldoSup(), TransaksiPulsa_.saldoSup));
            }
            if (criteria.getIsrebate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsrebate(), TransaksiPulsa_.isrebate));
            }
            if (criteria.getEnginename() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEnginename(), TransaksiPulsa_.enginename));
            }
            if (criteria.getTypemsg() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTypemsg(), TransaksiPulsa_.typemsg));
            }
            if (criteria.getIsro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsro(), TransaksiPulsa_.isro));
            }
            */
        }
        return specification;
    }

}
