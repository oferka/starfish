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
public class AccountContentProviderImpl implements AccountContentProvider {

    private final IdProvider idProvider;

    private final CreationDateProvider creationDateProvider;

    private final AccountNameProvider accountNameProvider;

    public AccountContentProviderImpl(IdProvider idProvider, CreationDateProvider creationDateProvider, AccountNameProvider accountNameProvider) {
        this.idProvider = idProvider;
        this.creationDateProvider = creationDateProvider;
        this.accountNameProvider = accountNameProvider;
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
