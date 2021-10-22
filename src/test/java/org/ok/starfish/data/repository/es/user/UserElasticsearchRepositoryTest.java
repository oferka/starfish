package org.ok.starfish.data.repository.es.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ok.starfish.data.sample.user.SampleUserProvider;
import org.ok.starfish.model.user.User;
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
public class UserElasticsearchRepositoryTest {

    @Autowired
    private UserElasticsearchRepository userElasticsearchRepository;

    @Autowired
    private SampleUserProvider sampleUserProvider;

    private long contentCountBefore;

    private final int numberOfItemsToLoad = 10;

    @BeforeEach
    void captureContentStatus() {
        contentCountBefore = userElasticsearchRepository.count();
    }

    @AfterEach
    void verifyContentStatusNotChanged() {
        long contentCountAfter = userElasticsearchRepository.count();
        assertEquals(contentCountBefore, contentCountAfter);
    }

    @Test
    void shouldSaveItem() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        assertEquals(item, saved);
        userElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldSaveItems() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        assertNotNull(saved);
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindItemById() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        Optional<User> foundItemOptional = userElasticsearchRepository.findById(item.getId());
        assertTrue(foundItemOptional.isPresent());
        User foundItem = foundItemOptional.get();
        assertEquals(item.getId(), foundItem.getId());
        userElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemById() {
        Optional<User> foundItemOptional = userElasticsearchRepository.findById(getNonExistingId());
        assertTrue(foundItemOptional.isEmpty());
    }

    @Test
    void shouldFindItemByGender() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        List<User> foundItems = userElasticsearchRepository.findByGender(item.getGender());
        assertFalse(foundItems.isEmpty());
        User foundItem = foundItems.get(0);
        assertEquals(item.getGender(), foundItem.getGender());
        userElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByGender() {
        List<User> foundItems = userElasticsearchRepository.findByGender(getNonExistingName());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindItemByFirstName() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        List<User> foundItems = userElasticsearchRepository.findByFirstName(item.getFirstName());
        assertFalse(foundItems.isEmpty());
        User foundItem = foundItems.get(0);
        assertEquals(item.getId(), foundItem.getId());
        userElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByFirstName() {
        List<User> foundItems = userElasticsearchRepository.findByFirstName(getNonExistingName());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindItemByCreatedDate() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        List<User> foundItems = userElasticsearchRepository.findByCreatedDate(item.getCreatedDate());
        assertFalse(foundItems.isEmpty());
        User foundItem = foundItems.get(0);
        assertEquals(item.getId(), foundItem.getId());
        userElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByCreatedDate() {
        List<User> foundItems = userElasticsearchRepository.findByCreatedDate(getNonExistingCreatedDate());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindAllItems() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        Iterable<User> found = userElasticsearchRepository.findAll();
        assertNotNull(found);
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsSortedByName() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        Iterable<User> found = userElasticsearchRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName"));
        assertNotNull(found);
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsSortedById() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        Iterable<User> found = userElasticsearchRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        assertNotNull(found);
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsPaged() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        Page<User> found = userElasticsearchRepository.findAll(PageRequest.of(0, 4));
        assertNotNull(found);
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsPagedAndSorted() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        Page<User> found = userElasticsearchRepository.findAll(PageRequest.of(0, 4, Sort.by(Sort.Direction.ASC, "firstName")));
        assertNotNull(found);
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldExistById() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        boolean exists = userElasticsearchRepository.existsById(saved.getId());
        assertTrue(exists);
        userElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotExistById() {
        boolean exists = userElasticsearchRepository.existsById(getNonExistingId());
        assertFalse(exists);
    }

    @Test
    void shouldCount() {
        long countBefore = userElasticsearchRepository.count();
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        long countAfter = userElasticsearchRepository.count();
        assertEquals(countAfter, countBefore + numberOfItemsToLoad);
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldDeleteItem() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        userElasticsearchRepository.delete(item);
        boolean exists = userElasticsearchRepository.existsById(saved.getId());
        assertFalse(exists);
    }

    @Test
    void shouldDeleteById() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        userElasticsearchRepository.deleteById(item.getId());
        boolean exists = userElasticsearchRepository.existsById(saved.getId());
        assertFalse(exists);
    }

    @Test
    void shouldNotDeleteById() {
        userElasticsearchRepository.deleteById(getNonExistingId());
    }

    @Test
    void shouldDeleteItems() {
        long countBefore = userElasticsearchRepository.count();
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        int numberOfItemsToDelete = 3;
        userElasticsearchRepository.deleteAll(items.subList(0, numberOfItemsToDelete));
        long countAfter = userElasticsearchRepository.count();
        assertEquals((countBefore + numberOfItemsToLoad - numberOfItemsToDelete), countAfter);
        userElasticsearchRepository.deleteAll(saved);
    }
}
