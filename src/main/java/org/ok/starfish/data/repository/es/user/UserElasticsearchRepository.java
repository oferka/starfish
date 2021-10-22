package org.ok.starfish.data.repository.es.user;

import org.ok.starfish.model.user.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface UserElasticsearchRepository extends ElasticsearchRepository<User, String> {

    List<User> findByFirstName(String firstName);

    List<User> findByCreatedDate(ZonedDateTime createdDate);
}
