package com.ib.repository.search;

import com.ib.domain.SmsHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SmsHistory entity.
 */
public interface SmsHistorySearchRepository extends ElasticsearchRepository<SmsHistory, Long> {
}
