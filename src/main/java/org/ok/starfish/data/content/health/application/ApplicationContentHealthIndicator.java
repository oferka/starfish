package org.ok.starfish.data.content.health.application;

import org.ok.starfish.data.service.application.ApplicationService;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("applicationContent")
@ConditionalOnEnabledHealthIndicator("applicationContent")
public class ApplicationContentHealthIndicator implements HealthIndicator {

    private final ApplicationService applicationService;

    public ApplicationContentHealthIndicator(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public Health health() {
        Health.Builder status;
        try {
            long count = applicationService.count();
            status = (count > 0)?Health.up():Health.down();
            status.withDetail("number_of_applications", count);
        }
        catch (Exception e) {
            status = Health.down(e);
        }
        return status.build();
    }
}
