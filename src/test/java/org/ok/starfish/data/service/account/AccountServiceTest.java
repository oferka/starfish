package org.ok.starfish.data.service.account;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ok.starfish.data.repository.es.account.AccountElasticsearchRepository;
import org.ok.starfish.data.sample.account.SampleAccountProvider;
import org.ok.starfish.model.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.ok.starfish.data.TestDataUtils.*;

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
    public void shouldNotFindById() {
        Optional<Account> found = accountService.findById(getNonExistingId());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindBySymbol() {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        String symbol = items.get(0).getSymbol();
        List<Account> found = accountService.findBySymbol(symbol);
        assertFalse(found.isEmpty());
        assertEquals(symbol, found.get(0).getSymbol());
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindBySymbol() {
        List<Account> found = accountService.findBySymbol(getNonExistingName());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByName() {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        String name = items.get(0).getName();
        List<Account> found = accountService.findByName(name);
        assertFalse(found.isEmpty());
        assertEquals(name, found.get(0).getName());
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindByName() {
        List<Account> found = accountService.findByName(getNonExistingName());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindBySector() {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        String sector = items.get(0).getSector();
        List<Account> found = accountService.findBySector(sector);
        assertFalse(found.isEmpty());
        assertEquals(sector, found.get(0).getSector());
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindBySector() {
        List<Account> found = accountService.findBySector(getNonExistingName());
        assertTrue(found.isEmpty());
    }

    @Test
    public void shouldFindByCreatedDate() {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        ZonedDateTime createdDate = items.get(0).getCreatedDate();
        List<Account> found = accountService.findByCreatedDate(createdDate);
        assertFalse(found.isEmpty());
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindByCreatedDate() {
        List<Account> found = accountService.findByCreatedDate(getNonExistingDate());
        assertTrue(found.isEmpty());
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
