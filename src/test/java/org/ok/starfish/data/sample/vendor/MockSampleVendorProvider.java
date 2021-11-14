package org.ok.starfish.data.sample.vendor;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.vendor.properties.VendorLogoProvider;
import org.ok.starfish.data.content.provider.vendor.properties.VendorNameProvider;
import org.ok.starfish.model.vendor.Vendor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static org.ok.starfish.data.TestDataUtils.getUniqueId;

@Service
@Slf4j
public class MockSampleVendorProvider implements SampleVendorProvider {

    private final VendorNameProvider vendorNameProvider;

    private final VendorLogoProvider vendorLogoProvider;

    private final CreationDateProvider creationDateProvider;

    public MockSampleVendorProvider(VendorNameProvider vendorNameProvider,
                                    VendorLogoProvider vendorLogoProvider,
                                    CreationDateProvider creationDateProvider) {
        this.vendorNameProvider = vendorNameProvider;
        this.vendorLogoProvider = vendorLogoProvider;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public @NotNull Vendor getItem() {
        return getItem(1);
    }

    @Override
    public @NotNull List<Vendor> getItems(int numberOfItems) {
        List<Vendor> result = new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getItem(i+1));
        }
        return result;
    }

    private @NotNull Vendor getItem(int itemNumber) {
        Vendor result = new Vendor(
                getUniqueId(),
                vendorNameProvider.get(),
                vendorLogoProvider.get(),
                creationDateProvider.getNow()
        );
        log.info("Vendor {} created: {}", itemNumber, result);
        return result;
    }
}
