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

import com.ib.domain.Produk;
import com.ib.domain.*; // for static metamodels
import com.ib.repository.ProdukRepository;
import com.ib.repository.search.ProdukSearchRepository;
import com.ib.service.dto.ProdukCriteria;


/**
 * Service for executing complex queries for Produk entities in the database.
 * The main input is a {@link ProdukCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Produk} or a {@link Page} of {%link Produk} which fullfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class ProdukQueryService extends QueryService<Produk> {

    private final Logger log = LoggerFactory.getLogger(ProdukQueryService.class);


    private final ProdukRepository produkRepository;

    private final ProdukSearchRepository produkSearchRepository;
    public ProdukQueryService(ProdukRepository produkRepository, ProdukSearchRepository produkSearchRepository) {
        this.produkRepository = produkRepository;
        this.produkSearchRepository = produkSearchRepository;
    }

    /**
     * Return a {@link List} of {%link Produk} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Produk> findByCriteria(ProdukCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Produk> specification = createSpecification(criteria);
        return produkRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Produk} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Produk> findByCriteria(ProdukCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Produk> specification = createSpecification(criteria);
        return produkRepository.findAll(specification, page);
    }

    /**
     * Function to convert ProdukCriteria to a {@link Specifications}
     */
    private Specifications<Produk> createSpecification(ProdukCriteria criteria) {
        Specifications<Produk> specification = Specifications.where(null);
        if (criteria != null) {
        	/*
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Produk_.id));
            }
            if (criteria.getIdProduk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdProduk(), Produk_.idProduk));
            }
            if (criteria.getKodeProduk() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKodeProduk(), Produk_.kodeProduk));
            }
            if (criteria.getIdKartu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdKartu(), Produk_.idKartu));
            }
            if (criteria.getDenom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDenom(), Produk_.denom));
            }
            if (criteria.getHpp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHpp(), Produk_.hpp));
            }
            if (criteria.getHargaMan() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHargaMan(), Produk_.hargaMan));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), Produk_.status));
            }
            if (criteria.getGangguan() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGangguan(), Produk_.gangguan));
            }
            if (criteria.getIdDistributor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdDistributor(), Produk_.idDistributor));
            }
            if (criteria.getFisik() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFisik(), Produk_.fisik));
            }
            if (criteria.getTglUpdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTglUpdate(), Produk_.tglUpdate));
            }
            if (criteria.getUserUpdate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserUpdate(), Produk_.userUpdate));
            }
            if (criteria.getIdDistributor2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdDistributor2(), Produk_.idDistributor2));
            }
            if (criteria.getKonversiSaldo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKonversiSaldo(), Produk_.konversiSaldo));
            }
            if (criteria.getIsreport() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsreport(), Produk_.isreport));
            }
            if (criteria.getIssplit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIssplit(), Produk_.issplit));
            }
            if (criteria.getOtotimeopen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOtotimeopen(), Produk_.ototimeopen));
            }
            if (criteria.getOtotimeclose() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOtotimeclose(), Produk_.ototimeclose));
            }
            if (criteria.getIdDistributor3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdDistributor3(), Produk_.idDistributor3));
            }
            if (criteria.getIsstok() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsstok(), Produk_.isstok));
            }
            if (criteria.getOtoclosestatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOtoclosestatus(), Produk_.otoclosestatus));
            }
            if (criteria.getSaldoMin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSaldoMin(), Produk_.saldoMin));
            }
            if (criteria.getAkses() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAkses(), Produk_.akses));
            }
            if (criteria.getHlr() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHlr(), Produk_.hlr));
            }
            if (criteria.getIsulang() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsulang(), Produk_.isulang));
            }
            if (criteria.getIsurut() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsurut(), Produk_.isurut));
            }
            if (criteria.getFormatppob() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFormatppob(), Produk_.formatppob));
            }
            if (criteria.getJenisppob() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJenisppob(), Produk_.jenisppob));
            }
            if (criteria.getKetproduk() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKetproduk(), Produk_.ketproduk));
            }
            */
        }
        return specification;
    }

}
