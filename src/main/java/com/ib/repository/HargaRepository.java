package com.ib.repository;

import com.ib.domain.Harga;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Harga entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HargaRepository extends JpaRepository<Harga, Long>, JpaSpecificationExecutor<Harga> {

}
