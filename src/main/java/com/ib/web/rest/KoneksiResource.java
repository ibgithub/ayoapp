package com.ib.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ib.domain.Koneksi;
import com.ib.service.KoneksiService;
import com.ib.web.rest.util.HeaderUtil;
import com.ib.web.rest.util.PaginationUtil;
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
 * REST controller for managing Koneksi.
 */
@RestController
@RequestMapping("/api")
public class KoneksiResource {

    private final Logger log = LoggerFactory.getLogger(KoneksiResource.class);

    private static final String ENTITY_NAME = "koneksi";

    private final KoneksiService koneksiService;

    public KoneksiResource(KoneksiService koneksiService) {
        this.koneksiService = koneksiService;
    }

    /**
     * POST  /koneksis : Create a new koneksi.
     *
     * @param koneksi the koneksi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new koneksi, or with status 400 (Bad Request) if the koneksi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/koneksis")
    @Timed
    public ResponseEntity<Koneksi> createKoneksi(@RequestBody Koneksi koneksi) throws URISyntaxException {
        log.debug("REST request to save Koneksi : {}", koneksi);
        if (koneksi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new koneksi cannot already have an ID")).body(null);
        }
        Koneksi result = koneksiService.save(koneksi);
        return ResponseEntity.created(new URI("/api/koneksis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /koneksis : Updates an existing koneksi.
     *
     * @param koneksi the koneksi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated koneksi,
     * or with status 400 (Bad Request) if the koneksi is not valid,
     * or with status 500 (Internal Server Error) if the koneksi couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/koneksis")
    @Timed
    public ResponseEntity<Koneksi> updateKoneksi(@RequestBody Koneksi koneksi) throws URISyntaxException {
        log.debug("REST request to update Koneksi : {}", koneksi);
        if (koneksi.getId() == null) {
            return createKoneksi(koneksi);
        }
        Koneksi result = koneksiService.save(koneksi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, koneksi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /koneksis : get all the koneksis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of koneksis in body
     */
    @GetMapping("/koneksis")
    @Timed
    public ResponseEntity<List<Koneksi>> getAllKoneksis(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Koneksis");
        Page<Koneksi> page = koneksiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/koneksis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /koneksis/:id : get the "id" koneksi.
     *
     * @param id the id of the koneksi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the koneksi, or with status 404 (Not Found)
     */
    @GetMapping("/koneksis/{id}")
    @Timed
    public ResponseEntity<Koneksi> getKoneksi(@PathVariable Long id) {
        log.debug("REST request to get Koneksi : {}", id);
        Koneksi koneksi = koneksiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(koneksi));
    }

    /**
     * DELETE  /koneksis/:id : delete the "id" koneksi.
     *
     * @param id the id of the koneksi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/koneksis/{id}")
    @Timed
    public ResponseEntity<Void> deleteKoneksi(@PathVariable Long id) {
        log.debug("REST request to delete Koneksi : {}", id);
        koneksiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/koneksis?query=:query : search for the koneksi corresponding
     * to the query.
     *
     * @param query the query of the koneksi search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/koneksis")
    @Timed
    public ResponseEntity<List<Koneksi>> searchKoneksis(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Koneksis for query {}", query);
        Page<Koneksi> page = koneksiService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/koneksis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
