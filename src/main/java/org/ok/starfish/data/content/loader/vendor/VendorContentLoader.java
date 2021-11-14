package org.ok.starfish.data.content.loader.vendor;

import org.ok.starfish.model.vendor.Vendor;

import javax.validation.constraints.NotNull;

public interface VendorContentLoader {

    @NotNull Iterable<Vendor> ensureContentLoaded();
}
