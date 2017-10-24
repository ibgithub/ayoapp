package com.ib.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ib.domain.Kartu;
import com.ib.service.KartuService;
import com.ib.web.rest.util.HeaderUtil;
import com.ib.web.rest.util.PaginationUtil;
import com.ib.service.dto.KartuCriteria;
import com.ib.service.KartuQueryService;
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
 * REST controller for managing Kartu.
 */
@RestController
@RequestMapping("/api")
public class KartuResource {

    private final Logger log = LoggerFactory.getLogger(KartuResource.class);

    private static final String ENTITY_NAME = "kartu";

    private final KartuService kartuService;
    private final KartuQueryService kartuQueryService;

    public KartuResource(KartuService kartuService, KartuQueryService kartuQueryService) {
        this.kartuService = kartuService;
        this.kartuQueryService = kartuQueryService;
    }

    /**
     * POST  /kartus : Create a new kartu.
     *
     * @param kartu the kartu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kartu, or with status 400 (Bad Request) if the kartu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/kartus")
    @Timed
    public ResponseEntity<Kartu> createKartu(@RequestBody Kartu kartu) throws URISyntaxException {
        log.debug("REST request to save Kartu : {}", kartu);
        if (kartu.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new kartu cannot already have an ID")).body(null);
        }
        Kartu result = kartuService.save(kartu);
        return ResponseEntity.created(new URI("/api/kartus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kartus : Updates an existing kartu.
     *
     * @param kartu the kartu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kartu,
     * or with status 400 (Bad Request) if the kartu is not valid,
     * or with status 500 (Internal Server Error) if the kartu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/kartus")
    @Timed
    public ResponseEntity<Kartu> updateKartu(@RequestBody Kartu kartu) throws URISyntaxException {
        log.debug("REST request to update Kartu : {}", kartu);
        if (kartu.getId() == null) {
            return createKartu(kartu);
        }
        Kartu result = kartuService.save(kartu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kartu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kartus : get all the kartus.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of kartus in body
     */
    @GetMapping("/kartus")
    @Timed
    public ResponseEntity<List<Kartu>> getAllKartus(KartuCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get Kartus by criteria: {}", criteria);
        Page<Kartu> page = kartuQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/kartus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /kartus/:id : get the "id" kartu.
     *
     * @param id the id of the kartu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kartu, or with status 404 (Not Found)
     */
    @GetMapping("/kartus/{id}")
    @Timed
    public ResponseEntity<Kartu> getKartu(@PathVariable Long id) {
        log.debug("REST request to get Kartu : {}", id);
        Kartu kartu = kartuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(kartu));
    }

    /**
     * DELETE  /kartus/:id : delete the "id" kartu.
     *
     * @param id the id of the kartu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/kartus/{id}")
    @Timed
    public ResponseEntity<Void> deleteKartu(@PathVariable Long id) {
        log.debug("REST request to delete Kartu : {}", id);
        kartuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/kartus?query=:query : search for the kartu corresponding
     * to the query.
     *
     * @param query the query of the kartu search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/kartus")
    @Timed
    public ResponseEntity<List<Kartu>> searchKartus(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Kartus for query {}", query);
        Page<Kartu> page = kartuService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/kartus");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
