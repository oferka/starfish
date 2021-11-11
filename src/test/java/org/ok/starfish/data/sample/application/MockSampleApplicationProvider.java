package org.ok.starfish.data.sample.application;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.application.properties.ApplicationLogoProvider;
import org.ok.starfish.data.content.provider.application.properties.ApplicationNameProvider;
import org.ok.starfish.data.service.applicaton_category.ApplicationCategoryService;
import org.ok.starfish.model.application.Application;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static org.ok.starfish.data.TestDataUtils.getUniqueId;

@Service
@Slf4j
public class MockSampleApplicationProvider implements SampleApplicationProvider {

    private final ApplicationCategoryService applicationCategoryService;

    private final ApplicationNameProvider applicationNameProvider;

    private final ApplicationLogoProvider applicationLogoProvider;

    private final CreationDateProvider creationDateProvider;

    public MockSampleApplicationProvider(ApplicationCategoryService applicationCategoryService,
                                         ApplicationNameProvider applicationNameProvider,
                                         ApplicationLogoProvider applicationLogoProvider,
                                         CreationDateProvider creationDateProvider) {
        this.applicationCategoryService = applicationCategoryService;
        this.applicationNameProvider = applicationNameProvider;
        this.applicationLogoProvider = applicationLogoProvider;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public @NotNull Application getItem() {
        return getItem(1);
    }

    @Override
    public @NotNull List<Application> getItems(int numberOfItems) {
        List<Application> result = new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getItem(i+1));
        }
        return result;
    }

    private @NotNull Application getItem(int itemNumber) {
        int numberOfCategories = RandomUtils.nextInt(1, 6);
        Application result = new Application(
                getUniqueId(),
                applicationNameProvider.get(),
                applicationLogoProvider.get(),
                creationDateProvider.getNow(),
                applicationCategoryService.findRandom(numberOfCategories)
        );
        log.info("Application {} created: {}", itemNumber, result);
        return result;
    }
}
