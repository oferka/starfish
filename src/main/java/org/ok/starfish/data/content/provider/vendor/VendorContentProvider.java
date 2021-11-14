package org.ok.starfish.data.content.provider.vendor;

import org.ok.starfish.model.vendor.Vendor;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface VendorContentProvider {

    @NotNull List<Vendor> get(int numberOfItems);
}
