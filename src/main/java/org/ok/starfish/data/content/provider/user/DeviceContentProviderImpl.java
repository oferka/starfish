package org.ok.starfish.data.content.provider.user;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.content.provider.CreationDateProvider;
import org.ok.starfish.data.service.user.UserService;
import org.ok.starfish.model.user.Device;
import org.ok.starfish.model.user.User;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Arrays.asList;

@Service
@Slf4j
public class DeviceContentProviderImpl implements DeviceContentProvider {

    private final UserService userService;

    private final CreationDateProvider creationDateProvider;

    public DeviceContentProviderImpl(UserService userService, CreationDateProvider creationDateProvider) {
        this.userService = userService;
        this.creationDateProvider = creationDateProvider;
    }

    @Override
    public List<Device> get() {
        List<Device> result = asList(
                getDevice("Device 1"),
                getDevice("Device 2"),
                getDevice("Device 3"),
                getDevice("Device 4"),
                getDevice("Device 5"),
                getDevice("Device 6"),
                getDevice("Device 7"),
                getDevice("Device 8"),
                getDevice("Device 9"),
                getDevice("Device 10"),
                getDevice("Device 11"),
                getDevice("Device 12"),
                getDevice("Device 13"),
                getDevice("Device 14"),
                getDevice("Device 15"),
                getDevice("Device 16"),
                getDevice("Device 17"),
                getDevice("Device 18"),
                getDevice("Device 19"),
                getDevice("Device 20"),
                getDevice("Device 21"),
                getDevice("Device 22"),
                getDevice("Device 23"),
                getDevice("Device 24"),
                getDevice("Device 25"),
                getDevice("Device 26"),
                getDevice("Device 27"),
                getDevice("Device 28"),
                getDevice("Device 29"),
                getDevice("Device 30"),
                getDevice("Device 31"),
                getDevice("Device 32"),
                getDevice("Device 33"),
                getDevice("Device 34"),
                getDevice("Device 35")
        );
        log.info("{} users provided", result.size());
        return result;
    }

    private @NotNull Device getDevice(@NotNull String name) {
        String id = UUID.randomUUID().toString();
        Optional<User> user = userService.findRandom();
        if(user.isPresent()) {
            return new Device(id, name, creationDateProvider.getNow(), user.get());
        }
        throw new RuntimeException("Failed to create device. Could not find a valid user");
    }
}
