package org.ok.starfish.data.service.device;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ok.starfish.data.repository.es.device.DeviceElasticsearchRepository;
import org.ok.starfish.data.sample.device.SampleDeviceProvider;
import org.ok.starfish.model.device.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.ok.starfish.data.TestDataUtils.*;

@SpringBootTest
public class DeviceServiceTest {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceElasticsearchRepository deviceElasticsearchRepository;

    @Autowired
    private SampleDeviceProvider sampleDeviceProvider;

    private long contentCountBefore;

    private final int numberOfItemsToLoad = 10;

    @BeforeEach
    void captureContentStatus() {
        contentCountBefore = deviceElasticsearchRepository.count();
    }

    @AfterEach
    void verifyContentStatusNotChanged() {
        long contentCountAfter = deviceElasticsearchRepository.count();
        assertEquals(contentCountBefore, contentCountAfter);
    }

    @Test
    public void shouldFindAll() {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> savedItems = deviceElasticsearchRepository.saveAll(items);
        List<Device> foundItems = deviceService.findAll();
        assertNotNull(foundItems);
        deviceElasticsearchRepository.deleteAll(savedItems);
    }

    @Test
    public void shouldFindById() {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        String id = items.get(0).getId();
        Optional<Device> found = deviceService.findById(id);
        assertTrue(found.isPresent());
        assertEquals(id, found.get().getId());
        deviceElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindById() {
        Optional<Device> found = deviceService.findById(getNonExistingId());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByName() {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        String name = items.get(0).getName();
        List<Device> found = deviceService.findByName(name);
        assertFalse(found.isEmpty());
        assertEquals(name, found.get(0).getName());
        deviceElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindByName() {
        List<Device> found = deviceService.findByName(getNonExistingName());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByCreatedDate() {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        ZonedDateTime createdDate = items.get(0).getCreatedDate();
        List<Device> found = deviceService.findByCreatedDate(createdDate);
        assertFalse(found.isEmpty());
        deviceElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindByCreatedDate() {
        List<Device> found = deviceService.findByCreatedDate(getNonExistingCreatedDate());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindRandom() {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        Optional<Device> found = deviceService.findRandom();
        assertTrue(found.isPresent());
        deviceElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldSave() {
        Device item = sampleDeviceProvider.getItem();
        Device saved = deviceService.save(item);
        assertEquals(saved, item);
        deviceElasticsearchRepository.delete(item);
    }

    @Test
    public void shouldSaveAll() {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceService.saveAll(items);
        assertNotNull(saved);
        deviceElasticsearchRepository.deleteAll(items);
    }

    @Test
    public void shouldUpdate() {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        Device item = items.get(0);
        Optional<Device> updated = deviceService.update(item.getId(), item);
        assertTrue(updated.isPresent());
        deviceElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotUpdate() {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        Device item = items.get(0);
        Optional<Device> updated = deviceService.update(getNonExistingId(), item);
        assertTrue(updated.isEmpty());
        deviceElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldDeleteById() {
        Device item = sampleDeviceProvider.getItem();
        Device saved = deviceElasticsearchRepository.save(item);
        String id = saved.getId();
        deviceService.deleteById(id);
        boolean exists = deviceElasticsearchRepository.existsById(id);
        assertFalse(exists);
    }

    @Test
    public void shouldNotDeleteById() {
        deviceService.deleteById(getNonExistingId());
    }

    @Test
    void shouldCount() {
        long countBefore = deviceElasticsearchRepository.count();
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        long countAfter = deviceService.count();
        assertEquals(countBefore + numberOfItemsToLoad, countAfter);
        deviceElasticsearchRepository.deleteAll(saved);
    }
}
