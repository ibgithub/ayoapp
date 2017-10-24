package com.ib.service.impl;

import com.ib.service.MemberHpService;
import com.ib.domain.MemberHp;
import com.ib.repository.MemberHpRepository;
import com.ib.repository.search.MemberHpSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.List;

/**
 * Service Implementation for managing MemberHp.
 */
@Service
@Transactional
public class MemberHpServiceImpl implements MemberHpService{

    private final Logger log = LoggerFactory.getLogger(MemberHpServiceImpl.class);

    private final MemberHpRepository memberHpRepository;

    private final MemberHpSearchRepository memberHpSearchRepository;
    public MemberHpServiceImpl(MemberHpRepository memberHpRepository, MemberHpSearchRepository memberHpSearchRepository) {
        this.memberHpRepository = memberHpRepository;
        this.memberHpSearchRepository = memberHpSearchRepository;
    }

    /**
     * Save a memberHp.
     *
     * @param memberHp the entity to save
     * @return the persisted entity
     */
    @Override
    public MemberHp save(MemberHp memberHp) {
        log.debug("Request to save MemberHp : {}", memberHp);
        MemberHp result = memberHpRepository.save(memberHp);
        memberHpSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the memberHps.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MemberHp> findAll(Pageable pageable) {
        log.debug("Request to get all MemberHps");
        return memberHpRepository.findAll(pageable);
    }

    /**
     *  Get one memberHp by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MemberHp findOne(Long id) {
        log.debug("Request to get MemberHp : {}", id);
        return memberHpRepository.findOne(id);
    }

	@Override
	public List<MemberHp> findAllByIdMember(String idMember) {
        log.debug("Request to get MemberHp : {}", idMember);
        return memberHpRepository.findAllByIdMember(idMember);
	}
	
    /**
     *  Delete the  memberHp by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MemberHp : {}", id);
        memberHpRepository.delete(id);
        memberHpSearchRepository.delete(id);
    }

    /**
     * Search for the memberHp corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MemberHp> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MemberHps for query {}", query);
        Page<MemberHp> result = memberHpSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

}
