package org.ok.starfish.data.content.provider.application;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

@Service
@Slf4j
public class ApplicationCategoryContentProviderImpl implements ApplicationCategoryContentProvider {

    private final CreationDateProvider creationDateProvider;

    public ApplicationCategoryContentProviderImpl(CreationDateProvider creationDateProvider) {
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<ApplicationCategory> get() {
        List<ApplicationCategory> result =  asList(
            getApplicationCategory("Application Category 1"),
            getApplicationCategory("Application Category 2"),
            getApplicationCategory("Application Category 3"),
            getApplicationCategory("Application Category 4"),
            getApplicationCategory("Application Category 5")
        );
        log.info("{} application categories provided", result.size());
        return result;
    }

    private @NotNull ApplicationCategory getApplicationCategory(@NotNull String name) {
        String id = UUID.randomUUID().toString();
        return new ApplicationCategory(id, name, creationDateProvider.getNow());
    }
}
