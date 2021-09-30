package org.ok.starfish.data.repository.es.account;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ok.starfish.data.sample.account.SampleAccountProvider;
import org.ok.starfish.model.account.Account;
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
public class AccountElasticsearchRepositoryTest {

    @Autowired
    private AccountElasticsearchRepository accountElasticsearchRepository;

    @Autowired
    private SampleAccountProvider sampleAccountProvider;

    private long contentCountBefore;

    private final int numberOfItemsToLoad = 18;

    @BeforeEach
    void captureContentStatus() {
        contentCountBefore = accountElasticsearchRepository.count();
    }

    @AfterEach
    void verifyContentStatusNotChanged() {
        long contentCountAfter = accountElasticsearchRepository.count();
        assertEquals(contentCountBefore, contentCountAfter);
    }

    @Test
    void shouldSaveItem() {
        Account item = sampleAccountProvider.getItem();
        Account saved = accountElasticsearchRepository.save(item);
        assertEquals(item, saved);
        accountElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldSaveItems() {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        assertNotNull(saved);
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindItemById() {
        Account item = sampleAccountProvider.getItem();
        Account saved = accountElasticsearchRepository.save(item);
        Optional<Account> foundItemOptional = accountElasticsearchRepository.findById(item.getId());
        assertTrue(foundItemOptional.isPresent());
        Account foundItem = foundItemOptional.get();
        assertEquals(item.getId(), foundItem.getId());
        accountElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemById() {
        Optional<Account> foundItemOptional = accountElasticsearchRepository.findById(getNonExistingId());
        assertTrue(foundItemOptional.isEmpty());
    }

    @Test
    void shouldFindItemByName() {
        Account item = sampleAccountProvider.getItem();
        Account saved = accountElasticsearchRepository.save(item);
        Optional<Account> foundItemOptional = accountElasticsearchRepository.findByName(item.getName());
        assertTrue(foundItemOptional.isPresent());
        Account foundItem = foundItemOptional.get();
        assertEquals(item.getId(), foundItem.getId());
        accountElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByName() {
        Optional<Account> foundItemOptional = accountElasticsearchRepository.findByName(getNonExistingName());
        assertTrue(foundItemOptional.isEmpty());
    }

    @Test
    void shouldFindItemByCreatedDate() {
        Account item = sampleAccountProvider.getItem();
        Account saved = accountElasticsearchRepository.save(item);
        Optional<Account> foundItemOptional = accountElasticsearchRepository.findByCreatedDate(item.getCreatedDate());
        assertTrue(foundItemOptional.isPresent());
        Account foundItem = foundItemOptional.get();
        assertEquals(item.getId(), foundItem.getId());
        accountElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotFindItemByCreatedDate() {
        Optional<Account> foundItemOptional = accountElasticsearchRepository.findByCreatedDate(getNonExistingCreatedDate());
        assertTrue(foundItemOptional.isEmpty());
    }

    @Test
    void shouldFindAllItems() {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        Iterable<Account> found = accountElasticsearchRepository.findAll();
        assertNotNull(found);
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsSortedByName() {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        Iterable<Account> found = accountElasticsearchRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        assertNotNull(found);
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsSortedById() {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        Iterable<Account> found = accountElasticsearchRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        assertNotNull(found);
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsPaged() {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        Page<Account> found = accountElasticsearchRepository.findAll(PageRequest.of(0, 4));
        assertNotNull(found);
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldFindAllItemsPagedAndSorted() {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        Page<Account> found = accountElasticsearchRepository.findAll(PageRequest.of(0, 4, Sort.by(Sort.Direction.ASC, "name")));
        assertNotNull(found);
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldExistById() {
        Account item = sampleAccountProvider.getItem();
        Account saved = accountElasticsearchRepository.save(item);
        boolean exists = accountElasticsearchRepository.existsById(saved.getId());
        assertTrue(exists);
        accountElasticsearchRepository.delete(saved);
    }

    @Test
    void shouldNotExistById() {
        boolean exists = accountElasticsearchRepository.existsById(getNonExistingId());
        assertFalse(exists);
    }

    @Test
    void shouldCount() {
        long countBefore = accountElasticsearchRepository.count();
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        long countAfter = accountElasticsearchRepository.count();
        assertEquals(countAfter, countBefore + numberOfItemsToLoad);
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    void shouldDeleteItem() {
        Account item = sampleAccountProvider.getItem();
        Account saved = accountElasticsearchRepository.save(item);
        accountElasticsearchRepository.delete(item);
        boolean exists = accountElasticsearchRepository.existsById(saved.getId());
        assertFalse(exists);
    }

    @Test
    void shouldDeleteById() {
        Account item = sampleAccountProvider.getItem();
        Account saved = accountElasticsearchRepository.save(item);
        accountElasticsearchRepository.deleteById(item.getId());
        boolean exists = accountElasticsearchRepository.existsById(saved.getId());
        assertFalse(exists);
    }

    @Test
    void shouldNotDeleteById() {
        accountElasticsearchRepository.deleteById(getNonExistingId());
    }

    @Test
    void shouldDeleteItems() {
        long countBefore = accountElasticsearchRepository.count();
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        int numberOfItemsToDelete = 3;
        accountElasticsearchRepository.deleteAll(items.subList(0, numberOfItemsToDelete));
        long countAfter = accountElasticsearchRepository.count();
        assertEquals((countBefore + numberOfItemsToLoad - numberOfItemsToDelete), countAfter);
        accountElasticsearchRepository.deleteAll(saved);
    }
}
