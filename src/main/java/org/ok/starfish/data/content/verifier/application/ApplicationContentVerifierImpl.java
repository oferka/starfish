package org.ok.starfish.data.content.verifier.application;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.service.application.ApplicationService;
import org.ok.starfish.model.application.Application;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ApplicationContentVerifierImpl implements ApplicationContentVerifier {

    private final ApplicationService applicationService;

    public ApplicationContentVerifierImpl(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public Iterable<Application> findLoaded(Iterable<Application> applications) {
        List<Application> result = new ArrayList<>();
        for(Application application : applications) {
            if(applicationService.exists(application)) {
                result.add(application);
            }
        }
        log.info("{} applications found as loaded", result.size());
        return result;
    }

    @Override
    public Iterable<Application> findNotLoaded(Iterable<Application> applications) {
        List<Application> result = new ArrayList<>();
        for(Application application : applications) {
            if(!applicationService.exists(application)) {
                result.add(application);
            }
        }
        log.info("{} applications found as unloaded", result.size());
        return result;
    }
}
