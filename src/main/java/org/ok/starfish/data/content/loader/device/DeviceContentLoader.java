package org.ok.starfish.data.content.loader.device;

import org.ok.starfish.model.device.Device;

import javax.validation.constraints.NotNull;

public interface DeviceContentLoader {

    @NotNull Iterable<Device> ensureContentLoaded();
}
