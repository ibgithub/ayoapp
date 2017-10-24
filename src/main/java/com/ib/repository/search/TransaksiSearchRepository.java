package com.ib.repository.search;

import com.ib.domain.Transaksi;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Transaksi entity.
 */
public interface TransaksiSearchRepository extends ElasticsearchRepository<Transaksi, Long> {
}
