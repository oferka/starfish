package org.ok.starfish.data.service.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ok.starfish.data.repository.es.user.UserElasticsearchRepository;
import org.ok.starfish.data.sample.user.SampleUserProvider;
import org.ok.starfish.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.ok.starfish.data.TestDataUtils.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

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
    public void shouldFindAll() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> savedItems = userElasticsearchRepository.saveAll(items);
        List<User> foundItems = userService.findAll();
        assertNotNull(foundItems);
        userElasticsearchRepository.deleteAll(savedItems);
    }

    @Test
    public void shouldFindById() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        String id = items.get(0).getId();
        Optional<User> found = userService.findById(id);
        assertTrue(found.isPresent());
        assertEquals(id, found.get().getId());
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindById() {
        Optional<User> found = userService.findById(getNonExistingId());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByGender() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        String gender = items.get(0).getGender();
        List<User> found = userService.findByGender(gender);
        assertFalse(found.isEmpty());
        assertEquals(gender, found.get(0).getGender());
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindByGender() {
        List<User> found = userService.findByGender(getNonExistingName());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByTitle() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        String title = items.get(0).getTitle();
        List<User> found = userService.findByTitle(title);
        assertFalse(found.isEmpty());
        assertEquals(title, found.get(0).getTitle());
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindByTitle() {
        List<User> found = userService.findByTitle(getNonExistingName());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByFirstName() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        String firstName = items.get(0).getFirstName();
        List<User> found = userService.findByFirstName(firstName);
        assertFalse(found.isEmpty());
        assertEquals(firstName, found.get(0).getFirstName());
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindByFirstName() {
        List<User> found = userService.findByFirstName(getNonExistingName());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByLastName() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        String lastName = items.get(0).getLastName();
        List<User> found = userService.findByLastName(lastName);
        assertFalse(found.isEmpty());
        assertEquals(lastName, found.get(0).getLastName());
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindByLastName() {
        List<User> found = userService.findByLastName(getNonExistingName());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByStreetNumber() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        int streetNumber = items.get(0).getStreetNumber();
        List<User> found = userService.findByStreetNumber(streetNumber);
        assertFalse(found.isEmpty());
        assertEquals(streetNumber, found.get(0).getStreetNumber());
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindByStreetNumber() {
        List<User> found = userService.findByStreetNumber(getNonExistingInteger());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByStreetName() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        String streetName = items.get(0).getStreetName();
        List<User> found = userService.findByStreetName(streetName);
        assertFalse(found.isEmpty());
        assertEquals(streetName, found.get(0).getStreetName());
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindByStreetName() {
        List<User> found = userService.findByStreetName(getNonExistingName());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByCity() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        String city = items.get(0).getCity();
        List<User> found = userService.findByCity(city);
        assertFalse(found.isEmpty());
        assertEquals(city, found.get(0).getCity());
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindByCity() {
        List<User> found = userService.findByCity(getNonExistingName());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByState() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        String state = items.get(0).getState();
        List<User> found = userService.findByState(state);
        assertFalse(found.isEmpty());
        assertEquals(state, found.get(0).getState());
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindByState() {
        List<User> found = userService.findByState(getNonExistingName());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByCountry() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        String country = items.get(0).getCountry();
        List<User> found = userService.findByCountry(country);
        assertFalse(found.isEmpty());
        assertEquals(country, found.get(0).getCountry());
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindByCountry() {
        List<User> found = userService.findByCountry(getNonExistingName());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByCreatedDate() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        ZonedDateTime createdDate = items.get(0).getCreatedDate();
        List<User> found = userService.findByCreatedDate(createdDate);
        assertFalse(found.isEmpty());
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindByCreatedDate() {
        List<User> found = userService.findByCreatedDate(getNonExistingDate());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindRandom() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        Optional<User> found = userService.findRandom();
        assertTrue(found.isPresent());
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldSave() {
        User item = sampleUserProvider.getItem();
        User saved = userService.save(item);
        assertEquals(saved, item);
        userElasticsearchRepository.delete(item);
    }

    @Test
    public void shouldSaveAll() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userService.saveAll(items);
        assertNotNull(saved);
        userElasticsearchRepository.deleteAll(items);
    }

    @Test
    public void shouldUpdate() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        User item = items.get(0);
        Optional<User> updated = userService.update(item.getId(), item);
        assertTrue(updated.isPresent());
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotUpdate() {
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        User item = items.get(0);
        Optional<User> updated = userService.update(getNonExistingId(), item);
        assertTrue(updated.isEmpty());
        userElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldDeleteById() {
        User item = sampleUserProvider.getItem();
        User saved = userElasticsearchRepository.save(item);
        String id = saved.getId();
        userService.deleteById(id);
        boolean exists = userElasticsearchRepository.existsById(id);
        assertFalse(exists);
    }

    @Test
    public void shouldNotDeleteById() {
        userService.deleteById(getNonExistingId());
    }

    @Test
    void shouldCount() {
        long countBefore = userElasticsearchRepository.count();
        List<User> items = sampleUserProvider.getItems(numberOfItemsToLoad);
        Iterable<User> saved = userElasticsearchRepository.saveAll(items);
        long countAfter = userService.count();
        assertEquals(countBefore + numberOfItemsToLoad, countAfter);
        userElasticsearchRepository.deleteAll(saved);
    }
}
