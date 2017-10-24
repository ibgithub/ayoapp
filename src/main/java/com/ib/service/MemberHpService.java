package com.ib.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ib.domain.MemberHp;

/**
 * Service Interface for managing MemberHp.
 */
public interface MemberHpService {

    /**
     * Save a memberHp.
     *
     * @param memberHp the entity to save
     * @return the persisted entity
     */
    MemberHp save(MemberHp memberHp);

    /**
     *  Get all the memberHps.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MemberHp> findAll(Pageable pageable);

    /**
     *  Get the "id" memberHp.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MemberHp findOne(Long id);

    List<MemberHp> findAllByIdMember(String idMember);
    
    /**
     *  Delete the "id" memberHp.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the memberHp corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MemberHp> search(String query, Pageable pageable);
}
