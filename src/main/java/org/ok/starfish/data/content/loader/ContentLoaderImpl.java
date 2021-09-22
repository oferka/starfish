package org.ok.starfish.data.content.loader;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.loader.application.ApplicationCategoryContentLoader;
import org.ok.starfish.data.content.loader.application.ApplicationContentLoader;
import org.ok.starfish.model.application.Application;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContentLoaderImpl implements ContentLoader {

    private final ApplicationCategoryContentLoader applicationCategoryContentLoader;
    private final ApplicationContentLoader applicationContentLoader;

    public ContentLoaderImpl(ApplicationCategoryContentLoader applicationCategoryContentLoader, ApplicationContentLoader applicationContentLoader) {
        this.applicationCategoryContentLoader = applicationCategoryContentLoader;
        this.applicationContentLoader = applicationContentLoader;
    }

    @Override
    public void ensureContentLoaded() {
        Iterable<ApplicationCategory> applicationCategories = applicationCategoryContentLoader.ensureContentLoaded();
        Iterable<Application> applications = applicationContentLoader.ensureContentLoaded();
    }
}
