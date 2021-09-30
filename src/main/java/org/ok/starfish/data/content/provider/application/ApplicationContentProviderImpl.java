package org.ok.starfish.data.content.provider.application;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.service.application.ApplicationCategoryService;
import org.ok.starfish.model.application.Application;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.time.ZonedDateTime.now;
import static java.util.Arrays.asList;

@Service
@Slf4j
public class ApplicationContentProviderImpl implements ApplicationContentProvider {

    private final ApplicationCategoryService applicationCategoryService;

    public ApplicationContentProviderImpl(ApplicationCategoryService applicationCategoryService) {
        this.applicationCategoryService = applicationCategoryService;
    }

    @Override
    public List<Application> get() {
        List<Application> result = asList(
                getApplication("Application 1"),
                getApplication("Application 2"),
                getApplication("Application 3"),
                getApplication("Application 4"),
                getApplication("Application 5"),
                getApplication("Application 6"),
                getApplication("Application 7"),
                getApplication("Application 8"),
                getApplication("Application 9"),
                getApplication("Application 10"),
                getApplication("Application 11"),
                getApplication("Application 12")
        );
        log.info("{} applications provided", result.size());
        return result;
    }

    private @NotNull Application getApplication(@NotNull String name) {
        String id = UUID.randomUUID().toString();
        Optional<ApplicationCategory> applicationCategory = applicationCategoryService.findRandom();
        if(applicationCategory.isPresent()) {
            return new Application(id, name, now(ZoneOffset.UTC), applicationCategory.get());
        }
        throw new RuntimeException("Failed to create application. Could not find a valid application category");
    }
}
