package org.ok.starfish.data.repository.es.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ok.starfish.data.sample.application.SampleApplicationProvider;
import org.ok.starfish.model.application.Application;
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
public class ApplicationElasticsearchRepositoryTest {

    @Autowired
    private ApplicationElasticsearchRepository applicationElasticsearchRepository;

    @Autowired
    private SampleApplicationProvider sampleApplicationProvider;

    private long contentCountBefore;

    private final int numberOfItemsToLoad = 10;

    @BeforeEach
    void captureContentStatus() {
        contentCountBefore = applicationElasticsearchRepository.count();
    }

    @AfterEach
    void verifyContentStatusNotChanged() {
        long contentCountAfter = applicationElasticsearchRepository.count();
        assertEquals(contentCountBefore, contentCountAfter);
    }

    @Test
    void shouldSaveItem() {
        Application item = sampleApplicationProvider.getItem();
        Application saved = applicationElasticsearchRepository.save(item);
        assertEquals(item, saved);
        applicationElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldSaveItems() {
        List<Application> items = sampleApplicationProvider.getItems(numberOfItemsToLoad);
        Iterable<Application> saved = applicationElasticsearchRepository.saveAll(items);
        assertNotNull(saved);
        applicationElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindItemById() {
        Application item = sampleApplicationProvider.getItem();
        Application saved = applicationElasticsearchRepository.save(item);
        Optional<Application> foundItemOptional = applicationElasticsearchRepository.findById(item.getId());
        assertTrue(foundItemOptional.isPresent());
        Application foundItem = foundItemOptional.get();
        assertEquals(item.getId(), foundItem.getId());
        applicationElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemById() {
        Optional<Application> foundItemOptional = applicationElasticsearchRepository.findById(getNonExistingId());
        assertTrue(foundItemOptional.isEmpty());
    }

    @Test
    void shouldFindItemByName() {
        Application item = sampleApplicationProvider.getItem();
        Application saved = applicationElasticsearchRepository.save(item);
        List<Application> foundItems = applicationElasticsearchRepository.findByName(item.getName());
        assertFalse(foundItems.isEmpty());
        Application foundItem = foundItems.get(0);
        assertEquals(item.getId(), foundItem.getId());
        applicationElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByName() {
        List<Application> foundItems = applicationElasticsearchRepository.findByName(getNonExistingName());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindItemByCreatedDate() {
        Application item = sampleApplicationProvider.getItem();
        Application saved = applicationElasticsearchRepository.save(item);
        List<Application> foundItems = applicationElasticsearchRepository.findByCreatedDate(item.getCreatedDate());
        assertFalse(foundItems.isEmpty());
        Application foundItem = foundItems.get(0);
        assertEquals(item.getId(), foundItem.getId());
        applicationElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByCreatedDate() {
        List<Application> foundItems = applicationElasticsearchRepository.findByCreatedDate(getNonExistingDate());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindAllItems() {
        List<Application> items = sampleApplicationProvider.getItems(numberOfItemsToLoad);
        Iterable<Application> saved = applicationElasticsearchRepository.saveAll(items);
        Iterable<Application> found = applicationElasticsearchRepository.findAll();
        assertNotNull(found);
        applicationElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsSortedByName() {
        List<Application> items = sampleApplicationProvider.getItems(numberOfItemsToLoad);
        Iterable<Application> saved = applicationElasticsearchRepository.saveAll(items);
        Iterable<Application> found = applicationElasticsearchRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        assertNotNull(found);
        applicationElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsSortedById() {
        List<Application> items = sampleApplicationProvider.getItems(numberOfItemsToLoad);
        Iterable<Application> saved = applicationElasticsearchRepository.saveAll(items);
        Iterable<Application> found = applicationElasticsearchRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        assertNotNull(found);
        applicationElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsPaged() {
        List<Application> items = sampleApplicationProvider.getItems(numberOfItemsToLoad);
        Iterable<Application> saved = applicationElasticsearchRepository.saveAll(items);
        Page<Application> found = applicationElasticsearchRepository.findAll(PageRequest.of(0, 4));
        assertNotNull(found);
        applicationElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsPagedAndSorted() {
        List<Application> items = sampleApplicationProvider.getItems(numberOfItemsToLoad);
        Iterable<Application> saved = applicationElasticsearchRepository.saveAll(items);
        Page<Application> found = applicationElasticsearchRepository.findAll(PageRequest.of(0, 4, Sort.by(Sort.Direction.ASC, "name")));
        assertNotNull(found);
        applicationElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldExistById() {
        Application item = sampleApplicationProvider.getItem();
        Application saved = applicationElasticsearchRepository.save(item);
        boolean exists = applicationElasticsearchRepository.existsById(saved.getId());
        assertTrue(exists);
        applicationElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotExistById() {
        boolean exists = applicationElasticsearchRepository.existsById(getNonExistingId());
        assertFalse(exists);
    }

    @Test
    void shouldCount() {
        long countBefore = applicationElasticsearchRepository.count();
        List<Application> items = sampleApplicationProvider.getItems(numberOfItemsToLoad);
        Iterable<Application> saved = applicationElasticsearchRepository.saveAll(items);
        long countAfter = applicationElasticsearchRepository.count();
        assertEquals(countAfter, countBefore + numberOfItemsToLoad);
        applicationElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldDeleteItem() {
        Application item = sampleApplicationProvider.getItem();
        Application saved = applicationElasticsearchRepository.save(item);
        applicationElasticsearchRepository.delete(item);
        boolean exists = applicationElasticsearchRepository.existsById(saved.getId());
        assertFalse(exists);
    }

    @Test
    void shouldDeleteById() {
        Application item = sampleApplicationProvider.getItem();
        Application saved = applicationElasticsearchRepository.save(item);
        applicationElasticsearchRepository.deleteById(item.getId());
        boolean exists = applicationElasticsearchRepository.existsById(saved.getId());
        assertFalse(exists);
    }

    @Test
    void shouldNotDeleteById() {
        applicationElasticsearchRepository.deleteById(getNonExistingId());
    }

    @Test
    void shouldDeleteItems() {
        long countBefore = applicationElasticsearchRepository.count();
        List<Application> items = sampleApplicationProvider.getItems(numberOfItemsToLoad);
        Iterable<Application> saved = applicationElasticsearchRepository.saveAll(items);
        int numberOfItemsToDelete = 3;
        applicationElasticsearchRepository.deleteAll(items.subList(0, numberOfItemsToDelete));
        long countAfter = applicationElasticsearchRepository.count();
        assertEquals((countBefore + numberOfItemsToLoad - numberOfItemsToDelete), countAfter);
        applicationElasticsearchRepository.deleteAll(saved);
    }
}
