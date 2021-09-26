package org.ok.starfish.data.sample.application;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static org.ok.starfish.data.TestDataUtils.getUniqueId;

@Service
@Slf4j
public class MockApplicationCategoryProvider implements SampleApplicationCategoryProvider {

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
        ApplicationCategory result = new ApplicationCategory(getUniqueId(), getRandomApplicationCategoryName());
        log.info("Application category {} created: {}", itemNumber, result);
        return result;
    }

    private @NotNull String getRandomApplicationCategoryName() {
        return new Faker().name().name();
    }
}
