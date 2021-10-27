package org.ok.starfish.data.content.provider.account;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.IdProvider;
import org.ok.starfish.model.account.Account;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Profile({"csv-accounts-provider", "default"})
public class AccountCSVContentProvider implements AccountContentProvider {

    private final IdProvider idProvider;

    private final AccountsCsvReader accountsCsvReader;

    private final CreationDateProvider creationDateProvider;

    public AccountCSVContentProvider(IdProvider idProvider, AccountsCsvReader accountsCsvReader, CreationDateProvider creationDateProvider) {
        this.idProvider = idProvider;
        this.accountsCsvReader = accountsCsvReader;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<Account> get(int numberOfItems) {
        List<AccountLine> accountLines = accountsCsvReader.read();
        List<Account> result =  new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getAccount(accountLines.get(i)));
        }
        log.info("{} accounts provided", result.size());
        return result;
    }

    private @NotNull Account getAccount(@NotNull AccountLine accountLine) {
        return new Account(
                idProvider.getRandom(),
                accountLine.getSymbol(),
                accountLine.getName(),
                accountLine.getSector(),
                creationDateProvider.getNow()
        );
    }
}
