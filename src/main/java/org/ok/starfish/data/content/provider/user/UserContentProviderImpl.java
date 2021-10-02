package org.ok.starfish.data.content.provider.user;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.IdProvider;
import org.ok.starfish.data.service.account.AccountService;
import org.ok.starfish.model.account.Account;
import org.ok.starfish.model.user.User;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Service
@Slf4j
public class UserContentProviderImpl implements UserContentProvider {

    private final AccountService accountService;

    private final IdProvider idProvider;

    private final CreationDateProvider creationDateProvider;

    public UserContentProviderImpl(AccountService accountService, IdProvider idProvider, CreationDateProvider creationDateProvider) {
        this.accountService = accountService;
        this.idProvider = idProvider;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<User> get() {
        List<User> result = asList(
                getUser("User 1"),
                getUser("User 2"),
                getUser("User 3"),
                getUser("User 4"),
                getUser("User 5"),
                getUser("User 6"),
                getUser("User 7"),
                getUser("User 8"),
                getUser("User 9"),
                getUser("User 10"),
                getUser("User 11"),
                getUser("User 12"),
                getUser("User 13"),
                getUser("User 14"),
                getUser("User 15"),
                getUser("User 16"),
                getUser("User 17")
        );
        log.info("{} users provided", result.size());
        return result;
    }

    private @NotNull User getUser(@NotNull String name) {
        Optional<Account> account = accountService.findRandom();
        if(account.isPresent()) {
            return new User(idProvider.getRandom(), name, creationDateProvider.getNow(), account.get());
        }
        throw new RuntimeException("Failed to create user. Could not find a valid account");
    }
}
