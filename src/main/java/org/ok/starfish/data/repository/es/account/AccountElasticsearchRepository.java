package org.ok.starfish.data.repository.es.account;

import org.ok.starfish.model.account.Account;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface AccountElasticsearchRepository extends ElasticsearchRepository<Account, String> {

    List<Account> findByName(String name);

    List<Account> findBySector(String sector);

    List<Account> findByCreatedDate(ZonedDateTime createdDate);
}
