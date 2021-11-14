package org.ok.starfish.data.service.vendor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ok.starfish.data.repository.es.vendor.VendorElasticsearchRepository;
import org.ok.starfish.data.sample.vendor.SampleVendorProvider;
import org.ok.starfish.model.vendor.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.ok.starfish.data.TestDataUtils.*;

@SpringBootTest
public class VendorServiceTest {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private VendorElasticsearchRepository vendorElasticsearchRepository;

    @Autowired
    private SampleVendorProvider sampleVendorProvider;

    private long contentCountBefore;

    private final int numberOfItemsToLoad = 10;

    @BeforeEach
    void captureContentStatus() {
        contentCountBefore = vendorElasticsearchRepository.count();
    }

    @AfterEach
    void verifyContentStatusNotChanged() {
        long contentCountAfter = vendorElasticsearchRepository.count();
        assertEquals(contentCountBefore, contentCountAfter);
    }

    @Test
    public void shouldFindAll() {
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> savedItems = vendorElasticsearchRepository.saveAll(items);
        List<Vendor> foundItems = vendorService.findAll();
        assertNotNull(foundItems);
        vendorElasticsearchRepository.deleteAll(savedItems);
    }

    @Test
    public void shouldFindById() {
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> saved = vendorElasticsearchRepository.saveAll(items);
        String id = items.get(0).getId();
        Optional<Vendor> found = vendorService.findById(id);
        assertTrue(found.isPresent());
        assertEquals(id, found.get().getId());
        vendorElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindById() {
        Optional<Vendor> found = vendorService.findById(getNonExistingId());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByName() {
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> saved = vendorElasticsearchRepository.saveAll(items);
        String name = items.get(0).getName();
        List<Vendor> found = vendorService.findByName(name);
        assertFalse(found.isEmpty());
        assertEquals(name, found.get(0).getName());
        vendorElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindByName() {
        List<Vendor> found = vendorService.findByName(getNonExistingName());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByCreatedDate() {
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> saved = vendorElasticsearchRepository.saveAll(items);
        ZonedDateTime createdDate = items.get(0).getCreatedDate();
        List<Vendor> found = vendorService.findByCreatedDate(createdDate);
        assertFalse(found.isEmpty());
        vendorElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindByCreatedDate() {
        List<Vendor> found = vendorService.findByCreatedDate(getNonExistingDate());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindRandom() {
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> saved = vendorElasticsearchRepository.saveAll(items);
        Optional<Vendor> found = vendorService.findRandom();
        assertTrue(found.isPresent());
        vendorElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldSave() {
        Vendor item = sampleVendorProvider.getItem();
        Vendor saved = vendorService.save(item);
        assertEquals(saved, item);
        vendorElasticsearchRepository.delete(item);
    }

    @Test
    public void shouldSaveAll() {
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> saved = vendorService.saveAll(items);
        assertNotNull(saved);
        vendorElasticsearchRepository.deleteAll(items);
    }

    @Test
    public void shouldUpdate() {
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> saved = vendorElasticsearchRepository.saveAll(items);
        Vendor item = items.get(0);
        Optional<Vendor> updated = vendorService.update(item.getId(), item);
        assertTrue(updated.isPresent());
        vendorElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotUpdate() {
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> saved = vendorElasticsearchRepository.saveAll(items);
        Vendor item = items.get(0);
        Optional<Vendor> updated = vendorService.update(getNonExistingId(), item);
        assertTrue(updated.isEmpty());
        vendorElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldDeleteById() {
        Vendor item = sampleVendorProvider.getItem();
        Vendor saved = vendorElasticsearchRepository.save(item);
        String id = saved.getId();
        vendorService.deleteById(id);
        boolean exists = vendorElasticsearchRepository.existsById(id);
        assertFalse(exists);
    }

    @Test
    public void shouldNotDeleteById() {
        vendorService.deleteById(getNonExistingId());
    }

    @Test
    void shouldCount() {
        long countBefore = vendorElasticsearchRepository.count();
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> saved = vendorElasticsearchRepository.saveAll(items);
        long countAfter = vendorService.count();
        assertEquals(countBefore + numberOfItemsToLoad, countAfter);
        vendorElasticsearchRepository.deleteAll(saved);
    }
}
