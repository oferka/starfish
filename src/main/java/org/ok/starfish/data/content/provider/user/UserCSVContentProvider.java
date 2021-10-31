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

    private final UsersCsvReader csvReader;

    private final CreationDateProvider creationDateProvider;

    public UserCSVContentProvider(AccountService accountService,
                                  IdProvider idProvider,
                                  UsersCsvReader usersCsvReader,
                                  CreationDateProvider creationDateProvider) {
        this.accountService = accountService;
        this.idProvider = idProvider;
        this.csvReader = usersCsvReader;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<User> get(int numberOfItems) {
        List<UserLine> lines = csvReader.read();
        List<User> result =  new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getUser(lines.get(i)));
        }
        log.info("{} users provided", result.size());
        return result;
    }

    private @NotNull User getUser(@NotNull UserLine line) {
        Optional<Account> account = accountService.findRandom();
        if(account.isPresent()) {
            return new User(
                    idProvider.getRandom(),
                    line.getGender(),
                    line.getTitle(),
                    line.getFirstName(),
                    line.getLastName(),
                    line.getStreetNumber(),
                    line.getStreetName(),
                    line.getCity(),
                    line.getState(),
                    line.getCountry(),
                    creationDateProvider.getNow(),
                    account.get()
            );
        }
        throw new RuntimeException("Failed to create user. Could not find a valid account");
    }
}
