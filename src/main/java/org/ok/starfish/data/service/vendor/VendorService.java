package org.ok.starfish.data.service.vendor;

import org.ok.starfish.model.vendor.Vendor;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface VendorService {

    @NotNull List<Vendor> findAll();

    @NotNull Optional<Vendor> findById(@NotNull String id);

    @NotNull List<Vendor> findByName(@NotNull String name);

    @NotNull List<Vendor> findByCreatedDate(@NotNull ZonedDateTime createdDate);

    @NotNull Optional<Vendor> findRandom();

    long count();

    @NotNull Vendor save(@NotNull Vendor vendor);

    @NotNull Iterable<Vendor> saveAll(@NotNull Iterable<Vendor> vendors);

    @NotNull Optional<Vendor> update(@NotNull String id, @NotNull Vendor vendor);

    void deleteById(@NotNull String id);

    boolean existsById(@NotNull String id);

    boolean exists(@NotNull @NotNull Vendor vendor);
}
