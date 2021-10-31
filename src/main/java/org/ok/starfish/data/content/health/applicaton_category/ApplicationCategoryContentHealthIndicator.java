package org.ok.starfish.data.content.health.applicaton_category;

import org.ok.starfish.data.service.applicaton_category.ApplicationCategoryService;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("applicationCategoryContent")
@ConditionalOnEnabledHealthIndicator("applicationCategoryContent")
public class ApplicationCategoryContentHealthIndicator implements HealthIndicator {

    private final ApplicationCategoryService applicationCategoryService;

    public ApplicationCategoryContentHealthIndicator(ApplicationCategoryService applicationCategoryService) {
        this.applicationCategoryService = applicationCategoryService;
    }

    @Override
    public Health health() {
        Health.Builder status;
        try {
            long count = applicationCategoryService.count();
            status = (count > 0)?Health.up():Health.down();
            status.withDetail("number_of_application_categories", count);
        }
        catch (Exception e) {
            status = Health.down(e);
        }
        return status.build();
    }
}
