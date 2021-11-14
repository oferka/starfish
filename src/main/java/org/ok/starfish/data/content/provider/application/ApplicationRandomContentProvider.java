package org.ok.starfish.data.content.provider.application;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.IdProvider;
import org.ok.starfish.data.content.provider.application.properties.ApplicationLogoProvider;
import org.ok.starfish.data.content.provider.application.properties.ApplicationNameProvider;
import org.ok.starfish.data.service.applicaton_category.ApplicationCategoryService;
import org.ok.starfish.data.service.vendor.VendorService;
import org.ok.starfish.model.application.Application;
import org.ok.starfish.model.vendor.Vendor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Profile("random-applications-provider")
public class ApplicationRandomContentProvider implements ApplicationContentProvider {

    private final ApplicationCategoryService applicationCategoryService;

    private final VendorService vendorService;

    private final IdProvider idProvider;

    private final ApplicationNameProvider applicationNameProvider;

    private final ApplicationLogoProvider applicationLogoProvider;

    private final CreationDateProvider creationDateProvider;

    public ApplicationRandomContentProvider(ApplicationCategoryService applicationCategoryService,
                                            VendorService vendorService,
                                            IdProvider idProvider,
                                            ApplicationNameProvider applicationNameProvider,
                                            ApplicationLogoProvider applicationLogoProvider,
                                            CreationDateProvider creationDateProvider) {
        this.applicationCategoryService = applicationCategoryService;
        this.vendorService = vendorService;
        this.idProvider = idProvider;
        this.applicationNameProvider = applicationNameProvider;
        this.applicationLogoProvider = applicationLogoProvider;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<Application> get(int numberOfItems) {
        List<Application> result =  new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getApplication());
        }
        log.info("{} applications provided", result.size());
        return result;
    }

    private @NotNull Application getApplication() {
        Optional<Vendor> vendor = vendorService.findRandom();
        if(vendor.isPresent()) {
            int numberOfCategories = RandomUtils.nextInt(1, 6);
            return new Application(
                    idProvider.getRandom(),
                    applicationNameProvider.get(),
                    applicationLogoProvider.get(),
                    creationDateProvider.getNow(),
                    applicationCategoryService.findRandom(numberOfCategories),
                    vendor.get()
            );
        }
        throw new RuntimeException("Failed to create application. Could not find a valid vendor");
    }
}
