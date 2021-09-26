package org.ok.starfish.data.content.verifier.user;

import org.ok.starfish.model.user.Device;

import javax.validation.constraints.NotNull;

public interface DeviceContentVerifier {

    @NotNull Iterable<Device> findLoaded(@NotNull Iterable<Device> devices);

    @NotNull Iterable<Device> findNotLoaded(@NotNull Iterable<Device> devices);
}
