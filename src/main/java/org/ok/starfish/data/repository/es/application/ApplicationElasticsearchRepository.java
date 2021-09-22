package org.ok.starfish.data.repository.es.application;

import org.ok.starfish.model.application.Application;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationElasticsearchRepository extends ElasticsearchRepository<Application, String> {

    Optional<Application> findByName(String name);
}
