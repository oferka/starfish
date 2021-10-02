package org.ok.starfish.data.content.provider.user;

import org.ok.starfish.model.user.Device;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface DeviceContentProvider {

    @NotNull List<Device> get(int numberOfItems);
}
