package com.ib.repository.search;

import com.ib.domain.LevelMember;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LevelMember entity.
 */
public interface LevelMemberSearchRepository extends ElasticsearchRepository<LevelMember, Long> {
}
