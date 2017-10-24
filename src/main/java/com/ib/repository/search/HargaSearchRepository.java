package com.ib.repository.search;

import com.ib.domain.Harga;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Harga entity.
 */
public interface HargaSearchRepository extends ElasticsearchRepository<Harga, Long> {
}
