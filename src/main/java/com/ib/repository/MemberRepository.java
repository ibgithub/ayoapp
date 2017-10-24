package com.ib.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ib.domain.Member;


/**
 * Spring Data JPA repository for the Member entity.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findOneByIdMember(String idMember);
}
