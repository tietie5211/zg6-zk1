package com.wjs.repository;

import com.wjs.entity.BlogEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * description
 *
 * @author wangjunshan
 * @date 2023/11/16 10:10
 */
@Repository
public interface BlogRepository extends ElasticsearchRepository<BlogEntity,Integer> {
}
