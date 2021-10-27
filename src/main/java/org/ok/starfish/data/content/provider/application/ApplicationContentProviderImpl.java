package org.ok.starfish.data.content.provider.application;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.IdProvider;
import org.ok.starfish.data.service.application.ApplicationCategoryService;
import org.ok.starfish.model.application.Application;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ApplicationContentProviderImpl implements ApplicationContentProvider {

    private final ApplicationCategoryService applicationCategoryService;

    private final IdProvider idProvider;

    private final ApplicationNameProvider applicationNameProvider;

    private final CreationDateProvider creationDateProvider;

    public ApplicationContentProviderImpl(ApplicationCategoryService applicationCategoryService,
                                          IdProvider idProvider,
                                          ApplicationNameProvider applicationNameProvider,
                                          CreationDateProvider creationDateProvider) {
        this.applicationCategoryService = applicationCategoryService;
        this.idProvider = idProvider;
        this.applicationNameProvider = applicationNameProvider;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<Application> get(int numberOfItems) {
        List<Application> result =  new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getApplication());
        }
        log.info("{} applications provided", result.size());
        return result;
    }

    private @NotNull Application getApplication() {
        Optional<ApplicationCategory> applicationCategory = applicationCategoryService.findRandom();
        if(applicationCategory.isPresent()) {
            return new Application(idProvider.getRandom(), applicationNameProvider.get(), creationDateProvider.getNow(), applicationCategory.get());
        }
        throw new RuntimeException("Failed to create application. Could not find a valid application category");
    }
}
