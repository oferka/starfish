package org.ok.starfish.data.content.provider.application;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.IdProvider;
import org.ok.starfish.data.service.applicaton_category.ApplicationCategoryService;
import org.ok.starfish.model.application.Application;
import org.ok.starfish.model.applicaton_category.ApplicationCategory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Profile({"csv-applications-provider", "default"})
public class ApplicationCSVContentProvider implements ApplicationContentProvider {

    private final IdProvider idProvider;

    private final ApplicationsCsvReader csvReader;

    private final CreationDateProvider creationDateProvider;

    private final ApplicationCategoryService applicationCategoryService;

    public ApplicationCSVContentProvider(IdProvider idProvider,
                                         ApplicationsCsvReader csvReader,
                                         CreationDateProvider creationDateProvider,
                                         ApplicationCategoryService applicationCategoryService) {
        this.idProvider = idProvider;
        this.csvReader = csvReader;
        this.creationDateProvider = creationDateProvider;
        this.applicationCategoryService = applicationCategoryService;
    }

    @Override
    public List<Application> get(int numberOfItems) {
        List<ApplicationLine> lines = csvReader.read();
        List<Application> result =  new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getApplication(lines.get(i)));
        }
        log.info("{} applications provided", result.size());
        return result;
    }

    private @NotNull Application getApplication(@NotNull ApplicationLine line) {
        return new Application(
                idProvider.getRandom(),
                line.getName(),
                line.getLogo(),
                creationDateProvider.getNow(),
                getApplicationCategories(line.getCategoryNames())
        );
    }

    private @NotNull List<ApplicationCategory> getApplicationCategories(@NotNull String categoriesString) {
        List<ApplicationCategory> result = new ArrayList<>();
        String[] categoryNames = categoriesString.split("@");
        for(String categoryName : categoryNames) {
            List<ApplicationCategory> applicationCategories = applicationCategoryService.findByName(categoryName);
            result.addAll(applicationCategories);
        }
        return result;
    }
}
