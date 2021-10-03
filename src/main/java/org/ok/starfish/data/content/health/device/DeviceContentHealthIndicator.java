package org.ok.starfish.data.content.health.device;

import org.ok.starfish.data.service.device.DeviceService;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("deviceContent")
@ConditionalOnEnabledHealthIndicator("deviceContent")
public class DeviceContentHealthIndicator implements HealthIndicator {

    private final DeviceService deviceService;

    public DeviceContentHealthIndicator(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public Health health() {
        Health.Builder status;
        try {
            long count = deviceService.count();
            status = (count > 0)?Health.up():Health.down();
            status.withDetail("number_of_devices", count);
        }
        catch (Exception e) {
            status = Health.down(e);
        }
        return status.build();
    }
}
