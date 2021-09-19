package org.ok.starfish.data.sample.application;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MockApplicationCategoryEmployeeProvider implements SampleApplicationCategoryProvider {

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
        String id = UUID.randomUUID().toString();
        ApplicationCategory result = new ApplicationCategory(id, getRandomApplicationCategoryName());
        log.info(result.toString());
        return result;
    }

    private @NotNull String getRandomApplicationCategoryName() {
        return new Faker().name().name();
    }
}