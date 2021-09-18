package org.ok.starfish.data.repository.es.application;

import org.junit.jupiter.api.Test;
import org.ok.starfish.data.sample.application.SampleApplicationCategoryProvider;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApplicationCategoryElasticsearchRepositoryTest {

    @Autowired
    private ApplicationCategoryElasticsearchRepository applicationCategoryElasticsearchRepository;

    @Autowired
    private SampleApplicationCategoryProvider sampleApplicationCategoryProvider;

    private final int numberOfItems = 10;

    @Test
    void shouldSaveItem() {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory savedItem = applicationCategoryElasticsearchRepository.save(item);
        assertEquals(item, savedItem);
        applicationCategoryElasticsearchRepository.delete(savedItem);
    }

    @Test
    void shouldSaveItems() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> savedItems = applicationCategoryElasticsearchRepository.saveAll(items);
        assertNotNull(savedItems);
        applicationCategoryElasticsearchRepository.deleteAll(savedItems);
    }

    @Test
    void shouldFindItemById() {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory savedItem = applicationCategoryElasticsearchRepository.save(item);
        Optional<ApplicationCategory> foundItemOptional = applicationCategoryElasticsearchRepository.findById(item.getId());
        assertTrue(foundItemOptional.isPresent());
        ApplicationCategory foundItem = foundItemOptional.get();
        assertEquals(item.getId(), foundItem.getId());
        applicationCategoryElasticsearchRepository.delete(savedItem);
    }

    @Test
    void shouldFindAllItems() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> savedItems = applicationCategoryElasticsearchRepository.saveAll(items);
        Iterable<ApplicationCategory> foundItems = applicationCategoryElasticsearchRepository.findAll();
        assertNotNull(foundItems);
        applicationCategoryElasticsearchRepository.deleteAll(savedItems);
    }

    @Test
    void shouldFindAllItemsSortedByName() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> savedItems = applicationCategoryElasticsearchRepository.saveAll(items);
        Iterable<ApplicationCategory> foundItems = applicationCategoryElasticsearchRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        assertNotNull(foundItems);
        applicationCategoryElasticsearchRepository.deleteAll(savedItems);
    }

    @Test
    void shouldFindAllItemsSortedById() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> savedItems = applicationCategoryElasticsearchRepository.saveAll(items);
        Iterable<ApplicationCategory> foundItems = applicationCategoryElasticsearchRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        assertNotNull(foundItems);
        applicationCategoryElasticsearchRepository.deleteAll(savedItems);
    }

    @Test
    void shouldFindAllItemsPaged() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> savedItems = applicationCategoryElasticsearchRepository.saveAll(items);
        Page<ApplicationCategory> foundItems = applicationCategoryElasticsearchRepository.findAll(PageRequest.of(0, 4));
        assertNotNull(foundItems);
        applicationCategoryElasticsearchRepository.deleteAll(savedItems);
    }

    @Test
    void shouldFindAllItemsPagedAndSorted() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> savedItems = applicationCategoryElasticsearchRepository.saveAll(items);
        Page<ApplicationCategory> foundItems = applicationCategoryElasticsearchRepository.findAll(PageRequest.of(0, 4, Sort.by(Sort.Direction.ASC, "name")));
        assertNotNull(foundItems);
        applicationCategoryElasticsearchRepository.deleteAll(savedItems);
    }

    @Test
    void shouldExistById() {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory savedItem = applicationCategoryElasticsearchRepository.save(item);
        boolean exists = applicationCategoryElasticsearchRepository.existsById(savedItem.getId());
        assertTrue(exists);
        applicationCategoryElasticsearchRepository.delete(savedItem);
    }

    @Test
    void shouldCount() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> savedItems = applicationCategoryElasticsearchRepository.saveAll(items);
        long count = applicationCategoryElasticsearchRepository.count();
        assertEquals(count, numberOfItems);
        applicationCategoryElasticsearchRepository.deleteAll(savedItems);
    }

    @Test
    void shouldDeleteItem() {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory savedItem = applicationCategoryElasticsearchRepository.save(item);
        applicationCategoryElasticsearchRepository.delete(item);
        boolean exists = applicationCategoryElasticsearchRepository.existsById(savedItem.getId());
        assertFalse(exists);
    }

    @Test
    void shouldDeleteById() {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory savedItem = applicationCategoryElasticsearchRepository.save(item);
        applicationCategoryElasticsearchRepository.deleteById(item.getId());
        boolean exists = applicationCategoryElasticsearchRepository.existsById(savedItem.getId());
        assertFalse(exists);
    }

    @Test
    void shouldDeleteItems() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> savedItems = applicationCategoryElasticsearchRepository.saveAll(items);
        int numberOfItemsToDelete = 3;
        applicationCategoryElasticsearchRepository.deleteAll(items.subList(0, numberOfItemsToDelete));
        long count = applicationCategoryElasticsearchRepository.count();
        assertEquals((numberOfItems-numberOfItemsToDelete), count);
        applicationCategoryElasticsearchRepository.deleteAll(savedItems);
    }

    @Test
    void shouldDeleteAllItems() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> savedItems = applicationCategoryElasticsearchRepository.saveAll(items);
        assertNotNull(savedItems);
        applicationCategoryElasticsearchRepository.deleteAll();
        long count = applicationCategoryElasticsearchRepository.count();
        assertEquals(0, count);
    }
}
