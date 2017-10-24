package com.ib.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ib.domain.SmsHistory;
import com.ib.service.SmsHistoryService;
import com.ib.web.rest.util.HeaderUtil;
import com.ib.web.rest.util.PaginationUtil;
import com.ib.service.dto.SmsHistoryCriteria;
import com.ib.service.SmsHistoryQueryService;
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
 * REST controller for managing SmsHistory.
 */
@RestController
@RequestMapping("/api")
public class SmsHistoryResource {

    private final Logger log = LoggerFactory.getLogger(SmsHistoryResource.class);

    private static final String ENTITY_NAME = "smsHistory";

    private final SmsHistoryService smsHistoryService;
    private final SmsHistoryQueryService smsHistoryQueryService;

    public SmsHistoryResource(SmsHistoryService smsHistoryService, SmsHistoryQueryService smsHistoryQueryService) {
        this.smsHistoryService = smsHistoryService;
        this.smsHistoryQueryService = smsHistoryQueryService;
    }

    /**
     * POST  /sms-histories : Create a new smsHistory.
     *
     * @param smsHistory the smsHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new smsHistory, or with status 400 (Bad Request) if the smsHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sms-histories")
    @Timed
    public ResponseEntity<SmsHistory> createSmsHistory(@RequestBody SmsHistory smsHistory) throws URISyntaxException {
        log.debug("REST request to save SmsHistory : {}", smsHistory);
        if (smsHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new smsHistory cannot already have an ID")).body(null);
        }
        SmsHistory result = smsHistoryService.save(smsHistory);
        return ResponseEntity.created(new URI("/api/sms-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sms-histories : Updates an existing smsHistory.
     *
     * @param smsHistory the smsHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated smsHistory,
     * or with status 400 (Bad Request) if the smsHistory is not valid,
     * or with status 500 (Internal Server Error) if the smsHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sms-histories")
    @Timed
    public ResponseEntity<SmsHistory> updateSmsHistory(@RequestBody SmsHistory smsHistory) throws URISyntaxException {
        log.debug("REST request to update SmsHistory : {}", smsHistory);
        if (smsHistory.getId() == null) {
            return createSmsHistory(smsHistory);
        }
        SmsHistory result = smsHistoryService.save(smsHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, smsHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sms-histories : get all the smsHistories.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of smsHistories in body
     */
    @GetMapping("/sms-histories")
    @Timed
    public ResponseEntity<List<SmsHistory>> getAllSmsHistories(SmsHistoryCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get SmsHistories by criteria: {}", criteria);
        Page<SmsHistory> page = smsHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sms-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sms-histories/:id : get the "id" smsHistory.
     *
     * @param id the id of the smsHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the smsHistory, or with status 404 (Not Found)
     */
    @GetMapping("/sms-histories/{id}")
    @Timed
    public ResponseEntity<SmsHistory> getSmsHistory(@PathVariable Long id) {
        log.debug("REST request to get SmsHistory : {}", id);
        SmsHistory smsHistory = smsHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(smsHistory));
    }

    /**
     * DELETE  /sms-histories/:id : delete the "id" smsHistory.
     *
     * @param id the id of the smsHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sms-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteSmsHistory(@PathVariable Long id) {
        log.debug("REST request to delete SmsHistory : {}", id);
        smsHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sms-histories?query=:query : search for the smsHistory corresponding
     * to the query.
     *
     * @param query the query of the smsHistory search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/sms-histories")
    @Timed
    public ResponseEntity<List<SmsHistory>> searchSmsHistories(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of SmsHistories for query {}", query);
        Page<SmsHistory> page = smsHistoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sms-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
