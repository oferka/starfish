package org.ok.starfish.data.service.account;

import org.apache.commons.lang3.RandomUtils;
import org.ok.starfish.data.repository.es.account.AccountElasticsearchRepository;
import org.ok.starfish.model.account.Account;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountElasticsearchRepository accountElasticsearchRepository;

    public AccountServiceImpl(AccountElasticsearchRepository accountElasticsearchRepository) {
        this.accountElasticsearchRepository = accountElasticsearchRepository;
    }

    @Override
    public @NotNull List<Account> findAll() {
        Iterable<Account> items = accountElasticsearchRepository.findAll();
        return StreamSupport
                .stream(items.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public @NotNull Optional<Account> findById(@NotNull String id) {
        return accountElasticsearchRepository.findById(id);
    }

    @Override
    public @NotNull List<Account> findByName(@NotNull String name) {
        return accountElasticsearchRepository.findByName(name);
    }

    @Override
    public List<Account> findByCreatedDate(ZonedDateTime createdDate) {
        return accountElasticsearchRepository.findByCreatedDate(createdDate);
    }

    @Override
    public Optional<Account> findRandom() {
        List<Account> items = findAll();
        if(items.isEmpty()) {
            return Optional.empty();
        }
        Account item = items.get(RandomUtils.nextInt(0, items.size()));
        return Optional.of(item);
    }

    @Override
    public @NotNull Account save(@NotNull Account account) {
        return accountElasticsearchRepository.save(account);
    }

    @Override
    public @NotNull Iterable<Account> saveAll(@NotNull Iterable<Account> accounts) {
        return accountElasticsearchRepository.saveAll(accounts);
    }

    @Override
    public Optional<Account> update(@NotNull String id, @NotNull Account account) {
        Optional<Account> result = Optional.empty();
        if(existsById(id)) {
            result = Optional.of(save(account));
        }
        return result;
    }

    @Override
    public void deleteById(@NotNull String id) {
        accountElasticsearchRepository.deleteById(id);
    }

    @Override
    public long count() {
        return accountElasticsearchRepository.count();
    }

    @Override
    public boolean existsById(@NotNull String id) {
        return accountElasticsearchRepository.existsById(id);
    }

    @Override
    public boolean exists(@NotNull @NotNull Account account) {
        List<Account> accounts = accountElasticsearchRepository.findByName(account.getName());
        return (!accounts.isEmpty());
    }
}
