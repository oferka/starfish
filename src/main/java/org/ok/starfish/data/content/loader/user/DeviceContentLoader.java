package org.ok.starfish.data.content.loader.user;

import org.ok.starfish.model.user.Device;

import javax.validation.constraints.NotNull;

public interface DeviceContentLoader {

    @NotNull Iterable<Device> ensureContentLoaded();
}
