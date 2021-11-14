package org.ok.starfish.data.sample.vendor;

import org.ok.starfish.model.vendor.Vendor;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SampleVendorProvider {

    @NotNull Vendor getItem();

    @NotNull List<Vendor> getItems(int numberOfItems);
}
