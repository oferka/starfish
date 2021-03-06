package org.ok.starfish.data.service.device;

import org.ok.starfish.model.device.Device;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface DeviceService {

    @NotNull List<Device> findAll();

    @NotNull Optional<Device> findById(@NotNull String id);

    @NotNull List<Device> findByName(@NotNull String name);

    @NotNull List<Device> findByCreatedDate(@NotNull ZonedDateTime createdDate);

    @NotNull Optional<Device> findRandom();

    long count();

    @NotNull Device save(@NotNull Device device);

    @NotNull Iterable<Device> saveAll(@NotNull Iterable<Device> devices);

    @NotNull Optional<Device> update(@NotNull String id, @NotNull Device device);

    void deleteById(@NotNull String id);

    boolean existsById(@NotNull String id);

    boolean exists(@NotNull @NotNull Device device);
}
