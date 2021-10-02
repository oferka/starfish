package org.ok.starfish.data.content.provider.account;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.IdProvider;
import org.ok.starfish.model.account.Account;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.Arrays.asList;

@Service
@Slf4j
public class AccountContentProviderImpl implements AccountContentProvider {

    private final IdProvider idProvider;

    private final CreationDateProvider creationDateProvider;

    public AccountContentProviderImpl(IdProvider idProvider, CreationDateProvider creationDateProvider) {
        this.idProvider = idProvider;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<Account> get() {
        List<Account> result =  asList(
                getAccount("Account 1"),
                getAccount("Account 2"),
                getAccount("Account 3"),
                getAccount("Account 4"),
                getAccount("Account 5"),
                getAccount("Account 6"),
                getAccount("Account 7"),
                getAccount("Account 8"),
                getAccount("Account 9"),
                getAccount("Account 10")
        );
        log.info("{} accounts provided", result.size());
        return result;
    }

    private @NotNull Account getAccount(@NotNull String name) {
        return new Account(idProvider.getRandom(), name, creationDateProvider.getNow());
    }
}
