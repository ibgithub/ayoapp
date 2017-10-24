package com.ib.repository;

import com.ib.domain.Koneksi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Koneksi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KoneksiRepository extends JpaRepository<Koneksi, Long> {

}
