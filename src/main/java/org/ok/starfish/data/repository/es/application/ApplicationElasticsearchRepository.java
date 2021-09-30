package org.ok.starfish.data.repository.es.application;

import org.ok.starfish.model.application.Application;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ApplicationElasticsearchRepository extends ElasticsearchRepository<Application, String> {

    List<Application> findByName(String name);

    List<Application> findByCreatedDate(ZonedDateTime createdDate);
}
