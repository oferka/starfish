package org.ok.starfish.data.content.verifier.device;

import lombok.extern.slf4j.Slf4j;
import org.ok.starfish.data.service.device.DeviceService;
import org.ok.starfish.model.device.Device;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DeviceContentVerifierImpl implements DeviceContentVerifier {

    private final DeviceService deviceService;

    public DeviceContentVerifierImpl(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public Iterable<Device> findLoaded(Iterable<Device> devices) {
        List<Device> result = new ArrayList<>();
        for(Device device : devices) {
            if(deviceService.exists(device)) {
                result.add(device);
            }
        }
        log.info("{} devices found as loaded", result.size());
        return result;
    }

    @Override
    public Iterable<Device> findNotLoaded(Iterable<Device> devices) {
        List<Device> result = new ArrayList<>();
        for(Device device : devices) {
            if(!deviceService.exists(device)) {
                result.add(device);
            }
        }
        log.info("{} devices found as unloaded", result.size());
        return result;
    }
}
