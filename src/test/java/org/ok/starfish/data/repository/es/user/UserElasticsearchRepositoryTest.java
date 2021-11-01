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
    void shouldFindItemByTitle() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        List<User> foundItems = userElasticsearchRepository.findByTitle(item.getTitle());
        assertFalse(foundItems.isEmpty());
        User foundItem = foundItems.get(0);
        assertEquals(item.getTitle(), foundItem.getTitle());
        userElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByTitle() {
        List<User> foundItems = userElasticsearchRepository.findByTitle(getNonExistingName());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindItemByFirstName() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        List<User> foundItems = userElasticsearchRepository.findByFirstName(item.getFirstName());
        assertFalse(foundItems.isEmpty());
        User foundItem = foundItems.get(0);
        assertEquals(item.getFirstName(), foundItem.getFirstName());
        userElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByFirstName() {
        List<User> foundItems = userElasticsearchRepository.findByFirstName(getNonExistingName());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindItemByLastName() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        List<User> foundItems = userElasticsearchRepository.findByLastName(item.getLastName());
        assertFalse(foundItems.isEmpty());
        User foundItem = foundItems.get(0);
        assertEquals(item.getLastName(), foundItem.getLastName());
        userElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByLastName() {
        List<User> foundItems = userElasticsearchRepository.findByLastName(getNonExistingName());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindItemByStreetNumber() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        List<User> foundItems = userElasticsearchRepository.findByStreetNumber(item.getStreetNumber());
        assertFalse(foundItems.isEmpty());
        User foundItem = foundItems.get(0);
        assertEquals(item.getStreetNumber(), foundItem.getStreetNumber());
        userElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByStreetNumber() {
        List<User> foundItems = userElasticsearchRepository.findByStreetNumber(getNonExistingNumber());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindItemByStreetName() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        List<User> foundItems = userElasticsearchRepository.findByStreetName(item.getStreetName());
        assertFalse(foundItems.isEmpty());
        User foundItem = foundItems.get(0);
        assertEquals(item.getStreetName(), foundItem.getStreetName());
        userElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByStreetName() {
        List<User> foundItems = userElasticsearchRepository.findByStreetName(getNonExistingName());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindItemByCity() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        List<User> foundItems = userElasticsearchRepository.findByCity(item.getCity());
        assertFalse(foundItems.isEmpty());
        User foundItem = foundItems.get(0);
        assertEquals(item.getCity(), foundItem.getCity());
        userElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByCity() {
        List<User> foundItems = userElasticsearchRepository.findByCity(getNonExistingName());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindItemByState() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        List<User> foundItems = userElasticsearchRepository.findByState(item.getState());
        assertFalse(foundItems.isEmpty());
        User foundItem = foundItems.get(0);
        assertEquals(item.getState(), foundItem.getState());
        userElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByState() {
        List<User> foundItems = userElasticsearchRepository.findByState(getNonExistingName());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindItemByCountry() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        List<User> foundItems = userElasticsearchRepository.findByCountry(item.getCountry());
        assertFalse(foundItems.isEmpty());
        User foundItem = foundItems.get(0);
        assertEquals(item.getCountry(), foundItem.getCountry());
        userElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByCountry() {
        List<User> foundItems = userElasticsearchRepository.findByCountry(getNonExistingName());
        assertTrue(foundItems.isEmpty());
    }

    @Test
    void shouldFindItemByPostcode() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        List<User> foundItems = userElasticsearchRepository.findByPostcode(item.getPostcode());
        assertFalse(foundItems.isEmpty());
        User foundItem = foundItems.get(0);
        assertEquals(item.getPostcode(), foundItem.getPostcode());
        userElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByPostcode() {
        List<User> foundItems = userElasticsearchRepository.findByPostcode(getNonExistingName());
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
