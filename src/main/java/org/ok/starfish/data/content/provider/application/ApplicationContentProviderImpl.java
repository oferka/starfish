package org.ok.starfish.data.content.provider.application;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.IdProvider;
import org.ok.starfish.data.service.application.ApplicationCategoryService;
import org.ok.starfish.model.application.Application;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Service
@Slf4j
public class ApplicationContentProviderImpl implements ApplicationContentProvider {

    private final ApplicationCategoryService applicationCategoryService;

    private final IdProvider idProvider;

    private final CreationDateProvider creationDateProvider;

    public ApplicationContentProviderImpl(ApplicationCategoryService applicationCategoryService, IdProvider idProvider, CreationDateProvider creationDateProvider) {
        this.applicationCategoryService = applicationCategoryService;
        this.idProvider = idProvider;
        this.creationDateProvider = creationDateProvider;
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
        Optional<ApplicationCategory> applicationCategory = applicationCategoryService.findRandom();
        if(applicationCategory.isPresent()) {
            return new Application(idProvider.getRandom(), name, creationDateProvider.getNow(), applicationCategory.get());
        }
        throw new RuntimeException("Failed to create application. Could not find a valid application category");
    }
}
