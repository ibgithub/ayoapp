package com.ib.service;

import com.ib.domain.LevelMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing LevelMember.
 */
public interface LevelMemberService {

    /**
     * Save a levelMember.
     *
     * @param levelMember the entity to save
     * @return the persisted entity
     */
    LevelMember save(LevelMember levelMember);

    /**
     *  Get all the levelMembers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LevelMember> findAll(Pageable pageable);

    /**
     *  Get the "id" levelMember.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LevelMember findOne(Long id);

    /**
     *  Delete the "id" levelMember.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the levelMember corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LevelMember> search(String query, Pageable pageable);
}
