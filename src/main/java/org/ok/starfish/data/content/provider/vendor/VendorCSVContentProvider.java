package org.ok.starfish.data.content.provider.vendor;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.IdProvider;
import org.ok.starfish.model.vendor.Vendor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Profile({"csv-vendors-provider", "default"})
public class VendorCSVContentProvider implements VendorContentProvider {

    private final IdProvider idProvider;

    private final VendorsCsvReader csvReader;

    private final CreationDateProvider creationDateProvider;

    public VendorCSVContentProvider(IdProvider idProvider,
                                         VendorsCsvReader csvReader,
                                         CreationDateProvider creationDateProvider) {
        this.idProvider = idProvider;
        this.csvReader = csvReader;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<Vendor> get(int numberOfItems) {
        List<VendorLine> lines = csvReader.read();
        List<Vendor> result =  new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getVendor(lines.get(i)));
        }
        log.info("{} vendors provided", result.size());
        return result;
    }

    private @NotNull Vendor getVendor(@NotNull VendorLine line) {
        return new Vendor(
                idProvider.getRandom(),
                line.getName(),
                line.getLogo(),
                creationDateProvider.getNow()
        );
    }
}
