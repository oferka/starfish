package org.ok.starfish.data.sample.user;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.service.user.UserService;
import org.ok.starfish.model.user.Device;
import org.ok.starfish.model.user.User;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.ok.starfish.data.TestDataUtils.getUniqueId;

@Service
@Slf4j
public class MockSampleDeviceProvider implements SampleDeviceProvider {

    private final UserService userService;

    public MockSampleDeviceProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public @NotNull Device getItem() {
        return getItem(1);
    }

    @Override
    public @NotNull List<Device> getItems(int numberOfItems) {
        List<Device> result = new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(getItem(i+1));
        }
        return result;
    }

    private @NotNull Device getItem(int itemNumber) {
        Optional<User> user = userService.findRandom();
        if(user.isPresent()) {
            Device result = new Device(getUniqueId(), getRandomDeviceName(), user.get());
            log.info("Device {} created: {}", itemNumber, result);
            return result;
        }
        throw new RuntimeException("Failed to create device. Could not find a valid user");
    }

    private @NotNull String getRandomDeviceName() {
        return new Faker().university().name();
    }
}
