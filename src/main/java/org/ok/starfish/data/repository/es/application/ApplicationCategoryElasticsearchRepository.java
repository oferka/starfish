package org.ok.starfish.data.repository.es.application;

import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationCategoryElasticsearchRepository extends ElasticsearchRepository<ApplicationCategory, String> {
}
