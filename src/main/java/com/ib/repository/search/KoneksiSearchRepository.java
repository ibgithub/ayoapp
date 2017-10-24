package com.ib.repository.search;

import com.ib.domain.Koneksi;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Koneksi entity.
 */
public interface KoneksiSearchRepository extends ElasticsearchRepository<Koneksi, Long> {
}
