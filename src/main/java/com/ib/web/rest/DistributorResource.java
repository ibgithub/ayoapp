package com.ib.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ib.domain.Distributor;
import com.ib.service.DistributorService;
import com.ib.web.rest.util.HeaderUtil;
import com.ib.web.rest.util.PaginationUtil;
import com.ib.service.dto.DistributorCriteria;
import com.ib.service.DistributorQueryService;
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
 * REST controller for managing Distributor.
 */
@RestController
@RequestMapping("/api")
public class DistributorResource {

    private final Logger log = LoggerFactory.getLogger(DistributorResource.class);

    private static final String ENTITY_NAME = "distributor";

    private final DistributorService distributorService;
    private final DistributorQueryService distributorQueryService;

    public DistributorResource(DistributorService distributorService, DistributorQueryService distributorQueryService) {
        this.distributorService = distributorService;
        this.distributorQueryService = distributorQueryService;
    }

    /**
     * POST  /distributors : Create a new distributor.
     *
     * @param distributor the distributor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new distributor, or with status 400 (Bad Request) if the distributor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/distributors")
    @Timed
    public ResponseEntity<Distributor> createDistributor(@RequestBody Distributor distributor) throws URISyntaxException {
        log.debug("REST request to save Distributor : {}", distributor);
        if (distributor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new distributor cannot already have an ID")).body(null);
        }
        Distributor result = distributorService.save(distributor);
        return ResponseEntity.created(new URI("/api/distributors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /distributors : Updates an existing distributor.
     *
     * @param distributor the distributor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated distributor,
     * or with status 400 (Bad Request) if the distributor is not valid,
     * or with status 500 (Internal Server Error) if the distributor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/distributors")
    @Timed
    public ResponseEntity<Distributor> updateDistributor(@RequestBody Distributor distributor) throws URISyntaxException {
        log.debug("REST request to update Distributor : {}", distributor);
        if (distributor.getId() == null) {
            return createDistributor(distributor);
        }
        Distributor result = distributorService.save(distributor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, distributor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /distributors : get all the distributors.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of distributors in body
     */
    @GetMapping("/distributors")
    @Timed
    public ResponseEntity<List<Distributor>> getAllDistributors(DistributorCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get Distributors by criteria: {}", criteria);
        Page<Distributor> page = distributorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/distributors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /distributors/:id : get the "id" distributor.
     *
     * @param id the id of the distributor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the distributor, or with status 404 (Not Found)
     */
    @GetMapping("/distributors/{id}")
    @Timed
    public ResponseEntity<Distributor> getDistributor(@PathVariable Long id) {
        log.debug("REST request to get Distributor : {}", id);
        Distributor distributor = distributorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(distributor));
    }

    /**
     * DELETE  /distributors/:id : delete the "id" distributor.
     *
     * @param id the id of the distributor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/distributors/{id}")
    @Timed
    public ResponseEntity<Void> deleteDistributor(@PathVariable Long id) {
        log.debug("REST request to delete Distributor : {}", id);
        distributorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/distributors?query=:query : search for the distributor corresponding
     * to the query.
     *
     * @param query the query of the distributor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/distributors")
    @Timed
    public ResponseEntity<List<Distributor>> searchDistributors(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Distributors for query {}", query);
        Page<Distributor> page = distributorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/distributors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
