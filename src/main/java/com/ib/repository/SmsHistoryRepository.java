package com.ib.repository;

import com.ib.domain.SmsHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SmsHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SmsHistoryRepository extends JpaRepository<SmsHistory, Long>, JpaSpecificationExecutor<SmsHistory> {

}
