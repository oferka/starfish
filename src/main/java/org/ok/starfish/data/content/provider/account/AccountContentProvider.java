package org.ok.starfish.data.content.provider.account;

import org.ok.starfish.model.account.Account;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AccountContentProvider {

    @NotNull List<Account> get();
}
