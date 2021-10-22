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
@Profile("random-users-provider")
public class UserRandomContentProvider implements UserContentProvider {

    private final AccountService accountService;

    private final IdProvider idProvider;

    private final UserGenderProvider userGenderProvider;

    private final UserNameProvider userNameProvider;

    private final CreationDateProvider creationDateProvider;

    public UserRandomContentProvider(AccountService accountService, IdProvider idProvider, UserGenderProvider userGenderProvider, UserNameProvider userNameProvider, CreationDateProvider creationDateProvider) {
        this.accountService = accountService;
        this.idProvider = idProvider;
        this.userGenderProvider = userGenderProvider;
        this.userNameProvider = userNameProvider;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<User> get(int numberOfItems) {
        List<User> result =  new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getUser());
        }
        log.info("{} users provided", result.size());
        return result;
    }

    private @NotNull User getUser() {
        Optional<Account> account = accountService.findRandom();
        if(account.isPresent()) {
            return new User(idProvider.getRandom(), userGenderProvider.get(), userNameProvider.get(), creationDateProvider.getNow(), account.get());
        }
        throw new RuntimeException("Failed to create user. Could not find a valid account");
    }
}