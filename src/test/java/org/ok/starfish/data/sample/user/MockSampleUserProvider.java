package org.ok.starfish.data.sample.user;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.service.account.AccountService;
import org.ok.starfish.model.account.Account;
import org.ok.starfish.model.user.User;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.ok.starfish.data.TestDataUtils.getUniqueId;

@Service
@Slf4j
public class MockSampleUserProvider implements SampleUserProvider {

    private final AccountService accountService;

    public MockSampleUserProvider(AccountService accountService) {
        this.accountService = accountService;
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
            User result = new User(getUniqueId(), getRandomUserName(), account.get());
            log.info("User {} created: {}", itemNumber, result);
            return result;
        }
        throw new RuntimeException("Failed to create user. Could not find a valid account");
    }

    private @NotNull String getRandomUserName() {
        return new Faker().name().name();
    }
}
