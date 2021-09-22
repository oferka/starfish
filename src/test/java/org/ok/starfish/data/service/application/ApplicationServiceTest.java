package org.ok.starfish.data.service.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    public void shouldFindAll() {
        List<Application> items = sampleApplicationProvider.getItems(numberOfItemsToLoad);
        Iterable<Application> savedItems = applicationElasticsearchRepository.saveAll(items);
        List<Application> foundItems = applicationService.findAll();
        assertNotNull(foundItems);
        applicationElasticsearchRepository.deleteAll(savedItems);
    }

    @Test
    public void shouldFindById() {
        List<Application> items = sampleApplicationProvider.getItems(numberOfItemsToLoad);
        Iterable<Application> saved = applicationElasticsearchRepository.saveAll(items);
        String id = items.get(0).getId();
        Optional<Application> found = applicationService.findById(id);
        assertTrue(found.isPresent());
        assertEquals(id, found.get().getId());
        applicationElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldFindRandom() {
        List<Application> items = sampleApplicationProvider.getItems(numberOfItemsToLoad);
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
        List<Application> items = sampleApplicationProvider.getItems(numberOfItemsToLoad);
        Iterable<Application> saved = applicationService.saveAll(items);
        assertNotNull(saved);
        applicationElasticsearchRepository.deleteAll(items);
    }

    @Test
    public void shouldUpdate() {
        List<Application> items = sampleApplicationProvider.getItems(numberOfItemsToLoad);
        Iterable<Application> saved = applicationElasticsearchRepository.saveAll(items);
        Application item = items.get(0);
        Optional<Application> updated = applicationService.update(item.getId(), item);
        assertTrue(updated.isPresent());
        applicationElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotUpdate() {
        List<Application> items = sampleApplicationProvider.getItems(numberOfItemsToLoad);
        Iterable<Application> saved = applicationElasticsearchRepository.saveAll(items);
        Application item = items.get(0);
        Optional<Application> updated = applicationService.update(getNonExistingId(), item);
        assertTrue(updated.isEmpty());
        applicationElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldDeleteById() {
        Application item = sampleApplicationProvider.getItem();
        Application saved = applicationElasticsearchRepository.save(item);
        String id = saved.getId();
        applicationService.deleteById(id);
        boolean exists = applicationElasticsearchRepository.existsById(id);
        assertFalse(exists);
    }

    @Test
    public void shouldNotDeleteById() {
        applicationService.deleteById(getNonExistingId());
    }

    @Test
    void shouldCount() {
        long countBefore = applicationElasticsearchRepository.count();
        List<Application> items = sampleApplicationProvider.getItems(numberOfItemsToLoad);
        Iterable<Application> saved = applicationElasticsearchRepository.saveAll(items);
        long countAfter = applicationService.count();
        assertEquals(countBefore + numberOfItemsToLoad, countAfter);
        applicationElasticsearchRepository.deleteAll(saved);
    }
}
