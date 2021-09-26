package org.ok.starfish.data.content.provider.account;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.model.account.Account;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

@Service
@Slf4j
public class AccountContentProviderImpl implements AccountContentProvider {

    @Override
    public List<Account> get() {
        List<Account> result =  asList(
                getAccount("Application Category 1"),
                getAccount("Application Category 2"),
                getAccount("Application Category 3"),
                getAccount("Application Category 4"),
                getAccount("Application Category 5"),
                getAccount("Application Category 6"),
                getAccount("Application Category 7"),
                getAccount("Application Category 8"),
                getAccount("Application Category 9"),
                getAccount("Application Category 10")
        );
        log.info("{} accounts provided", result.size());
        return result;
    }

    private @NotNull Account getAccount(@NotNull String name) {
        String id = UUID.randomUUID().toString();
        return new Account(id, name);
    }
}
