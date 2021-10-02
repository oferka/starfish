package org.ok.starfish.data.content.provider.application;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.IdProvider;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ApplicationCategoryContentProviderImpl implements ApplicationCategoryContentProvider {

    private final IdProvider idProvider;

    private final ApplicationCategoryNameProvider applicationCategoryNameProvider;

    private final CreationDateProvider creationDateProvider;

    public ApplicationCategoryContentProviderImpl(IdProvider idProvider, ApplicationCategoryNameProvider applicationCategoryNameProvider, CreationDateProvider creationDateProvider) {
        this.idProvider = idProvider;
        this.applicationCategoryNameProvider = applicationCategoryNameProvider;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<ApplicationCategory> get(int numberOfItems) {
        List<ApplicationCategory> result =  new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getApplicationCategory());
        }
        log.info("{} application categories provided", result.size());
        return result;
    }

    private @NotNull ApplicationCategory getApplicationCategory() {
        return new ApplicationCategory(idProvider.getRandom(), applicationCategoryNameProvider.get(), creationDateProvider.getNow());
    }
}
