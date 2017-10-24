package com.ib.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ib.domain.LevelMember;
import com.ib.service.LevelMemberService;
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
 * REST controller for managing LevelMember.
 */
@RestController
@RequestMapping("/api")
public class LevelMemberResource {

    private final Logger log = LoggerFactory.getLogger(LevelMemberResource.class);

    private static final String ENTITY_NAME = "levelMember";

    private final LevelMemberService levelMemberService;

    public LevelMemberResource(LevelMemberService levelMemberService) {
        this.levelMemberService = levelMemberService;
    }

    /**
     * POST  /level-members : Create a new levelMember.
     *
     * @param levelMember the levelMember to create
     * @return the ResponseEntity with status 201 (Created) and with body the new levelMember, or with status 400 (Bad Request) if the levelMember has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/level-members")
    @Timed
    public ResponseEntity<LevelMember> createLevelMember(@RequestBody LevelMember levelMember) throws URISyntaxException {
        log.debug("REST request to save LevelMember : {}", levelMember);
        if (levelMember.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new levelMember cannot already have an ID")).body(null);
        }
        LevelMember result = levelMemberService.save(levelMember);
        return ResponseEntity.created(new URI("/api/level-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /level-members : Updates an existing levelMember.
     *
     * @param levelMember the levelMember to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated levelMember,
     * or with status 400 (Bad Request) if the levelMember is not valid,
     * or with status 500 (Internal Server Error) if the levelMember couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/level-members")
    @Timed
    public ResponseEntity<LevelMember> updateLevelMember(@RequestBody LevelMember levelMember) throws URISyntaxException {
        log.debug("REST request to update LevelMember : {}", levelMember);
        if (levelMember.getId() == null) {
            return createLevelMember(levelMember);
        }
        LevelMember result = levelMemberService.save(levelMember);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, levelMember.getId().toString()))
            .body(result);
    }

    /**
     * GET  /level-members : get all the levelMembers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of levelMembers in body
     */
    @GetMapping("/level-members")
    @Timed
    public ResponseEntity<List<LevelMember>> getAllLevelMembers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LevelMembers");
        Page<LevelMember> page = levelMemberService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/level-members");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /level-members/:id : get the "id" levelMember.
     *
     * @param id the id of the levelMember to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the levelMember, or with status 404 (Not Found)
     */
    @GetMapping("/level-members/{id}")
    @Timed
    public ResponseEntity<LevelMember> getLevelMember(@PathVariable Long id) {
        log.debug("REST request to get LevelMember : {}", id);
        LevelMember levelMember = levelMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(levelMember));
    }

    /**
     * DELETE  /level-members/:id : delete the "id" levelMember.
     *
     * @param id the id of the levelMember to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/level-members/{id}")
    @Timed
    public ResponseEntity<Void> deleteLevelMember(@PathVariable Long id) {
        log.debug("REST request to delete LevelMember : {}", id);
        levelMemberService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/level-members?query=:query : search for the levelMember corresponding
     * to the query.
     *
     * @param query the query of the levelMember search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/level-members")
    @Timed
    public ResponseEntity<List<LevelMember>> searchLevelMembers(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of LevelMembers for query {}", query);
        Page<LevelMember> page = levelMemberService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/level-members");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
