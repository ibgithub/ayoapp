package com.ib.repository;

import com.ib.domain.Kartu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Kartu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KartuRepository extends JpaRepository<Kartu, Long>, JpaSpecificationExecutor<Kartu> {

}
