package com.ib.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ib.domain.Produk;
import com.ib.service.ProdukService;
import com.ib.web.rest.util.HeaderUtil;
import com.ib.web.rest.util.PaginationUtil;
import com.ib.service.dto.ProdukCriteria;
import com.ib.service.ProdukQueryService;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Produk.
 */
@RestController
@RequestMapping("/api")
public class ProdukResource {

    private final Logger log = LoggerFactory.getLogger(ProdukResource.class);

    private static final String ENTITY_NAME = "produk";

    private final ProdukService produkService;
    private final ProdukQueryService produkQueryService;

    public ProdukResource(ProdukService produkService, ProdukQueryService produkQueryService) {
        this.produkService = produkService;
        this.produkQueryService = produkQueryService;
    }

    /**
     * POST  /produks : Create a new produk.
     *
     * @param produk the produk to create
     * @return the ResponseEntity with status 201 (Created) and with body the new produk, or with status 400 (Bad Request) if the produk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/produks")
    @Timed
    public ResponseEntity<Produk> createProduk(@RequestBody Produk produk) throws URISyntaxException {
        log.debug("REST request to save Produk : {}", produk);
        if (produk.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new produk cannot already have an ID")).body(null);
        }
        Produk result = produkService.save(produk);
        return ResponseEntity.created(new URI("/api/produks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produks : Updates an existing produk.
     *
     * @param produk the produk to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated produk,
     * or with status 400 (Bad Request) if the produk is not valid,
     * or with status 500 (Internal Server Error) if the produk couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/produks")
    @Timed
    public ResponseEntity<Produk> updateProduk(@RequestBody Produk produk) throws URISyntaxException {
        log.debug("REST request to update Produk : {}", produk);
        if (produk.getId() == null) {
            return createProduk(produk);
        }
        Produk result = produkService.save(produk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, produk.getId().toString()))
            .body(result);
    }

    /**
     * GET  /produks : get all the produks.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of produks in body
     */
    @GetMapping("/produks")
    @Timed
    public ResponseEntity<List<Produk>> getAllProduks(ProdukCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get Produks by criteria: {}", criteria);
        Page<Produk> page = produkQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/produks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /produks/:id : get the "id" produk.
     *
     * @param id the id of the produk to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the produk, or with status 404 (Not Found)
     */
    @GetMapping("/produks/{id}")
    @Timed
    public ResponseEntity<Produk> getProduk(@PathVariable Long id) {
        log.debug("REST request to get Produk : {}", id);
        Produk produk = produkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(produk));
    }

    /**
     * DELETE  /produks/:id : delete the "id" produk.
     *
     * @param id the id of the produk to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/produks/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduk(@PathVariable Long id) {
        log.debug("REST request to delete Produk : {}", id);
        produkService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/produks?query=:query : search for the produk corresponding
     * to the query.
     *
     * @param query the query of the produk search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/produks")
    @Timed
    public ResponseEntity<List<Produk>> searchProduks(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Produks for query {}", query);
        Page<Produk> page = produkService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/produks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
