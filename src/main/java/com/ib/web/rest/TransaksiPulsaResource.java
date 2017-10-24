package com.ib.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ib.domain.TransaksiPulsa;
import com.ib.service.TransaksiPulsaService;
import com.ib.web.rest.util.HeaderUtil;
import com.ib.web.rest.util.PaginationUtil;
import com.ib.service.dto.TransaksiPulsaCriteria;
import com.ib.service.TransaksiPulsaQueryService;
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
 * REST controller for managing TransaksiPulsa.
 */
@RestController
@RequestMapping("/api")
public class TransaksiPulsaResource {

    private final Logger log = LoggerFactory.getLogger(TransaksiPulsaResource.class);

    private static final String ENTITY_NAME = "transaksiPulsa";

    private final TransaksiPulsaService transaksiPulsaService;
    private final TransaksiPulsaQueryService transaksiPulsaQueryService;

    public TransaksiPulsaResource(TransaksiPulsaService transaksiPulsaService, TransaksiPulsaQueryService transaksiPulsaQueryService) {
        this.transaksiPulsaService = transaksiPulsaService;
        this.transaksiPulsaQueryService = transaksiPulsaQueryService;
    }

    /**
     * POST  /transaksi-pulsas : Create a new transaksiPulsa.
     *
     * @param transaksiPulsa the transaksiPulsa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transaksiPulsa, or with status 400 (Bad Request) if the transaksiPulsa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transaksi-pulsas")
    @Timed
    public ResponseEntity<TransaksiPulsa> createTransaksiPulsa(@RequestBody TransaksiPulsa transaksiPulsa) throws URISyntaxException {
        log.debug("REST request to save TransaksiPulsa : {}", transaksiPulsa);
        if (transaksiPulsa.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new transaksiPulsa cannot already have an ID")).body(null);
        }
        TransaksiPulsa result = transaksiPulsaService.save(transaksiPulsa);
        return ResponseEntity.created(new URI("/api/transaksi-pulsas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transaksi-pulsas : Updates an existing transaksiPulsa.
     *
     * @param transaksiPulsa the transaksiPulsa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transaksiPulsa,
     * or with status 400 (Bad Request) if the transaksiPulsa is not valid,
     * or with status 500 (Internal Server Error) if the transaksiPulsa couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transaksi-pulsas")
    @Timed
    public ResponseEntity<TransaksiPulsa> updateTransaksiPulsa(@RequestBody TransaksiPulsa transaksiPulsa) throws URISyntaxException {
        log.debug("REST request to update TransaksiPulsa : {}", transaksiPulsa);
        if (transaksiPulsa.getId() == null) {
            return createTransaksiPulsa(transaksiPulsa);
        }
        TransaksiPulsa result = transaksiPulsaService.save(transaksiPulsa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transaksiPulsa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transaksi-pulsas : get all the transaksiPulsas.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of transaksiPulsas in body
     */
    @GetMapping("/transaksi-pulsas")
    @Timed
    public ResponseEntity<List<TransaksiPulsa>> getAllTransaksiPulsas(TransaksiPulsaCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get TransaksiPulsas by criteria: {}", criteria);
        Page<TransaksiPulsa> page = transaksiPulsaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/transaksi-pulsas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /transaksi-pulsas/:id : get the "id" transaksiPulsa.
     *
     * @param id the id of the transaksiPulsa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transaksiPulsa, or with status 404 (Not Found)
     */
    @GetMapping("/transaksi-pulsas/{id}")
    @Timed
    public ResponseEntity<TransaksiPulsa> getTransaksiPulsa(@PathVariable Long id) {
        log.debug("REST request to get TransaksiPulsa : {}", id);
        TransaksiPulsa transaksiPulsa = transaksiPulsaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transaksiPulsa));
    }

    /**
     * DELETE  /transaksi-pulsas/:id : delete the "id" transaksiPulsa.
     *
     * @param id the id of the transaksiPulsa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transaksi-pulsas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransaksiPulsa(@PathVariable Long id) {
        log.debug("REST request to delete TransaksiPulsa : {}", id);
        transaksiPulsaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/transaksi-pulsas?query=:query : search for the transaksiPulsa corresponding
     * to the query.
     *
     * @param query the query of the transaksiPulsa search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/transaksi-pulsas")
    @Timed
    public ResponseEntity<List<TransaksiPulsa>> searchTransaksiPulsas(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of TransaksiPulsas for query {}", query);
        Page<TransaksiPulsa> page = transaksiPulsaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/transaksi-pulsas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
