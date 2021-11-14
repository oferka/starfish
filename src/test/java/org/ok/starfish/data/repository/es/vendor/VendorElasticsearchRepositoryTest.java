package org.ok.starfish.data.repository.es.vendor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ok.starfish.data.sample.vendor.SampleVendorProvider;
import org.ok.starfish.model.vendor.Vendor;
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
public class VendorElasticsearchRepositoryTest {

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
    void shouldSaveItem() {
        Vendor item = sampleVendorProvider.getItem();
        Vendor saved = vendorElasticsearchRepository.save(item);
        assertEquals(item, saved);
        vendorElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldSaveItems() {
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> saved = vendorElasticsearchRepository.saveAll(items);
        assertNotNull(saved);
        vendorElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindItemById() {
        Vendor item = sampleVendorProvider.getItem();
        Vendor saved = vendorElasticsearchRepository.save(item);
        Optional<Vendor> foundItemOptional = vendorElasticsearchRepository.findById(item.getId());
        assertTrue(foundItemOptional.isPresent());
        Vendor foundItem = foundItemOptional.get();
        assertEquals(item.getId(), foundItem.getId());
        vendorElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemById() {
        Optional<Vendor> foundItemOptional = vendorElasticsearchRepository.findById(getNonExistingId());
        assertTrue(foundItemOptional.isEmpty());
    }

    @Test
    void shouldFindItemByName() {
        Vendor item = sampleVendorProvider.getItem();
        Vendor saved = vendorElasticsearchRepository.save(item);
        List<Vendor> foundItems = vendorElasticsearchRepository.findByName(item.getName());
        assertFalse(foundItems.isEmpty());
        Vendor foundItem = foundItems.get(0);
        assertEquals(item.getId(), foundItem.getId());
        vendorElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByName() {
        List<Vendor> foundItems = vendorElasticsearchRepository.findByName(getNonExistingName());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindItemByCreatedDate() {
        Vendor item = sampleVendorProvider.getItem();
        Vendor saved = vendorElasticsearchRepository.save(item);
        List<Vendor> foundItems = vendorElasticsearchRepository.findByCreatedDate(item.getCreatedDate());
        assertFalse(foundItems.isEmpty());
        Vendor foundItem = foundItems.get(0);
        assertEquals(item.getId(), foundItem.getId());
        vendorElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByCreatedDate() {
        List<Vendor> foundItems = vendorElasticsearchRepository.findByCreatedDate(getNonExistingDate());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindAllItems() {
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> saved = vendorElasticsearchRepository.saveAll(items);
        Iterable<Vendor> found = vendorElasticsearchRepository.findAll();
        assertNotNull(found);
        vendorElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsSortedByName() {
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> saved = vendorElasticsearchRepository.saveAll(items);
        Iterable<Vendor> found = vendorElasticsearchRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        assertNotNull(found);
        vendorElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsSortedById() {
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> saved = vendorElasticsearchRepository.saveAll(items);
        Iterable<Vendor> found = vendorElasticsearchRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        assertNotNull(found);
        vendorElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsPaged() {
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> saved = vendorElasticsearchRepository.saveAll(items);
        Page<Vendor> found = vendorElasticsearchRepository.findAll(PageRequest.of(0, 4));
        assertNotNull(found);
        vendorElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsPagedAndSorted() {
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> saved = vendorElasticsearchRepository.saveAll(items);
        Page<Vendor> found = vendorElasticsearchRepository.findAll(PageRequest.of(0, 4, Sort.by(Sort.Direction.ASC, "name")));
        assertNotNull(found);
        vendorElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldExistById() {
        Vendor item = sampleVendorProvider.getItem();
        Vendor saved = vendorElasticsearchRepository.save(item);
        boolean exists = vendorElasticsearchRepository.existsById(saved.getId());
        assertTrue(exists);
        vendorElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotExistById() {
        boolean exists = vendorElasticsearchRepository.existsById(getNonExistingId());
        assertFalse(exists);
    }

    @Test
    void shouldCount() {
        long countBefore = vendorElasticsearchRepository.count();
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> saved = vendorElasticsearchRepository.saveAll(items);
        long countAfter = vendorElasticsearchRepository.count();
        assertEquals(countAfter, countBefore + numberOfItemsToLoad);
        vendorElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldDeleteItem() {
        Vendor item = sampleVendorProvider.getItem();
        Vendor saved = vendorElasticsearchRepository.save(item);
        vendorElasticsearchRepository.delete(item);
        boolean exists = vendorElasticsearchRepository.existsById(saved.getId());
        assertFalse(exists);
    }

    @Test
    void shouldDeleteById() {
        Vendor item = sampleVendorProvider.getItem();
        Vendor saved = vendorElasticsearchRepository.save(item);
        vendorElasticsearchRepository.deleteById(item.getId());
        boolean exists = vendorElasticsearchRepository.existsById(saved.getId());
        assertFalse(exists);
    }

    @Test
    void shouldNotDeleteById() {
        vendorElasticsearchRepository.deleteById(getNonExistingId());
    }

    @Test
    void shouldDeleteItems() {
        long countBefore = vendorElasticsearchRepository.count();
        List<Vendor> items = sampleVendorProvider.getItems(numberOfItemsToLoad);
        Iterable<Vendor> saved = vendorElasticsearchRepository.saveAll(items);
        int numberOfItemsToDelete = 3;
        vendorElasticsearchRepository.deleteAll(items.subList(0, numberOfItemsToDelete));
        long countAfter = vendorElasticsearchRepository.count();
        assertEquals((countBefore + numberOfItemsToLoad - numberOfItemsToDelete), countAfter);
        vendorElasticsearchRepository.deleteAll(saved);
    }
}
