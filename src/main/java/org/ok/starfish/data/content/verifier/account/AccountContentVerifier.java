package org.ok.starfish.data.content.verifier.account;

import org.ok.starfish.model.account.Account;

import javax.validation.constraints.NotNull;

public interface AccountContentVerifier {

    @NotNull Iterable<Account> findLoaded(@NotNull Iterable<Account> accounts);

    @NotNull Iterable<Account> findNotLoaded(@NotNull Iterable<Account> accounts);
}
