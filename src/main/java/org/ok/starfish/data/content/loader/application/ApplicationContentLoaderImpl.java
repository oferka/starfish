package org.ok.starfish.data.content.loader.application;

import org.ok.starfish.data.content.provider.application.ApplicationContentProvider;
import org.ok.starfish.data.service.application.ApplicationService;
import org.ok.starfish.model.application.Application;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class ApplicationContentLoaderImpl implements ApplicationContentLoader {

    private final ApplicationContentProvider applicationContentProvider;
    private final ApplicationService applicationService;

    public ApplicationContentLoaderImpl(ApplicationContentProvider applicationContentProvider, ApplicationService applicationService) {
        this.applicationContentProvider = applicationContentProvider;
        this.applicationService = applicationService;
    }

    @Override
    public @NotNull Iterable<Application> load() {
        List<Application> content = applicationContentProvider.get();
        return applicationService.saveAll(content);
    }
}
