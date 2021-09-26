package org.ok.starfish.data.repository.es.user;

import org.ok.starfish.model.user.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserElasticsearchRepository extends ElasticsearchRepository<User, String> {

    Optional<User> findByName(String name);
}
