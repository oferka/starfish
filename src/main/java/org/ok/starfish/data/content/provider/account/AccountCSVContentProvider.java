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

    private final AccountsCsvReader csvReader;

    private final CreationDateProvider creationDateProvider;

    public AccountCSVContentProvider(IdProvider idProvider,
                                     AccountsCsvReader accountsCsvReader,
                                     CreationDateProvider creationDateProvider) {
        this.idProvider = idProvider;
        this.csvReader = accountsCsvReader;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<Account> get(int numberOfItems) {
        List<AccountLine> lines = csvReader.read();
        List<Account> result =  new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getAccount(lines.get(i)));
        }
        log.info("{} accounts provided", result.size());
        return result;
    }

    private @NotNull Account getAccount(@NotNull AccountLine line) {
        return new Account(
                idProvider.getRandom(),
                line.getSymbol(),
                line.getName(),
                line.getSector(),
                creationDateProvider.getNow()
        );
    }
}
