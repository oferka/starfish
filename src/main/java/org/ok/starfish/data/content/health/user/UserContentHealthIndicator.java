package org.ok.starfish.data.content.health.user;

import org.ok.starfish.data.service.user.UserService;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("userContent")
@ConditionalOnEnabledHealthIndicator("userContent")
public class UserContentHealthIndicator implements HealthIndicator {

    private final UserService userService;

    public UserContentHealthIndicator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Health health() {
        Health.Builder status;
        try {
            long count = userService.count();
            status = (count > 0)?Health.up():Health.down();
            status.withDetail("number_of_users", count);
        }
        catch (Exception e) {
            status = Health.down(e);
        }
        return status.build();
    }
}
