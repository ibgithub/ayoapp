package com.ib.repository.search;

import com.ib.domain.MemberHp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MemberHp entity.
 */
public interface MemberHpSearchRepository extends ElasticsearchRepository<MemberHp, Long> {
}
