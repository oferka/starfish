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
    public void load() {
        Iterable<ApplicationCategory> applicationCategories = applicationCategoryContentLoader.load();
        log.info("Loaded {} application categories", IterableUtils.size(applicationCategories));
        Iterable<Application> applications = applicationContentLoader.load();
        log.info("Loaded {} applications", IterableUtils.size(applications));
    }
}
