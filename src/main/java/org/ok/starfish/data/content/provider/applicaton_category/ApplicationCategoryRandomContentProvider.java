package org.ok.starfish.data.content.provider.applicaton_category;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.IdProvider;
import org.ok.starfish.data.content.provider.applicaton_category.properties.ApplicationCategoryNameProvider;
import org.ok.starfish.model.applicaton_category.ApplicationCategory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Profile("random-application-categories-provider")
public class ApplicationCategoryRandomContentProvider implements ApplicationCategoryContentProvider {

    private final IdProvider idProvider;

    private final ApplicationCategoryNameProvider applicationCategoryNameProvider;

    private final CreationDateProvider creationDateProvider;

    public ApplicationCategoryRandomContentProvider(IdProvider idProvider,
                                                    ApplicationCategoryNameProvider applicationCategoryNameProvider,
                                                    CreationDateProvider creationDateProvider) {
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
        return new ApplicationCategory(
                idProvider.getRandom(),
                applicationCategoryNameProvider.get(),
                creationDateProvider.getNow()
        );
    }
}
