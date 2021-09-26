package org.ok.starfish.data.sample.user;

import org.ok.starfish.model.user.Device;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SampleDeviceProvider {

    @NotNull Device getItem();

    @NotNull List<Device> getItems(int numberOfItems);
}
