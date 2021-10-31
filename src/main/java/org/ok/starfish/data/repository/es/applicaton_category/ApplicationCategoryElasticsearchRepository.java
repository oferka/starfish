package org.ok.starfish.data.repository.es.applicaton_category;

import org.ok.starfish.model.applicaton_category.ApplicationCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ApplicationCategoryElasticsearchRepository extends ElasticsearchRepository<ApplicationCategory, String> {

    List<ApplicationCategory> findByName(String name);

    List<ApplicationCategory> findByCreatedDate(ZonedDateTime createdDate);
}
