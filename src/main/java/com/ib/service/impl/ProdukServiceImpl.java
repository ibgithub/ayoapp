package com.ib.service.impl;

import com.ib.service.ProdukService;
import com.ib.domain.Produk;
import com.ib.repository.ProdukRepository;
import com.ib.repository.search.ProdukSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Produk.
 */
@Service
@Transactional
public class ProdukServiceImpl implements ProdukService{

    private final Logger log = LoggerFactory.getLogger(ProdukServiceImpl.class);

    private final ProdukRepository produkRepository;

    private final ProdukSearchRepository produkSearchRepository;
    public ProdukServiceImpl(ProdukRepository produkRepository, ProdukSearchRepository produkSearchRepository) {
        this.produkRepository = produkRepository;
        this.produkSearchRepository = produkSearchRepository;
    }

    /**
     * Save a produk.
     *
     * @param produk the entity to save
     * @return the persisted entity
     */
    @Override
    public Produk save(Produk produk) {
        log.debug("Request to save Produk : {}", produk);
        Produk result = produkRepository.save(produk);
        produkSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the produks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Produk> findAll(Pageable pageable) {
        log.debug("Request to get all Produks");
        return produkRepository.findAll(pageable);
    }

    /**
     *  Get one produk by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Produk findOne(Long id) {
        log.debug("Request to get Produk : {}", id);
        return produkRepository.findOne(id);
    }

    /**
     *  Delete the  produk by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Produk : {}", id);
        produkRepository.delete(id);
        produkSearchRepository.delete(id);
    }

    /**
     * Search for the produk corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Produk> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Produks for query {}", query);
        Page<Produk> result = produkSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
