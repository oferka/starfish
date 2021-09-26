package org.ok.starfish.data.content.health.account;

import org.ok.starfish.data.service.account.AccountService;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("accountContent")
@ConditionalOnEnabledHealthIndicator("accountContent")
public class AccountContentHealthIndicator implements HealthIndicator {

    private final AccountService accountService;

    public AccountContentHealthIndicator(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Health health() {
        Health.Builder status;
        try {
            long count = accountService.count();
            status = (count > 0)?Health.up():Health.down();
            status.withDetail("number_of_accounts", count);
        }
        catch (Exception e) {
            status = Health.down(e);
        }
        return status.build();
    }
}
