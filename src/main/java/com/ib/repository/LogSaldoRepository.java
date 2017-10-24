package com.ib.repository;

import com.ib.domain.LogSaldo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LogSaldo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogSaldoRepository extends JpaRepository<LogSaldo, Long>, JpaSpecificationExecutor<LogSaldo> {

}
