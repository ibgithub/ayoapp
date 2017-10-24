package com.ib.repository.search;

import com.ib.domain.Kartu;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Kartu entity.
 */
public interface KartuSearchRepository extends ElasticsearchRepository<Kartu, Long> {
}
