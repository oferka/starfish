package org.ok.starfish.data.content.loader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
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
        log.info("{} application categories are loaded", IterableUtils.size(applicationCategories));
        Iterable<Application> applications = applicationContentLoader.ensureContentLoaded();
        log.info("{} applications are loaded", IterableUtils.size(applications));
    }
}
