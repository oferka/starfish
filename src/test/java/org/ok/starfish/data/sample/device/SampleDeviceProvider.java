package org.ok.starfish.data.sample.device;

import org.ok.starfish.model.device.Device;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SampleDeviceProvider {

    @NotNull Device getItem();

    @NotNull List<Device> getItems(int numberOfItems);
}
