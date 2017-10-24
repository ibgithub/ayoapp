package com.ib.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ib.domain.Rebate;
import com.ib.service.RebateService;
import com.ib.web.rest.util.HeaderUtil;
import com.ib.web.rest.util.PaginationUtil;
import com.ib.service.dto.RebateCriteria;
import com.ib.service.RebateQueryService;
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
 * REST controller for managing Rebate.
 */
@RestController
@RequestMapping("/api")
public class RebateResource {

    private final Logger log = LoggerFactory.getLogger(RebateResource.class);

    private static final String ENTITY_NAME = "rebate";

    private final RebateService rebateService;
    private final RebateQueryService rebateQueryService;

    public RebateResource(RebateService rebateService, RebateQueryService rebateQueryService) {
        this.rebateService = rebateService;
        this.rebateQueryService = rebateQueryService;
    }

    /**
     * POST  /rebates : Create a new rebate.
     *
     * @param rebate the rebate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rebate, or with status 400 (Bad Request) if the rebate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rebates")
    @Timed
    public ResponseEntity<Rebate> createRebate(@RequestBody Rebate rebate) throws URISyntaxException {
        log.debug("REST request to save Rebate : {}", rebate);
        if (rebate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rebate cannot already have an ID")).body(null);
        }
        Rebate result = rebateService.save(rebate);
        return ResponseEntity.created(new URI("/api/rebates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rebates : Updates an existing rebate.
     *
     * @param rebate the rebate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rebate,
     * or with status 400 (Bad Request) if the rebate is not valid,
     * or with status 500 (Internal Server Error) if the rebate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rebates")
    @Timed
    public ResponseEntity<Rebate> updateRebate(@RequestBody Rebate rebate) throws URISyntaxException {
        log.debug("REST request to update Rebate : {}", rebate);
        if (rebate.getId() == null) {
            return createRebate(rebate);
        }
        Rebate result = rebateService.save(rebate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rebate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rebates : get all the rebates.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of rebates in body
     */
    @GetMapping("/rebates")
    @Timed
    public ResponseEntity<List<Rebate>> getAllRebates(RebateCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get Rebates by criteria: {}", criteria);
        Page<Rebate> page = rebateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rebates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rebates/:id : get the "id" rebate.
     *
     * @param id the id of the rebate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rebate, or with status 404 (Not Found)
     */
    @GetMapping("/rebates/{id}")
    @Timed
    public ResponseEntity<Rebate> getRebate(@PathVariable Long id) {
        log.debug("REST request to get Rebate : {}", id);
        Rebate rebate = rebateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rebate));
    }

    /**
     * DELETE  /rebates/:id : delete the "id" rebate.
     *
     * @param id the id of the rebate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rebates/{id}")
    @Timed
    public ResponseEntity<Void> deleteRebate(@PathVariable Long id) {
        log.debug("REST request to delete Rebate : {}", id);
        rebateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/rebates?query=:query : search for the rebate corresponding
     * to the query.
     *
     * @param query the query of the rebate search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/rebates")
    @Timed
    public ResponseEntity<List<Rebate>> searchRebates(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Rebates for query {}", query);
        Page<Rebate> page = rebateService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/rebates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
