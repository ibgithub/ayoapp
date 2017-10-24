package com.ib.repository;

import com.ib.domain.Rebate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Rebate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RebateRepository extends JpaRepository<Rebate, Long>, JpaSpecificationExecutor<Rebate> {

}
