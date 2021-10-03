package org.ok.starfish.data.content.provider.device;

import org.ok.starfish.model.device.Device;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface DeviceContentProvider {

    @NotNull List<Device> get(int numberOfItems);
}
