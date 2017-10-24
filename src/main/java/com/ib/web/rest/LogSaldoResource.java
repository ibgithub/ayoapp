package com.ib.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ib.domain.LogSaldo;
import com.ib.service.LogSaldoService;
import com.ib.web.rest.util.HeaderUtil;
import com.ib.web.rest.util.PaginationUtil;
import com.ib.service.dto.LogSaldoCriteria;
import com.ib.service.LogSaldoQueryService;
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
 * REST controller for managing LogSaldo.
 */
@RestController
@RequestMapping("/api")
public class LogSaldoResource {

    private final Logger log = LoggerFactory.getLogger(LogSaldoResource.class);

    private static final String ENTITY_NAME = "logSaldo";

    private final LogSaldoService logSaldoService;
    private final LogSaldoQueryService logSaldoQueryService;

    public LogSaldoResource(LogSaldoService logSaldoService, LogSaldoQueryService logSaldoQueryService) {
        this.logSaldoService = logSaldoService;
        this.logSaldoQueryService = logSaldoQueryService;
    }

    /**
     * POST  /log-saldos : Create a new logSaldo.
     *
     * @param logSaldo the logSaldo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new logSaldo, or with status 400 (Bad Request) if the logSaldo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/log-saldos")
    @Timed
    public ResponseEntity<LogSaldo> createLogSaldo(@RequestBody LogSaldo logSaldo) throws URISyntaxException {
        log.debug("REST request to save LogSaldo : {}", logSaldo);
        if (logSaldo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new logSaldo cannot already have an ID")).body(null);
        }
        LogSaldo result = logSaldoService.save(logSaldo);
        return ResponseEntity.created(new URI("/api/log-saldos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /log-saldos : Updates an existing logSaldo.
     *
     * @param logSaldo the logSaldo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated logSaldo,
     * or with status 400 (Bad Request) if the logSaldo is not valid,
     * or with status 500 (Internal Server Error) if the logSaldo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/log-saldos")
    @Timed
    public ResponseEntity<LogSaldo> updateLogSaldo(@RequestBody LogSaldo logSaldo) throws URISyntaxException {
        log.debug("REST request to update LogSaldo : {}", logSaldo);
        if (logSaldo.getId() == null) {
            return createLogSaldo(logSaldo);
        }
        LogSaldo result = logSaldoService.save(logSaldo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, logSaldo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /log-saldos : get all the logSaldos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of logSaldos in body
     */
    @GetMapping("/log-saldos")
    @Timed
    public ResponseEntity<List<LogSaldo>> getAllLogSaldos(LogSaldoCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get LogSaldos by criteria: {}", criteria);
        Page<LogSaldo> page = logSaldoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/log-saldos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /log-saldos/:id : get the "id" logSaldo.
     *
     * @param id the id of the logSaldo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the logSaldo, or with status 404 (Not Found)
     */
    @GetMapping("/log-saldos/{id}")
    @Timed
    public ResponseEntity<LogSaldo> getLogSaldo(@PathVariable Long id) {
        log.debug("REST request to get LogSaldo : {}", id);
        LogSaldo logSaldo = logSaldoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(logSaldo));
    }

    /**
     * DELETE  /log-saldos/:id : delete the "id" logSaldo.
     *
     * @param id the id of the logSaldo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/log-saldos/{id}")
    @Timed
    public ResponseEntity<Void> deleteLogSaldo(@PathVariable Long id) {
        log.debug("REST request to delete LogSaldo : {}", id);
        logSaldoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/log-saldos?query=:query : search for the logSaldo corresponding
     * to the query.
     *
     * @param query the query of the logSaldo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/log-saldos")
    @Timed
    public ResponseEntity<List<LogSaldo>> searchLogSaldos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of LogSaldos for query {}", query);
        Page<LogSaldo> page = logSaldoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/log-saldos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
