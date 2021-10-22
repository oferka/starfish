package org.ok.starfish.data.content.provider.user;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.IdProvider;
import org.ok.starfish.data.service.account.AccountService;
import org.ok.starfish.model.account.Account;
import org.ok.starfish.model.user.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Profile({"csv-users-provider", "default"})
public class UserCSVContentProvider implements UserContentProvider {

    private final AccountService accountService;

    private final IdProvider idProvider;

    private final UsersCsvReader usersCsvReader;

    private final CreationDateProvider creationDateProvider;

    public UserCSVContentProvider(AccountService accountService, IdProvider idProvider, UsersCsvReader usersCsvReader, CreationDateProvider creationDateProvider) {
        this.accountService = accountService;
        this.idProvider = idProvider;
        this.usersCsvReader = usersCsvReader;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<User> get(int numberOfItems) {
        List<UserLine> userLines = usersCsvReader.read();
        List<User> result =  new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getUser(userLines.get(i)));
        }
        log.info("{} users provided", result.size());
        return result;
    }

    private @NotNull User getUser(@NotNull UserLine userLine) {
        Optional<Account> account = accountService.findRandom();
        if(account.isPresent()) {
            return new User(
                    idProvider.getRandom(),
                    userLine.getGender(),
                    userLine.getTitle(),
                    userLine.getFirstName(),
                    creationDateProvider.getNow(),
                    account.get()
            );
        }
        throw new RuntimeException("Failed to create user. Could not find a valid account");
    }
}
