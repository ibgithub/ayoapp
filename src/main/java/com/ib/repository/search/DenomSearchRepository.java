package com.ib.repository.search;

import com.ib.domain.Denom;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Denom entity.
 */
public interface DenomSearchRepository extends ElasticsearchRepository<Denom, Long> {
}
