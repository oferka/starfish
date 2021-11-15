package org.ok.starfish.data.content.loader.applicaton_category;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.ok.starfish.data.content.provider.applicaton_category.ApplicationCategoryContentProvider;
import org.ok.starfish.data.content.verifier.application_category.ApplicationCategoryContentVerifier;
import org.ok.starfish.data.service.applicaton_category.ApplicationCategoryService;
import org.ok.starfish.model.applicaton_category.ApplicationCategory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Slf4j
public class ApplicationCategoryContentLoaderImpl implements ApplicationCategoryContentLoader {

    private final ApplicationCategoryContentProvider applicationCategoryContentProvider;
    private final ApplicationCategoryContentVerifier applicationCategoryContentVerifier;
    private final ApplicationCategoryService applicationCategoryService;

    public ApplicationCategoryContentLoaderImpl(ApplicationCategoryContentProvider applicationCategoryContentProvider, ApplicationCategoryContentVerifier applicationCategoryContentVerifier, ApplicationCategoryService applicationCategoryService) {
        this.applicationCategoryContentProvider = applicationCategoryContentProvider;
        this.applicationCategoryContentVerifier = applicationCategoryContentVerifier;
        this.applicationCategoryService = applicationCategoryService;
    }

    @Override
    public @NotNull Iterable<ApplicationCategory> ensureContentLoaded() {
        List<ApplicationCategory> content = applicationCategoryContentProvider.get(116);
        Iterable<ApplicationCategory> unloadedContent = applicationCategoryContentVerifier.findNotLoaded(content);
        if(!IterableUtils.isEmpty(unloadedContent)) {
            Iterable<ApplicationCategory> saved = applicationCategoryService.saveAll(unloadedContent);
            log.info("{} application categories saved", IterableUtils.size(saved));
        }
        log.info("{} application categories ensured loaded", content.size());
        return content;
    }
}
