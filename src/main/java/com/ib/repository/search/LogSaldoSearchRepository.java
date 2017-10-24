package com.ib.repository.search;

import com.ib.domain.LogSaldo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LogSaldo entity.
 */
public interface LogSaldoSearchRepository extends ElasticsearchRepository<LogSaldo, Long> {
}
