package org.ok.starfish.data.service.application;

import org.junit.jupiter.api.Test;
import org.ok.starfish.data.repository.es.application.ApplicationElasticsearchRepository;
import org.ok.starfish.data.sample.application.SampleApplicationProvider;
import org.ok.starfish.model.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.ok.starfish.data.TestDataUtils.getNonExistingId;

@SpringBootTest
public class ApplicationServiceTest {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationElasticsearchRepository applicationElasticsearchRepository;

    @Autowired
    private SampleApplicationProvider sampleApplicationProvider;

    private final int numberOfItems = 10;

    @Test
    public void shouldFindAll() {
        List<Application> items = sampleApplicationProvider.getItems(numberOfItems);
        Iterable<Application> savedItems = applicationElasticsearchRepository.saveAll(items);
        List<Application> foundItems = applicationService.findAll();
        assertNotNull(foundItems);
        applicationElasticsearchRepository.deleteAll(savedItems);
    }

    @Test
    public void shouldFindById() {
        List<Application> items = sampleApplicationProvider.getItems(numberOfItems);
        Iterable<Application> saved = applicationElasticsearchRepository.saveAll(items);
        String id = items.get(0).getId();
        Optional<Application> found = applicationService.findById(id);
        assertTrue(found.isPresent());
        assertEquals(id, found.get().getId());
        applicationElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldFindRandom() {
        List<Application> items = sampleApplicationProvider.getItems(numberOfItems);
        Iterable<Application> saved = applicationElasticsearchRepository.saveAll(items);
        Optional<Application> found = applicationService.findRandom();
        assertTrue(found.isPresent());
        applicationElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindById() {
        Optional<Application> found = applicationService.findById(getNonExistingId());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldSave() {
        Application item = sampleApplicationProvider.getItem();
        Application saved = applicationService.save(item);
        assertEquals(saved, item);
        applicationElasticsearchRepository.delete(item);
    }

    @Test
    public void shouldSaveAll() {
        List<Application> items = sampleApplicationProvider.getItems(numberOfItems);
        Iterable<Application> saved = applicationService.saveAll(items);
        assertNotNull(saved);
        applicationElasticsearchRepository.deleteAll(items);
    }
//
//    @Test
//    public void shouldUpdate() {
//        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
//        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
//        ApplicationCategory item = items.get(0);
//        Optional<ApplicationCategory> updated = applicationCategoryService.update(item.getId(), item);
//        assertTrue(updated.isPresent());
//        applicationCategoryElasticsearchRepository.deleteAll(saved);
//    }
//
//    @Test
//    public void shouldNotUpdate() {
//        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
//        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
//        ApplicationCategory item = items.get(0);
//        Optional<ApplicationCategory> updated = applicationCategoryService.update(getNonExistingId(), item);
//        assertTrue(updated.isEmpty());
//        applicationCategoryElasticsearchRepository.deleteAll(saved);
//    }
//
//    @Test
//    public void shouldDeleteById() {
//        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
//        ApplicationCategory saved = applicationCategoryElasticsearchRepository.save(item);
//        String id = saved.getId();
//        applicationCategoryService.deleteById(id);
//        boolean exists = applicationCategoryElasticsearchRepository.existsById(id);
//        assertFalse(exists);
//    }
//
//    @Test
//    public void shouldNotDeleteById() {
//        applicationCategoryService.deleteById(getNonExistingId());
//    }
//
//    @Test
//    void shouldCount() {
//        long countBefore = applicationCategoryElasticsearchRepository.count();
//        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
//        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
//        long countAfter = applicationCategoryService.count();
//        assertEquals(countBefore + numberOfItems, countAfter);
//        applicationCategoryElasticsearchRepository.deleteAll(saved);
//    }
}
