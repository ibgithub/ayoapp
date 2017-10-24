package com.ib.repository.search;

import com.ib.domain.TransaksiPulsa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TransaksiPulsa entity.
 */
public interface TransaksiPulsaSearchRepository extends ElasticsearchRepository<TransaksiPulsa, Long> {
}
