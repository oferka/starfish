package org.ok.starfish.data.content.loader.account;

import org.ok.starfish.model.account.Account;

import javax.validation.constraints.NotNull;

public interface AccountContentLoader {

    @NotNull Iterable<Account> ensureContentLoaded();
}
