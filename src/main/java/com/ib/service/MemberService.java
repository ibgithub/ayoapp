package com.ib.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ib.domain.Member;

/**
 * Service Interface for managing Member.
 */
public interface MemberService {

    /**
     * Save a member.
     *
     * @param member the entity to save
     * @return the persisted entity
     */
    Member save(Member member);

    /**
     *  Get all the members.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Member> findAll(Pageable pageable);

    /**
     *  Get the "id" member.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Member findOne(Long id);
    
    Optional<Member> findOneByIdMember(String idMember);

    /**
     *  Delete the "id" member.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the member corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Member> search(String query, Pageable pageable);
}
