package com.ib.repository;

import com.ib.domain.LevelMember;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LevelMember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LevelMemberRepository extends JpaRepository<LevelMember, Long> {

}
