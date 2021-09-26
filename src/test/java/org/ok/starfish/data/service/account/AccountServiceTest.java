package org.ok.starfish.data.service.account;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ok.starfish.data.repository.es.account.AccountElasticsearchRepository;
import org.ok.starfish.data.sample.account.SampleAccountProvider;
import org.ok.starfish.model.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.ok.starfish.data.TestDataUtils.getNonExistingId;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountElasticsearchRepository accountElasticsearchRepository;

    @Autowired
    private SampleAccountProvider sampleAccountProvider;

    private long contentCountBefore;

    private final int numberOfItemsToLoad = 10;

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
    public void shouldFindAll() {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> savedItems = accountElasticsearchRepository.saveAll(items);
        List<Account> foundItems = accountService.findAll();
        assertNotNull(foundItems);
        accountElasticsearchRepository.deleteAll(savedItems);
    }

    @Test
    public void shouldFindById() {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        String id = items.get(0).getId();
        Optional<Account> found = accountService.findById(id);
        assertTrue(found.isPresent());
        assertEquals(id, found.get().getId());
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldFindRandom() {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        Optional<Account> found = accountService.findRandom();
        assertTrue(found.isPresent());
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindById() {
        Optional<Account> found = accountService.findById(getNonExistingId());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldSave() {
        Account item = sampleAccountProvider.getItem();
        Account saved = accountService.save(item);
        assertEquals(saved, item);
        accountElasticsearchRepository.delete(item);
    }

    @Test
    public void shouldSaveAll() {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountService.saveAll(items);
        assertNotNull(saved);
        accountElasticsearchRepository.deleteAll(items);
    }

    @Test
    public void shouldUpdate() {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        Account item = items.get(0);
        Optional<Account> updated = accountService.update(item.getId(), item);
        assertTrue(updated.isPresent());
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotUpdate() {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        Account item = items.get(0);
        Optional<Account> updated = accountService.update(getNonExistingId(), item);
        assertTrue(updated.isEmpty());
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldDeleteById() {
        Account item = sampleAccountProvider.getItem();
        Account saved = accountElasticsearchRepository.save(item);
        String id = saved.getId();
        accountService.deleteById(id);
        boolean exists = accountElasticsearchRepository.existsById(id);
        assertFalse(exists);
    }

    @Test
    public void shouldNotDeleteById() {
        accountService.deleteById(getNonExistingId());
    }

    @Test
    void shouldCount() {
        long countBefore = accountElasticsearchRepository.count();
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        long countAfter = accountService.count();
        assertEquals(countBefore + numberOfItemsToLoad, countAfter);
        accountElasticsearchRepository.deleteAll(saved);
    }
}
