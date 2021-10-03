package org.ok.starfish.data.content.loader.device;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.ok.starfish.data.content.provider.device.DeviceContentProvider;
import org.ok.starfish.data.content.verifier.device.DeviceContentVerifier;
import org.ok.starfish.data.service.device.DeviceService;
import org.ok.starfish.model.device.Device;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Slf4j
public class DeviceContentLoaderImpl implements DeviceContentLoader {

    private final DeviceContentProvider deviceContentProvider;
    private final DeviceContentVerifier deviceContentVerifier;
    private final DeviceService deviceService;

    public DeviceContentLoaderImpl(DeviceContentProvider deviceContentProvider, DeviceContentVerifier deviceContentVerifier, DeviceService deviceService) {
        this.deviceContentProvider = deviceContentProvider;
        this.deviceContentVerifier = deviceContentVerifier;
        this.deviceService = deviceService;
    }

    @Override
    public @NotNull Iterable<Device> ensureContentLoaded() {
        List<Device> content = deviceContentProvider.get(100);
        Iterable<Device> unloadedContent = deviceContentVerifier.findNotLoaded(content);
        if(!IterableUtils.isEmpty(unloadedContent)) {
            Iterable<Device> saved = deviceService.saveAll(unloadedContent);
            log.info("{} devices saved", IterableUtils.size(saved));
        }
        log.info("{} devices ensured loaded", content.size());
        return content;
    }
}
