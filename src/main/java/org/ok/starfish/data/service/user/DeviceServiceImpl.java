package org.ok.starfish.data.service.user;

import org.apache.commons.lang3.RandomUtils;
import org.ok.starfish.data.repository.es.user.DeviceElasticsearchRepository;
import org.ok.starfish.model.user.Device;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceElasticsearchRepository deviceElasticsearchRepository;

    public DeviceServiceImpl(DeviceElasticsearchRepository deviceElasticsearchRepository) {
        this.deviceElasticsearchRepository = deviceElasticsearchRepository;
    }

    @Override
    public @NotNull List<Device> findAll() {
        Iterable<Device> items = deviceElasticsearchRepository.findAll();
        return StreamSupport
                .stream(items.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public @NotNull Optional<Device> findById(@NotNull String id) {
        return deviceElasticsearchRepository.findById(id);
    }

    @Override
    public @NotNull List<Device> findByName(@NotNull String name) {
        return deviceElasticsearchRepository.findByName(name);
    }

    @Override
    public Optional<Device> findRandom() {
        List<Device> items = findAll();
        if(items.isEmpty()) {
            return Optional.empty();
        }
        Device item = items.get(RandomUtils.nextInt(0, items.size()));
        return Optional.of(item);
    }

    @Override
    public @NotNull Device save(@NotNull Device device) {
        return deviceElasticsearchRepository.save(device);
    }

    @Override
    public @NotNull Iterable<Device> saveAll(@NotNull Iterable<Device> devices) {
        return deviceElasticsearchRepository.saveAll(devices);
    }

    @Override
    public Optional<Device> update(@NotNull String id, @NotNull Device device) {
        Optional<Device> result = Optional.empty();
        if(existsById(id)) {
            result = Optional.of(save(device));
        }
        return result;
    }

    @Override
    public void deleteById(@NotNull String id) {
        deviceElasticsearchRepository.deleteById(id);
    }

    @Override
    public long count() {
        return deviceElasticsearchRepository.count();
    }

    @Override
    public boolean existsById(@NotNull String id) {
        return deviceElasticsearchRepository.existsById(id);
    }

    @Override
    public boolean exists(@NotNull Device device) {
        List<Device> devices = deviceElasticsearchRepository.findByName(device.getName());
        return (!devices.isEmpty());
    }
}
