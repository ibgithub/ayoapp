package com.ib.repository;

import com.ib.domain.Denom;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Denom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DenomRepository extends JpaRepository<Denom, Long> {

}
