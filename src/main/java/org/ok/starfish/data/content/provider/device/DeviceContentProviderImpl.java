package org.ok.starfish.data.content.provider.device;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.content.provider.IdProvider;
import org.ok.starfish.data.service.user.UserService;
import org.ok.starfish.model.device.Device;
import org.ok.starfish.model.user.User;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DeviceContentProviderImpl implements DeviceContentProvider {

    private final UserService userService;

    private final IdProvider idProvider;

    private final DeviceNameProvider deviceNameProvider;

    private final CreationDateProvider creationDateProvider;

    public DeviceContentProviderImpl(UserService userService,
                                     IdProvider idProvider,
                                     DeviceNameProvider userNameProvider,
                                     CreationDateProvider creationDateProvider) {
        this.userService = userService;
        this.idProvider = idProvider;
        this.deviceNameProvider = userNameProvider;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<Device> get(int numberOfItems) {
        List<Device> result =  new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getDevice());
        }
        log.info("{} devices provided", result.size());
        return result;
    }

    private @NotNull Device getDevice() {
        Optional<User> user = userService.findRandom();
        if(user.isPresent()) {
            return new Device(idProvider.getRandom(), deviceNameProvider.get(), creationDateProvider.getNow(), user.get());
        }
            throw new RuntimeException("Failed to create device. Could not find a valid user");
    }
}
