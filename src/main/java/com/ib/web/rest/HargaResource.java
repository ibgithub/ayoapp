package com.ib.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ib.domain.Harga;
import com.ib.service.HargaService;
import com.ib.web.rest.util.HeaderUtil;
import com.ib.web.rest.util.PaginationUtil;
import com.ib.service.dto.HargaCriteria;
import com.ib.service.HargaQueryService;
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
 * REST controller for managing Harga.
 */
@RestController
@RequestMapping("/api")
public class HargaResource {

    private final Logger log = LoggerFactory.getLogger(HargaResource.class);

    private static final String ENTITY_NAME = "harga";

    private final HargaService hargaService;
    private final HargaQueryService hargaQueryService;

    public HargaResource(HargaService hargaService, HargaQueryService hargaQueryService) {
        this.hargaService = hargaService;
        this.hargaQueryService = hargaQueryService;
    }

    /**
     * POST  /hargas : Create a new harga.
     *
     * @param harga the harga to create
     * @return the ResponseEntity with status 201 (Created) and with body the new harga, or with status 400 (Bad Request) if the harga has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hargas")
    @Timed
    public ResponseEntity<Harga> createHarga(@RequestBody Harga harga) throws URISyntaxException {
        log.debug("REST request to save Harga : {}", harga);
        if (harga.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new harga cannot already have an ID")).body(null);
        }
        Harga result = hargaService.save(harga);
        return ResponseEntity.created(new URI("/api/hargas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hargas : Updates an existing harga.
     *
     * @param harga the harga to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated harga,
     * or with status 400 (Bad Request) if the harga is not valid,
     * or with status 500 (Internal Server Error) if the harga couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hargas")
    @Timed
    public ResponseEntity<Harga> updateHarga(@RequestBody Harga harga) throws URISyntaxException {
        log.debug("REST request to update Harga : {}", harga);
        if (harga.getId() == null) {
            return createHarga(harga);
        }
        Harga result = hargaService.save(harga);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, harga.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hargas : get all the hargas.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of hargas in body
     */
    @GetMapping("/hargas")
    @Timed
    public ResponseEntity<List<Harga>> getAllHargas(HargaCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get Hargas by criteria: {}", criteria);
        Page<Harga> page = hargaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hargas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hargas/:id : get the "id" harga.
     *
     * @param id the id of the harga to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the harga, or with status 404 (Not Found)
     */
    @GetMapping("/hargas/{id}")
    @Timed
    public ResponseEntity<Harga> getHarga(@PathVariable Long id) {
        log.debug("REST request to get Harga : {}", id);
        Harga harga = hargaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(harga));
    }

    /**
     * DELETE  /hargas/:id : delete the "id" harga.
     *
     * @param id the id of the harga to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hargas/{id}")
    @Timed
    public ResponseEntity<Void> deleteHarga(@PathVariable Long id) {
        log.debug("REST request to delete Harga : {}", id);
        hargaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hargas?query=:query : search for the harga corresponding
     * to the query.
     *
     * @param query the query of the harga search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/hargas")
    @Timed
    public ResponseEntity<List<Harga>> searchHargas(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Hargas for query {}", query);
        Page<Harga> page = hargaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/hargas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
