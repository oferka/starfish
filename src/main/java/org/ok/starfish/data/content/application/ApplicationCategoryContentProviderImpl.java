package org.ok.starfish.data.content.application;

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
            getApplicationCategory("1"),
            getApplicationCategory("2"),
            getApplicationCategory("3")
        );
    }

    private @NotNull ApplicationCategory getApplicationCategory(@NotNull String name) {
        String id = UUID.randomUUID().toString();
        return new ApplicationCategory(id, name);
    }
}
