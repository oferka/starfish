package org.ok.starfish.data.sample.account;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.account.properties.AccountNameProvider;
import org.ok.starfish.data.content.provider.account.properties.AccountSectorProvider;
import org.ok.starfish.data.content.provider.account.properties.AccountSymbolProvider;
import org.ok.starfish.model.account.Account;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static java.time.ZonedDateTime.now;
import static org.ok.starfish.data.TestDataUtils.getUniqueId;

@Service
@Slf4j
public class MockSampleAccountProvider implements SampleAccountProvider {

    private final AccountSymbolProvider accountSymbolProvider;

    private final AccountNameProvider accountNameProvider;

    private final AccountSectorProvider accountSectorProvider;

    public MockSampleAccountProvider(AccountSymbolProvider accountSymbolProvider, AccountNameProvider accountNameProvider, AccountSectorProvider accountSectorProvider) {
        this.accountSymbolProvider = accountSymbolProvider;
        this.accountNameProvider = accountNameProvider;
        this.accountSectorProvider = accountSectorProvider;
    }

    @Override
    public @NotNull Account getItem() {
        return getItem(1);
    }

    @Override
    public @NotNull List<Account> getItems(int numberOfItems) {
        List<Account> result = new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getItem(i+1));
        }
        return result;
    }

    private @NotNull Account getItem(int itemNumber) {
        Account result = new Account(
                getUniqueId(),
                accountSymbolProvider.get(),
                accountNameProvider.get(),
                accountSectorProvider.get(),
                now(ZoneOffset.UTC)
        );
        log.info("Account {} created: {}", itemNumber, result);
        return result;
    }
}
