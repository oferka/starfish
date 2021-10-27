package org.ok.starfish.data.content.provider.application;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.IdProvider;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Profile({"csv-application-categories-provider", "default"})
public class ApplicationCategoryCSVContentProvider implements ApplicationCategoryContentProvider {

    private final IdProvider idProvider;

    private final ApplicationCategoriesCsvReader csvReader;

    private final CreationDateProvider creationDateProvider;

    public ApplicationCategoryCSVContentProvider(IdProvider idProvider,
                                                 ApplicationCategoriesCsvReader csvReader,
                                                 CreationDateProvider creationDateProvider) {
        this.idProvider = idProvider;
        this.csvReader = csvReader;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<ApplicationCategory> get(int numberOfItems) {
        List<ApplicationCategoryLine> lines = csvReader.read();
        List<ApplicationCategory> result =  new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getApplicationCategory(lines.get(i)));
        }
        log.info("{} application categories provided", result.size());
        return result;
    }

    private @NotNull ApplicationCategory getApplicationCategory(@NotNull ApplicationCategoryLine line) {
        return new ApplicationCategory(
                idProvider.getRandom(),
                line.getName(),
                creationDateProvider.getNow()
        );
    }
}

