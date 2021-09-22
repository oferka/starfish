package org.ok.starfish.data.content.verifier.application;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.service.application.ApplicationCategoryService;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ApplicationCategoryContentVerifierImpl implements ApplicationCategoryContentVerifier {

    private final ApplicationCategoryService applicationCategoryService;

    public ApplicationCategoryContentVerifierImpl(ApplicationCategoryService applicationCategoryService) {
        this.applicationCategoryService = applicationCategoryService;
    }

    @Override
    public Iterable<ApplicationCategory> findLoaded(Iterable<ApplicationCategory> applicationCategories) {
        List<ApplicationCategory> result = new ArrayList<>();
        for(ApplicationCategory applicationCategory : applicationCategories) {
            if(applicationCategoryService.exists(applicationCategory)) {
                result.add(applicationCategory);
            }
        }
        log.info("{} application categories found as loaded", result.size());
        return result;
    }

    @Override
    public Iterable<ApplicationCategory> findNotLoaded(Iterable<ApplicationCategory> applicationCategories) {
        List<ApplicationCategory> result = new ArrayList<>();
        for(ApplicationCategory applicationCategory : applicationCategories) {
            if(!applicationCategoryService.exists(applicationCategory)) {
                result.add(applicationCategory);
            }
        }
        log.info("{} application categories found as unloaded", result.size());
        return result;
    }
}
