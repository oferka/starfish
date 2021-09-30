package org.ok.starfish.data.repository.es.account;

import org.ok.starfish.model.account.Account;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public interface AccountElasticsearchRepository extends ElasticsearchRepository<Account, String> {

    Optional<Account> findByName(String name);

    Optional<Account> findByCreatedDate(ZonedDateTime createdDate);
}
