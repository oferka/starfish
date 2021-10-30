package org.ok.starfish.data.sample.application;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.application.ApplicationCategoryNameProvider;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static java.time.ZonedDateTime.now;
import static org.ok.starfish.data.TestDataUtils.getUniqueId;

@Service
@Slf4j
public class MockApplicationCategoryProvider implements SampleApplicationCategoryProvider {

    private final ApplicationCategoryNameProvider applicationCategoryNameProvider;

    public MockApplicationCategoryProvider(ApplicationCategoryNameProvider applicationCategoryNameProvider) {
        this.applicationCategoryNameProvider = applicationCategoryNameProvider;
    }

    @Override
    public @NotNull ApplicationCategory getItem() {
        return getItem(1);
    }

    @Override
    public @NotNull List<ApplicationCategory> getItems(int numberOfItems) {
        List<ApplicationCategory> result = new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getItem(i+1));
        }
        return result;
    }

    private @NotNull ApplicationCategory getItem(int itemNumber) {
        ApplicationCategory result = new ApplicationCategory(getUniqueId(), applicationCategoryNameProvider.get(), now(ZoneOffset.UTC));
        log.info("Application category {} created: {}", itemNumber, result);
        return result;
    }
}
