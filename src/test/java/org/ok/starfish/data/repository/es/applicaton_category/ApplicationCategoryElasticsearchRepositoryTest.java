package org.ok.starfish.data.repository.es.applicaton_category;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ok.starfish.data.sample.applicaton_category.SampleApplicationCategoryProvider;
import org.ok.starfish.model.applicaton_category.ApplicationCategory;
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
public class ApplicationCategoryElasticsearchRepositoryTest {

    @Autowired
    private ApplicationCategoryElasticsearchRepository applicationCategoryElasticsearchRepository;

    @Autowired
    private SampleApplicationCategoryProvider sampleApplicationCategoryProvider;

    private long contentCountBefore;

    private final int numberOfItemsToLoad = 10;

    @BeforeEach
    void captureContentStatus() {
        contentCountBefore = applicationCategoryElasticsearchRepository.count();
    }

    @AfterEach
    void verifyContentStatusNotChanged() {
        long contentCountAfter = applicationCategoryElasticsearchRepository.count();
        assertEquals(contentCountBefore, contentCountAfter);
    }

    @Test
    void shouldSaveItem() {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory saved = applicationCategoryElasticsearchRepository.save(item);
        assertEquals(item, saved);
        applicationCategoryElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldSaveItems() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItemsToLoad);
        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
        assertNotNull(saved);
        applicationCategoryElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindItemById() {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory saved = applicationCategoryElasticsearchRepository.save(item);
        Optional<ApplicationCategory> foundItemOptional = applicationCategoryElasticsearchRepository.findById(item.getId());
        assertTrue(foundItemOptional.isPresent());
        ApplicationCategory foundItem = foundItemOptional.get();
        assertEquals(item.getId(), foundItem.getId());
        applicationCategoryElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemById() {
        Optional<ApplicationCategory> foundItemOptional = applicationCategoryElasticsearchRepository.findById(getNonExistingId());
        assertTrue(foundItemOptional.isEmpty());
    }

    @Test
    void shouldFindItemByName() {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory saved = applicationCategoryElasticsearchRepository.save(item);
        List<ApplicationCategory> foundItems = applicationCategoryElasticsearchRepository.findByName(item.getName());
        assertFalse(foundItems.isEmpty());
        ApplicationCategory foundItem = foundItems.get(0);
        assertEquals(item.getId(), foundItem.getId());
        applicationCategoryElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByName() {
        List<ApplicationCategory> foundItems = applicationCategoryElasticsearchRepository.findByName(getNonExistingName());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindItemByCreatedDate() {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory saved = applicationCategoryElasticsearchRepository.save(item);
        List<ApplicationCategory> foundItems = applicationCategoryElasticsearchRepository.findByCreatedDate(item.getCreatedDate());
        assertFalse(foundItems.isEmpty());
        ApplicationCategory foundItem = foundItems.get(0);
        assertEquals(item.getId(), foundItem.getId());
        applicationCategoryElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByCreatedDate() {
        List<ApplicationCategory> foundItems = applicationCategoryElasticsearchRepository.findByCreatedDate(getNonExistingDate());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindAllItems() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItemsToLoad);
        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
        Iterable<ApplicationCategory> found = applicationCategoryElasticsearchRepository.findAll();
        assertNotNull(found);
        applicationCategoryElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsSortedByName() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItemsToLoad);
        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
        Iterable<ApplicationCategory> found = applicationCategoryElasticsearchRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        assertNotNull(found);
        applicationCategoryElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsSortedById() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItemsToLoad);
        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
        Iterable<ApplicationCategory> found = applicationCategoryElasticsearchRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        assertNotNull(found);
        applicationCategoryElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsPaged() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItemsToLoad);
        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
        Page<ApplicationCategory> found = applicationCategoryElasticsearchRepository.findAll(PageRequest.of(0, 4));
        assertNotNull(found);
        applicationCategoryElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsPagedAndSorted() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItemsToLoad);
        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
        Page<ApplicationCategory> found = applicationCategoryElasticsearchRepository.findAll(PageRequest.of(0, 4, Sort.by(Sort.Direction.ASC, "name")));
        assertNotNull(found);
        applicationCategoryElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldExistById() {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory saved = applicationCategoryElasticsearchRepository.save(item);
        boolean exists = applicationCategoryElasticsearchRepository.existsById(saved.getId());
        assertTrue(exists);
        applicationCategoryElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotExistById() {
        boolean exists = applicationCategoryElasticsearchRepository.existsById(getNonExistingId());
        assertFalse(exists);
    }

    @Test
    void shouldCount() {
        long countBefore = applicationCategoryElasticsearchRepository.count();
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItemsToLoad);
        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
        long countAfter = applicationCategoryElasticsearchRepository.count();
        assertEquals(countAfter, countBefore + numberOfItemsToLoad);
        applicationCategoryElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldDeleteItem() {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory saved = applicationCategoryElasticsearchRepository.save(item);
        applicationCategoryElasticsearchRepository.delete(item);
        boolean exists = applicationCategoryElasticsearchRepository.existsById(saved.getId());
        assertFalse(exists);
    }

    @Test
    void shouldDeleteById() {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory saved = applicationCategoryElasticsearchRepository.save(item);
        applicationCategoryElasticsearchRepository.deleteById(item.getId());
        boolean exists = applicationCategoryElasticsearchRepository.existsById(saved.getId());
        assertFalse(exists);
    }

    @Test
    void shouldNotDeleteById() {
        applicationCategoryElasticsearchRepository.deleteById(getNonExistingId());
    }

    @Test
    void shouldDeleteItems() {
        long countBefore = applicationCategoryElasticsearchRepository.count();
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItemsToLoad);
        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
        int numberOfItemsToDelete = 3;
        applicationCategoryElasticsearchRepository.deleteAll(items.subList(0, numberOfItemsToDelete));
        long countAfter = applicationCategoryElasticsearchRepository.count();
        assertEquals((countBefore + numberOfItemsToLoad - numberOfItemsToDelete), countAfter);
        applicationCategoryElasticsearchRepository.deleteAll(saved);
    }
}
