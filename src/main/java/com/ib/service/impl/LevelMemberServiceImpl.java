package com.ib.service.impl;

import com.ib.service.LevelMemberService;
import com.ib.domain.LevelMember;
import com.ib.repository.LevelMemberRepository;
import com.ib.repository.search.LevelMemberSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing LevelMember.
 */
@Service
@Transactional
public class LevelMemberServiceImpl implements LevelMemberService{

    private final Logger log = LoggerFactory.getLogger(LevelMemberServiceImpl.class);

    private final LevelMemberRepository levelMemberRepository;

    private final LevelMemberSearchRepository levelMemberSearchRepository;
    public LevelMemberServiceImpl(LevelMemberRepository levelMemberRepository, LevelMemberSearchRepository levelMemberSearchRepository) {
        this.levelMemberRepository = levelMemberRepository;
        this.levelMemberSearchRepository = levelMemberSearchRepository;
    }

    /**
     * Save a levelMember.
     *
     * @param levelMember the entity to save
     * @return the persisted entity
     */
    @Override
    public LevelMember save(LevelMember levelMember) {
        log.debug("Request to save LevelMember : {}", levelMember);
        LevelMember result = levelMemberRepository.save(levelMember);
        levelMemberSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the levelMembers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LevelMember> findAll(Pageable pageable) {
        log.debug("Request to get all LevelMembers");
        return levelMemberRepository.findAll(pageable);
    }

    /**
     *  Get one levelMember by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LevelMember findOne(Long id) {
        log.debug("Request to get LevelMember : {}", id);
        return levelMemberRepository.findOne(id);
    }

    /**
     *  Delete the  levelMember by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LevelMember : {}", id);
        levelMemberRepository.delete(id);
        levelMemberSearchRepository.delete(id);
    }

    /**
     * Search for the levelMember corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LevelMember> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LevelMembers for query {}", query);
        Page<LevelMember> result = levelMemberSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
