package org.ok.starfish.data.content.provider.vendor;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.IdProvider;
import org.ok.starfish.data.content.provider.vendor.properties.VendorLogoProvider;
import org.ok.starfish.data.content.provider.vendor.properties.VendorNameProvider;
import org.ok.starfish.model.vendor.Vendor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Profile("random-vendors-provider")
public class VendorRandomContentProvider implements VendorContentProvider {

    private final IdProvider idProvider;

    private final VendorNameProvider vendorNameProvider;

    private final VendorLogoProvider vendorLogoProvider;

    private final CreationDateProvider creationDateProvider;

    public VendorRandomContentProvider(IdProvider idProvider,
                                            VendorNameProvider vendorNameProvider,
                                            VendorLogoProvider vendorLogoProvider,
                                            CreationDateProvider creationDateProvider) {
        this.idProvider = idProvider;
        this.vendorNameProvider = vendorNameProvider;
        this.vendorLogoProvider = vendorLogoProvider;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<Vendor> get(int numberOfItems) {
        List<Vendor> result =  new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getVendor());
        }
        log.info("{} vendors provided", result.size());
        return result;
    }

    private @NotNull Vendor getVendor() {
        return new Vendor(
                idProvider.getRandom(),
                vendorNameProvider.get(),
                vendorLogoProvider.get(),
                creationDateProvider.getNow()
        );
    }
}
