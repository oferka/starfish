package org.ok.starfish.data.sample.account;

import org.ok.starfish.model.account.Account;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SampleAccountProvider {

    @NotNull Account getItem();

    @NotNull List<Account> getItems(int numberOfItems);
}
