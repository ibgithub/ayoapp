package com.ib.repository;

import com.ib.domain.TransaksiPulsa;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TransaksiPulsa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransaksiPulsaRepository extends JpaRepository<TransaksiPulsa, Long>, JpaSpecificationExecutor<TransaksiPulsa> {

}
