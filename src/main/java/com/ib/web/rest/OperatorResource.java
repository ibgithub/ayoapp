package com.ib.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ib.domain.Operator;
import com.ib.service.OperatorService;
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
 * REST controller for managing Operator.
 */
@RestController
@RequestMapping("/api")
public class OperatorResource {

    private final Logger log = LoggerFactory.getLogger(OperatorResource.class);

    private static final String ENTITY_NAME = "operator";

    private final OperatorService operatorService;

    public OperatorResource(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    /**
     * POST  /operators : Create a new operator.
     *
     * @param operator the operator to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operator, or with status 400 (Bad Request) if the operator has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operators")
    @Timed
    public ResponseEntity<Operator> createOperator(@RequestBody Operator operator) throws URISyntaxException {
        log.debug("REST request to save Operator : {}", operator);
        if (operator.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new operator cannot already have an ID")).body(null);
        }
        Operator result = operatorService.save(operator);
        return ResponseEntity.created(new URI("/api/operators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operators : Updates an existing operator.
     *
     * @param operator the operator to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operator,
     * or with status 400 (Bad Request) if the operator is not valid,
     * or with status 500 (Internal Server Error) if the operator couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operators")
    @Timed
    public ResponseEntity<Operator> updateOperator(@RequestBody Operator operator) throws URISyntaxException {
        log.debug("REST request to update Operator : {}", operator);
        if (operator.getId() == null) {
            return createOperator(operator);
        }
        Operator result = operatorService.save(operator);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operator.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operators : get all the operators.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of operators in body
     */
    @GetMapping("/operators")
    @Timed
    public ResponseEntity<List<Operator>> getAllOperators(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Operators");
        Page<Operator> page = operatorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operators");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /operators/:id : get the "id" operator.
     *
     * @param id the id of the operator to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operator, or with status 404 (Not Found)
     */
    @GetMapping("/operators/{id}")
    @Timed
    public ResponseEntity<Operator> getOperator(@PathVariable Long id) {
        log.debug("REST request to get Operator : {}", id);
        Operator operator = operatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(operator));
    }

    /**
     * DELETE  /operators/:id : delete the "id" operator.
     *
     * @param id the id of the operator to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operators/{id}")
    @Timed
    public ResponseEntity<Void> deleteOperator(@PathVariable Long id) {
        log.debug("REST request to delete Operator : {}", id);
        operatorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/operators?query=:query : search for the operator corresponding
     * to the query.
     *
     * @param query the query of the operator search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/operators")
    @Timed
    public ResponseEntity<List<Operator>> searchOperators(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Operators for query {}", query);
        Page<Operator> page = operatorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/operators");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
