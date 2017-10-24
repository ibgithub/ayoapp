package com.ib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ib.domain.MemberHp;


/**
 * Spring Data JPA repository for the MemberHp entity.
 */
@Repository
public interface MemberHpRepository extends JpaRepository<MemberHp, Long> {
	List<MemberHp> findAllByIdMember(String idMember);
}
