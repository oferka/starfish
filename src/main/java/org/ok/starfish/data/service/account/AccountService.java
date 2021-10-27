package org.ok.starfish.data.service.account;

import org.ok.starfish.model.account.Account;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    @NotNull List<Account> findAll();

    @NotNull Optional<Account> findById(@NotNull String id);

    @NotNull List<Account> findBySymbol(@NotNull String symbol);

    @NotNull List<Account> findByName(@NotNull String name);

    @NotNull List<Account> findBySector(@NotNull String sector);

    @NotNull List<Account> findByCreatedDate(@NotNull ZonedDateTime createdDate);

    @NotNull Optional<Account> findRandom();

    long count();

    @NotNull Account save(@NotNull Account account);

    @NotNull Iterable<Account> saveAll(@NotNull Iterable<Account> accounts);

    @NotNull Optional<Account> update(@NotNull String id, @NotNull Account account);

    void deleteById(@NotNull String id);

    boolean existsById(@NotNull String id);

    boolean exists(@NotNull Account account);
}
