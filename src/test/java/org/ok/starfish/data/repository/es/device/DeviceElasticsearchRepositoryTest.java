package org.ok.starfish.data.repository.es.device;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ok.starfish.data.sample.device.SampleDeviceProvider;
import org.ok.starfish.model.device.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.ok.starfish.data.TestDataUtils.*;

@SpringBootTest
public class DeviceElasticsearchRepositoryTest {

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
    void shouldSaveItem() {
        Device item = sampleDeviceProvider.getItem();
        Device saved = deviceElasticsearchRepository.save(item);
        assertEquals(item, saved);
        deviceElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldSaveItems() {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        assertNotNull(saved);
        deviceElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindItemById() {
        Device item = sampleDeviceProvider.getItem();
        Device saved = deviceElasticsearchRepository.save(item);
        Optional<Device> foundItemOptional = deviceElasticsearchRepository.findById(item.getId());
        assertTrue(foundItemOptional.isPresent());
        Device foundItem = foundItemOptional.get();
        assertEquals(item.getId(), foundItem.getId());
        deviceElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemById() {
        Optional<Device> foundItemOptional = deviceElasticsearchRepository.findById(getNonExistingId());
        assertTrue(foundItemOptional.isEmpty());
    }

    @Test
    void shouldFindItemByName() {
        Device item = sampleDeviceProvider.getItem();
        Device saved = deviceElasticsearchRepository.save(item);
        List<Device> foundItems = deviceElasticsearchRepository.findByName(item.getName());
        assertFalse(foundItems.isEmpty());
        Device foundItem = foundItems.get(0);
        assertEquals(item.getId(), foundItem.getId());
        deviceElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByName() {
        List<Device> foundItems = deviceElasticsearchRepository.findByName(getNonExistingName());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindItemByCreatedDate() {
        Device item = sampleDeviceProvider.getItem();
        Device saved = deviceElasticsearchRepository.save(item);
        List<Device> foundItems = deviceElasticsearchRepository.findByCreatedDate(item.getCreatedDate());
        assertFalse(foundItems.isEmpty());
        Device foundItem = foundItems.get(0);
        assertEquals(item.getId(), foundItem.getId());
        deviceElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByCreatedDate() {
        List<Device> foundItems = deviceElasticsearchRepository.findByCreatedDate(getNonExistingCreatedDate());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindAllItems() {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        Iterable<Device> found = deviceElasticsearchRepository.findAll();
        assertNotNull(found);
        deviceElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsSortedByName() {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        Iterable<Device> found = deviceElasticsearchRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        assertNotNull(found);
        deviceElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsSortedById() {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        Iterable<Device> found = deviceElasticsearchRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        assertNotNull(found);
        deviceElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsPaged() {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        Page<Device> found = deviceElasticsearchRepository.findAll(PageRequest.of(0, 4));
        assertNotNull(found);
        deviceElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsPagedAndSorted() {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        Page<Device> found = deviceElasticsearchRepository.findAll(PageRequest.of(0, 4, Sort.by(Sort.Direction.ASC, "name")));
        assertNotNull(found);
        deviceElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldExistById() {
        Device item = sampleDeviceProvider.getItem();
        Device saved = deviceElasticsearchRepository.save(item);
        boolean exists = deviceElasticsearchRepository.existsById(saved.getId());
        assertTrue(exists);
        deviceElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotExistById() {
        boolean exists = deviceElasticsearchRepository.existsById(getNonExistingId());
        assertFalse(exists);
    }

    @Test
    void shouldCount() {
        long countBefore = deviceElasticsearchRepository.count();
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        long countAfter = deviceElasticsearchRepository.count();
        assertEquals(countAfter, countBefore + numberOfItemsToLoad);
        deviceElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldDeleteItem() {
        Device item = sampleDeviceProvider.getItem();
        Device saved = deviceElasticsearchRepository.save(item);
        deviceElasticsearchRepository.delete(item);
        boolean exists = deviceElasticsearchRepository.existsById(saved.getId());
        assertFalse(exists);
    }

    @Test
    void shouldDeleteById() {
        Device item = sampleDeviceProvider.getItem();
        Device saved = deviceElasticsearchRepository.save(item);
        deviceElasticsearchRepository.deleteById(item.getId());
        boolean exists = deviceElasticsearchRepository.existsById(saved.getId());
        assertFalse(exists);
    }

    @Test
    void shouldNotDeleteById() {
        deviceElasticsearchRepository.deleteById(getNonExistingId());
    }

    @Test
    void shouldDeleteItems() {
        long countBefore = deviceElasticsearchRepository.count();
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        int numberOfItemsToDelete = 3;
        deviceElasticsearchRepository.deleteAll(items.subList(0, numberOfItemsToDelete));
        long countAfter = deviceElasticsearchRepository.count();
        assertEquals((countBefore + numberOfItemsToLoad - numberOfItemsToDelete), countAfter);
        deviceElasticsearchRepository.deleteAll(saved);
    }
}
