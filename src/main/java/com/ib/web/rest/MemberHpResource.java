package com.ib.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ib.domain.MemberHp;
import com.ib.service.MemberHpService;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MemberHp.
 */
@RestController
@RequestMapping("/api")
public class MemberHpResource {

    private final Logger log = LoggerFactory.getLogger(MemberHpResource.class);

    private static final String ENTITY_NAME = "memberHp";

    private final MemberHpService memberHpService;

    public MemberHpResource(MemberHpService memberHpService) {
        this.memberHpService = memberHpService;
    }

    /**
     * POST  /member-hps : Create a new memberHp.
     *
     * @param memberHp the memberHp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new memberHp, or with status 400 (Bad Request) if the memberHp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/member-hps")
    @Timed
    public ResponseEntity<MemberHp> createMemberHp(@Valid @RequestBody MemberHp memberHp) throws URISyntaxException {
        log.debug("REST request to save MemberHp : {}", memberHp);
        if (memberHp.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new memberHp cannot already have an ID")).body(null);
        }
        MemberHp result = memberHpService.save(memberHp);
        
        return ResponseEntity.created(new URI("/api/member-hps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    /**
     * POST  /add-hp
     *
     * @param memberHp the memberHp to create
     * @return the ResponseEntity with status 200 (OK) and with body the memberHp, or with status 404 (Not Found)
     */
    @PostMapping("/add-hp")
    @Timed
    public ResponseEntity<MemberHp> addHp(@Valid @RequestBody MemberHp memberHp) throws URISyntaxException {
        log.debug("REST request to save MemberHp : {}", memberHp);
        if (memberHp.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new memberHp cannot already have an ID")).body(null);
        }
        MemberHp result = memberHpService.save(memberHp);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
    
    /**
     * PUT  /member-hps : Updates an existing memberHp.
     *
     * @param memberHp the memberHp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated memberHp,
     * or with status 400 (Bad Request) if the memberHp is not valid,
     * or with status 500 (Internal Server Error) if the memberHp couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/member-hps")
    @Timed
    public ResponseEntity<MemberHp> updateMemberHp(@Valid @RequestBody MemberHp memberHp) throws URISyntaxException {
        log.debug("REST request to update MemberHp : {}", memberHp);
        if (memberHp.getId() == null) {
            return createMemberHp(memberHp);
        }
        MemberHp result = memberHpService.save(memberHp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, memberHp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /member-hps : get all the memberHps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of memberHps in body
     */
    @GetMapping("/member-hps")
    @Timed
    public ResponseEntity<List<MemberHp>> getAllMemberHps(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MemberHps");
        Page<MemberHp> page = memberHpService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/member-hps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /member-hps/:id : get the "id" memberHp.
     *
     * @param id the id of the memberHp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the memberHp, or with status 404 (Not Found)
     */
    @GetMapping("/member-hps/{id}")
    @Timed
    public ResponseEntity<MemberHp> getMemberHp(@PathVariable Long id) {
        log.debug("REST request to get MemberHp : {}", id);
        MemberHp memberHp = memberHpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(memberHp));
    }

    /**
     * GET  /member-hpsByIdMember/:idMember : get the "idMember" memberHp.
     *
     * @param idMember the idMember of the memberHp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the memberHp, or with status 404 (Not Found)
     */
    @GetMapping("/memberHpsByIdMember/{idMember}")
    @Timed
    public ResponseEntity<List<MemberHp>> getMemberHpsByIdMember(@PathVariable String idMember) {
    	idMember = idMember.toUpperCase();
        log.debug("REST request to get MemberHp : {}", idMember);
        List<MemberHp> memberHps = memberHpService.findAllByIdMember(idMember);
        return new ResponseEntity<>(memberHps, HttpStatus.OK);
    }
    /**
     * DELETE  /member-hps/:id : delete the "id" memberHp.
     *
     * @param id the id of the memberHp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/member-hps/{id}")
    @Timed
    public ResponseEntity<Void> deleteMemberHp(@PathVariable Long id) {
        log.debug("REST request to delete MemberHp : {}", id);
        memberHpService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/member-hps?query=:query : search for the memberHp corresponding
     * to the query.
     *
     * @param query the query of the memberHp search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/member-hps")
    @Timed
    public ResponseEntity<List<MemberHp>> searchMemberHps(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MemberHps for query {}", query);
        Page<MemberHp> page = memberHpService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/member-hps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
