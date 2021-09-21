package org.ok.starfish.data.repository.es.application;

import org.junit.jupiter.api.Test;
import org.ok.starfish.data.sample.application.SampleApplicationProvider;
import org.ok.starfish.model.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ApplicationElasticsearchRepositoryTest {

    @Autowired
    private ApplicationElasticsearchRepository applicationElasticsearchRepository;

    @Autowired
    private SampleApplicationProvider sampleApplicationProvider;

    private final int numberOfItems = 10;

    @Test
    void shouldSaveItem() {
        Application item = sampleApplicationProvider.getItem();
        Application saved = applicationElasticsearchRepository.save(item);
        assertEquals(item, saved);
        applicationElasticsearchRepository.delete(saved);
    }

//    @Test
//    void shouldSaveItems() {
//        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
//        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
//        assertNotNull(saved);
//        applicationCategoryElasticsearchRepository.deleteAll(saved);
//    }
//
//    @Test
//    void shouldFindItemById() {
//        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
//        ApplicationCategory saved = applicationCategoryElasticsearchRepository.save(item);
//        Optional<ApplicationCategory> foundItemOptional = applicationCategoryElasticsearchRepository.findById(item.getId());
//        assertTrue(foundItemOptional.isPresent());
//        ApplicationCategory foundItem = foundItemOptional.get();
//        assertEquals(item.getId(), foundItem.getId());
//        applicationCategoryElasticsearchRepository.delete(saved);
//    }
//
//    @Test
//    void shouldNotFindItemById() {
//        Optional<ApplicationCategory> foundItemOptional = applicationCategoryElasticsearchRepository.findById(getNonExistingId());
//        assertTrue(foundItemOptional.isEmpty());
//    }
//
//    @Test
//    void shouldFindAllItems() {
//        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
//        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
//        Iterable<ApplicationCategory> found = applicationCategoryElasticsearchRepository.findAll();
//        assertNotNull(found);
//        applicationCategoryElasticsearchRepository.deleteAll(saved);
//    }
//
//    @Test
//    void shouldFindAllItemsSortedByName() {
//        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
//        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
//        Iterable<ApplicationCategory> found = applicationCategoryElasticsearchRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
//        assertNotNull(found);
//        applicationCategoryElasticsearchRepository.deleteAll(saved);
//    }
//
//    @Test
//    void shouldFindAllItemsSortedById() {
//        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
//        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
//        Iterable<ApplicationCategory> found = applicationCategoryElasticsearchRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
//        assertNotNull(found);
//        applicationCategoryElasticsearchRepository.deleteAll(saved);
//    }
//
//    @Test
//    void shouldFindAllItemsPaged() {
//        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
//        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
//        Page<ApplicationCategory> found = applicationCategoryElasticsearchRepository.findAll(PageRequest.of(0, 4));
//        assertNotNull(found);
//        applicationCategoryElasticsearchRepository.deleteAll(saved);
//    }
//
//    @Test
//    void shouldFindAllItemsPagedAndSorted() {
//        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
//        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
//        Page<ApplicationCategory> found = applicationCategoryElasticsearchRepository.findAll(PageRequest.of(0, 4, Sort.by(Sort.Direction.ASC, "name")));
//        assertNotNull(found);
//        applicationCategoryElasticsearchRepository.deleteAll(saved);
//    }
//
//    @Test
//    void shouldExistById() {
//        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
//        ApplicationCategory saved = applicationCategoryElasticsearchRepository.save(item);
//        boolean exists = applicationCategoryElasticsearchRepository.existsById(saved.getId());
//        assertTrue(exists);
//        applicationCategoryElasticsearchRepository.delete(saved);
//    }
//
//    @Test
//    void shouldNotExistById() {
//        boolean exists = applicationCategoryElasticsearchRepository.existsById(getNonExistingId());
//        assertFalse(exists);
//    }
//
//    @Test
//    void shouldCount() {
//        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
//        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
//        long count = applicationCategoryElasticsearchRepository.count();
//        assertEquals(count, numberOfItems);
//        applicationCategoryElasticsearchRepository.deleteAll(saved);
//    }
//
//    @Test
//    void shouldDeleteItem() {
//        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
//        ApplicationCategory saved = applicationCategoryElasticsearchRepository.save(item);
//        applicationCategoryElasticsearchRepository.delete(item);
//        boolean exists = applicationCategoryElasticsearchRepository.existsById(saved.getId());
//        assertFalse(exists);
//    }
//
//    @Test
//    void shouldDeleteById() {
//        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
//        ApplicationCategory saved = applicationCategoryElasticsearchRepository.save(item);
//        applicationCategoryElasticsearchRepository.deleteById(item.getId());
//        boolean exists = applicationCategoryElasticsearchRepository.existsById(saved.getId());
//        assertFalse(exists);
//    }
//
//    @Test
//    void shouldNotDeleteById() {
//        applicationCategoryElasticsearchRepository.deleteById(getNonExistingId());
//    }
//
//    @Test
//    void shouldDeleteItems() {
//        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
//        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
//        int numberOfItemsToDelete = 3;
//        applicationCategoryElasticsearchRepository.deleteAll(items.subList(0, numberOfItemsToDelete));
//        long count = applicationCategoryElasticsearchRepository.count();
//        assertEquals((numberOfItems-numberOfItemsToDelete), count);
//        applicationCategoryElasticsearchRepository.deleteAll(saved);
//    }
//
//    @Test
//    void shouldDeleteAllItems() {
//        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
//        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
//        assertNotNull(saved);
//        applicationCategoryElasticsearchRepository.deleteAll();
//        long count = applicationCategoryElasticsearchRepository.count();
//        assertEquals(0, count);
//    }
}
