package org.ok.starfish.data.content.loader.application;

import org.ok.starfish.data.content.provider.application.ApplicationCategoryContentProvider;
import org.ok.starfish.data.service.application.ApplicationCategoryService;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class ApplicationCategoryContentLoaderImpl implements ApplicationCategoryContentLoader {

    private final ApplicationCategoryContentProvider applicationCategoryContentProvider;
    private final ApplicationCategoryService applicationCategoryService;

    public ApplicationCategoryContentLoaderImpl(ApplicationCategoryContentProvider applicationCategoryContentProvider, ApplicationCategoryService applicationCategoryService) {
        this.applicationCategoryContentProvider = applicationCategoryContentProvider;
        this.applicationCategoryService = applicationCategoryService;
    }

    @Override
    public @NotNull Iterable<ApplicationCategory> ensureContentLoaded() {
        List<ApplicationCategory> content = applicationCategoryContentProvider.get();
        return applicationCategoryService.saveAll(content);
    }
}
