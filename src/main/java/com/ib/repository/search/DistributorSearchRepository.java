package com.ib.repository.search;

import com.ib.domain.Distributor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Distributor entity.
 */
public interface DistributorSearchRepository extends ElasticsearchRepository<Distributor, Long> {
}
