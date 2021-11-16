package org.ok.starfish.data.content.loader.application;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.ok.starfish.data.content.provider.application.ApplicationContentProvider;
import org.ok.starfish.data.content.verifier.application.ApplicationContentVerifier;
import org.ok.starfish.data.service.application.ApplicationService;
import org.ok.starfish.model.application.Application;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Slf4j
public class ApplicationContentLoaderImpl implements ApplicationContentLoader {

    private final ApplicationContentProvider applicationContentProvider;
    private final ApplicationContentVerifier applicationContentVerifier;
    private final ApplicationService applicationService;

    public ApplicationContentLoaderImpl(ApplicationContentProvider applicationContentProvider, ApplicationContentVerifier applicationContentVerifier, ApplicationService applicationService) {
        this.applicationContentProvider = applicationContentProvider;
        this.applicationContentVerifier = applicationContentVerifier;
        this.applicationService = applicationService;
    }

    @Override
    public @NotNull Iterable<Application> ensureContentLoaded() {
        List<Application> content = applicationContentProvider.get(50);
        Iterable<Application> unloadedContent = applicationContentVerifier.findNotLoaded(content);
        if(!IterableUtils.isEmpty(unloadedContent)) {
            Iterable<Application> saved = applicationService.saveAll(unloadedContent);
            log.info("{} applications saved", IterableUtils.size(saved));
        }
        log.info("{} applications ensured loaded", content.size());
        return content;
    }
}
