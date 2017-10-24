package com.ib.repository;

import com.ib.domain.Transaksi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Transaksi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, Long>, JpaSpecificationExecutor<Transaksi> {

}
