package org.ok.starfish.data.sample.application;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.model.application.Application;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static org.ok.starfish.data.TestDataUtils.getUniqueId;

@Service
@Slf4j
public class MockSampleApplicationProvider implements SampleApplicationProvider {

    private final SampleApplicationCategoryProvider sampleApplicationCategoryProvider;

    public MockSampleApplicationProvider(SampleApplicationCategoryProvider sampleApplicationCategoryProvider) {
        this.sampleApplicationCategoryProvider = sampleApplicationCategoryProvider;
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
        Application result = new Application(getUniqueId(), getRandomApplicationName(), sampleApplicationCategoryProvider.getItem());
        log.info("Application {} created: {}", itemNumber, result);
        return result;
    }

    private @NotNull String getRandomApplicationName() {
        return new Faker().name().name();
    }
}
