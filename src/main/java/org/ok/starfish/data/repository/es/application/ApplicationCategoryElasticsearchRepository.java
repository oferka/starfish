package org.ok.starfish.data.repository.es.application;

import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ApplicationCategoryElasticsearchRepository extends ElasticsearchRepository<ApplicationCategory, String> {

    List<ApplicationCategory> findByName(String name);

    List<ApplicationCategory> findByCreatedDate(ZonedDateTime createdDate);
}
