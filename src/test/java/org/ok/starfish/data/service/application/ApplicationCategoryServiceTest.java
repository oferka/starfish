package org.ok.starfish.data.service.application;

import org.junit.jupiter.api.Test;
import org.ok.starfish.data.repository.es.application.ApplicationCategoryElasticsearchRepository;
import org.ok.starfish.data.sample.application.SampleApplicationCategoryProvider;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.ok.starfish.data.TestDataUtils.getNonExistingId;

@SpringBootTest
public class ApplicationCategoryServiceTest {

    @Autowired
    private ApplicationCategoryService applicationCategoryService;

    @Autowired
    private ApplicationCategoryElasticsearchRepository applicationCategoryElasticsearchRepository;

    @Autowired
    private SampleApplicationCategoryProvider sampleApplicationCategoryProvider;

    private final int numberOfItems = 10;

    @Test
    public void shouldFindAll() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> savedItems = applicationCategoryElasticsearchRepository.saveAll(items);
        List<ApplicationCategory> foundItems = applicationCategoryService.findAll();
        assertNotNull(foundItems);
        applicationCategoryElasticsearchRepository.deleteAll(savedItems);
    }

    @Test
    public void shouldFindById() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
        String id = items.get(0).getId();
        Optional<ApplicationCategory> found = applicationCategoryService.findById(id);
        assertTrue(found.isPresent());
        assertEquals(id, found.get().getId());
        applicationCategoryElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldFindRandom() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
        Optional<ApplicationCategory> found = applicationCategoryService.findRandom();
        assertTrue(found.isPresent());
        applicationCategoryElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindById() {
        Optional<ApplicationCategory> found = applicationCategoryService.findById(getNonExistingId());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldSave() {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory saved = applicationCategoryService.save(item);
        assertEquals(saved, item);
        applicationCategoryElasticsearchRepository.delete(item);
    }

    @Test
    public void shouldSaveAll() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> saved = applicationCategoryService.saveAll(items);
        assertNotNull(saved);
        applicationCategoryElasticsearchRepository.deleteAll(items);
    }

    @Test
    public void shouldUpdate() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
        ApplicationCategory item = items.get(0);
        Optional<ApplicationCategory> updated = applicationCategoryService.update(item.getId(), item);
        assertTrue(updated.isPresent());
        applicationCategoryElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotUpdate() {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
        ApplicationCategory item = items.get(0);
        Optional<ApplicationCategory> updated = applicationCategoryService.update(getNonExistingId(), item);
        assertTrue(updated.isEmpty());
        applicationCategoryElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldDeleteById() {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory saved = applicationCategoryElasticsearchRepository.save(item);
        String id = saved.getId();
        applicationCategoryService.deleteById(id);
        boolean exists = applicationCategoryElasticsearchRepository.existsById(id);
        assertFalse(exists);
    }

    @Test
    public void shouldNotDeleteById() {
        applicationCategoryService.deleteById(getNonExistingId());
    }

    @Test
    void shouldCount() {
        long countBefore = applicationCategoryElasticsearchRepository.count();
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
        long countAfter = applicationCategoryService.count();
        assertEquals(countBefore + numberOfItems, countAfter);
        applicationCategoryElasticsearchRepository.deleteAll(saved);
    }
}
