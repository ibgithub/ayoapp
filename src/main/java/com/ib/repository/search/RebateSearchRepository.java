package com.ib.repository.search;

import com.ib.domain.Rebate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Rebate entity.
 */
public interface RebateSearchRepository extends ElasticsearchRepository<Rebate, Long> {
}
