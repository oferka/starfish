package org.ok.starfish.data.content.verifier.vendor;

import org.ok.starfish.model.vendor.Vendor;

import javax.validation.constraints.NotNull;

public interface VendorContentVerifier {

    @NotNull Iterable<Vendor> findLoaded(@NotNull Iterable<Vendor> vendors);

    @NotNull Iterable<Vendor> findNotLoaded(@NotNull Iterable<Vendor> vendors);
}
