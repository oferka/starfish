package org.ok.starfish.data.content.verifier.device;

import org.ok.starfish.model.device.Device;

import javax.validation.constraints.NotNull;

public interface DeviceContentVerifier {

    @NotNull Iterable<Device> findLoaded(@NotNull Iterable<Device> devices);

    @NotNull Iterable<Device> findNotLoaded(@NotNull Iterable<Device> devices);
}
