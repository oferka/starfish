package org.ok.starfish.data.content.provider.application;

import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

@Service
public class ApplicationCategoryContentProviderImpl implements ApplicationCategoryContentProvider {

    @Override
    public List<ApplicationCategory> get() {
        return asList(
            getApplicationCategory("Application Category 1"),
            getApplicationCategory("Application Category 2"),
            getApplicationCategory("Application Category 3"),
            getApplicationCategory("Application Category 4"),
            getApplicationCategory("Application Category 5")
        );
    }

    private @NotNull ApplicationCategory getApplicationCategory(@NotNull String name) {
        String id = UUID.randomUUID().toString();
        return new ApplicationCategory(id, name);
    }
}
