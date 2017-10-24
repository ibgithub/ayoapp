package com.ib.repository.search;

import com.ib.domain.Produk;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Produk entity.
 */
public interface ProdukSearchRepository extends ElasticsearchRepository<Produk, Long> {
}
