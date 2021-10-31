package org.ok.starfish.data.sample.user;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.user.properties.*;
import org.ok.starfish.data.service.account.AccountService;
import org.ok.starfish.model.account.Account;
import org.ok.starfish.model.user.User;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.ZonedDateTime.now;
import static org.ok.starfish.data.TestDataUtils.getUniqueId;

@Service
@Slf4j
public class MockSampleUserProvider implements SampleUserProvider {

    private final AccountService accountService;

    private final UserGenderProvider userGenderProvider;

    private final UserTitleProvider userTitleProvider;

    private final UserFirstNameProvider userFirstNameProvider;

    private final UserLastNameProvider userLastNameProvider;

    private final UserStreetNumberProvider userStreetNumberProvider;

    private final UserStreetNameProvider userStreetNameProvider;

    private final UserCityProvider userCityProvider;

    private final UserStateProvider userStateProvider;

    private final UserCountryProvider userCountryProvider;

    public MockSampleUserProvider(AccountService accountService,
                                  UserGenderProvider userGenderProvider,
                                  UserTitleProvider userTitleProvider,
                                  UserFirstNameProvider userFirstNameProvider,
                                  UserLastNameProvider userLastNameProvider,
                                  UserStreetNumberProvider userStreetNumberProvider,
                                  UserStreetNameProvider userStreetNameProvider,
                                  UserCityProvider userCityProvider,
                                  UserStateProvider userStateProvider,
                                  UserCountryProvider userCountryProvider) {
        this.accountService = accountService;
        this.userGenderProvider = userGenderProvider;
        this.userTitleProvider = userTitleProvider;
        this.userFirstNameProvider = userFirstNameProvider;
        this.userLastNameProvider = userLastNameProvider;
        this.userStreetNumberProvider = userStreetNumberProvider;
        this.userStreetNameProvider = userStreetNameProvider;
        this.userCityProvider = userCityProvider;
        this.userStateProvider = userStateProvider;
        this.userCountryProvider = userCountryProvider;
    }

    @Override
    public @NotNull User getItem() {
        return getItem(1);
    }

    @Override
    public @NotNull List<User> getItems(int numberOfItems) {
        List<User> result = new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getItem(i+1));
        }
        return result;
    }

    private @NotNull User getItem(int itemNumber) {
        Optional<Account> account = accountService.findRandom();
        if(account.isPresent()) {
            User result = new User(
                    getUniqueId(),
                    userGenderProvider.get(),
                    userTitleProvider.get(),
                    userFirstNameProvider.get(),
                    userLastNameProvider.get(),
                    userStreetNumberProvider.get(),
                    userStreetNameProvider.get(),
                    userCityProvider.get(),
                    userStateProvider.get(),
                    userCountryProvider.get(),
                    now(ZoneOffset.UTC),
                    account.get()
            );
            log.info("User {} created: {}", itemNumber, result);
            return result;
        }
        throw new RuntimeException("Failed to create user. Could not find a valid account");
    }
}
