package com.ib.repository;

import com.ib.domain.Produk;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Produk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdukRepository extends JpaRepository<Produk, Long>, JpaSpecificationExecutor<Produk> {

}
