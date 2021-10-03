package org.ok.starfish.data.content.provider.account;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.IdProvider;
import org.ok.starfish.model.account.Account;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccountRandomContentProvider implements AccountContentProvider {

    private final IdProvider idProvider;

    private final AccountNameProvider accountNameProvider;

    private final CreationDateProvider creationDateProvider;

    public AccountRandomContentProvider(IdProvider idProvider, AccountNameProvider accountNameProvider, CreationDateProvider creationDateProvider) {
        this.idProvider = idProvider;
        this.accountNameProvider = accountNameProvider;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<Account> get(int numberOfItems) {
        List<Account> result =  new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getAccount());
        }
        log.info("{} accounts provided", result.size());
        return result;
    }

    private @NotNull Account getAccount() {
        return new Account(idProvider.getRandom(), accountNameProvider.get(), creationDateProvider.getNow());
    }
}
