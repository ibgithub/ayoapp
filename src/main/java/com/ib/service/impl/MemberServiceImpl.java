package com.ib.service.impl;

import com.ib.service.MemberService;
import com.ib.domain.Member;
import com.ib.repository.MemberRepository;
import com.ib.repository.search.MemberSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.Optional;

/**
 * Service Implementation for managing Member.
 */
@Service
@Transactional
public class MemberServiceImpl implements MemberService{

    private final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MemberRepository memberRepository;

    private final MemberSearchRepository memberSearchRepository;
    public MemberServiceImpl(MemberRepository memberRepository, MemberSearchRepository memberSearchRepository) {
        this.memberRepository = memberRepository;
        this.memberSearchRepository = memberSearchRepository;
    }

    /**
     * Save a member.
     *
     * @param member the entity to save
     * @return the persisted entity
     */
    @Override
    public Member save(Member member) {
        log.debug("Request to save Member : {}", member);
        Member result = memberRepository.save(member);
        memberSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the members.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Member> findAll(Pageable pageable) {
        log.debug("Request to get all Members");
        return memberRepository.findAll(pageable);
    }

    /**
     *  Get one member by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Member findOne(Long id) {
        log.debug("Request to get Member : {}", id);
        return memberRepository.findOne(id);
    }

	@Override
	@Transactional(readOnly = true)
    public Optional<Member> findOneByIdMember(String idMember) {
        log.debug("Request to get Member : {}", idMember);
		return memberRepository.findOneByIdMember(idMember);
	}
	
    /**
     *  Delete the  member by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Member : {}", id);
        memberRepository.delete(id);
        memberSearchRepository.delete(id);
    }

    /**
     * Search for the member corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Member> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Members for query {}", query);
        Page<Member> result = memberSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

}
