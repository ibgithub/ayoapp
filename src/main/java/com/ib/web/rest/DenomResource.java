package com.ib.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ib.domain.Denom;
import com.ib.service.DenomService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Denom.
 */
@RestController
@RequestMapping("/api")
public class DenomResource {

    private final Logger log = LoggerFactory.getLogger(DenomResource.class);

    private static final String ENTITY_NAME = "denom";

    private final DenomService denomService;

    public DenomResource(DenomService denomService) {
        this.denomService = denomService;
    }

    /**
     * POST  /denoms : Create a new denom.
     *
     * @param denom the denom to create
     * @return the ResponseEntity with status 201 (Created) and with body the new denom, or with status 400 (Bad Request) if the denom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/denoms")
    @Timed
    public ResponseEntity<Denom> createDenom(@Valid @RequestBody Denom denom) throws URISyntaxException {
        log.debug("REST request to save Denom : {}", denom);
        if (denom.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new denom cannot already have an ID")).body(null);
        }
        Denom result = denomService.save(denom);
        return ResponseEntity.created(new URI("/api/denoms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /denoms : Updates an existing denom.
     *
     * @param denom the denom to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated denom,
     * or with status 400 (Bad Request) if the denom is not valid,
     * or with status 500 (Internal Server Error) if the denom couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/denoms")
    @Timed
    public ResponseEntity<Denom> updateDenom(@Valid @RequestBody Denom denom) throws URISyntaxException {
        log.debug("REST request to update Denom : {}", denom);
        if (denom.getId() == null) {
            return createDenom(denom);
        }
        Denom result = denomService.save(denom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, denom.getId().toString()))
            .body(result);
    }

    /**
     * GET  /denoms : get all the denoms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of denoms in body
     */
    @GetMapping("/denoms")
    @Timed
    public ResponseEntity<List<Denom>> getAllDenoms(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Denoms");
        Page<Denom> page = denomService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/denoms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /denoms/:id : get the "id" denom.
     *
     * @param id the id of the denom to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the denom, or with status 404 (Not Found)
     */
    @GetMapping("/denoms/{id}")
    @Timed
    public ResponseEntity<Denom> getDenom(@PathVariable Long id) {
        log.debug("REST request to get Denom : {}", id);
        Denom denom = denomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(denom));
    }

    /**
     * DELETE  /denoms/:id : delete the "id" denom.
     *
     * @param id the id of the denom to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/denoms/{id}")
    @Timed
    public ResponseEntity<Void> deleteDenom(@PathVariable Long id) {
        log.debug("REST request to delete Denom : {}", id);
        denomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/denoms?query=:query : search for the denom corresponding
     * to the query.
     *
     * @param query the query of the denom search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/denoms")
    @Timed
    public ResponseEntity<List<Denom>> searchDenoms(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Denoms for query {}", query);
        Page<Denom> page = denomService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/denoms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
