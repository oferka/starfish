package org.ok.starfish.data.controller.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ok.starfish.StarfishApplication;
import org.ok.starfish.data.repository.es.account.AccountElasticsearchRepository;
import org.ok.starfish.data.sample.account.SampleAccountProvider;
import org.ok.starfish.model.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.ok.starfish.data.TestDataUtils.getNonExistingId;
import static org.ok.starfish.data.controller.Paths.ACCOUNT_PATH;
import static org.ok.starfish.data.controller.Paths.COUNT_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = StarfishApplication.class)
public class AccountControllerTest {

    private MockMvc mvc;

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

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldFindAll() throws Exception {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        MvcResult mvcResult = mvc.perform(get(format("/%s", ACCOUNT_PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(items.get(0).getId())))
                .andReturn();
        assertNotNull(mvcResult);
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldFindById() throws Exception {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        String id = items.get(0).getId();
        MvcResult mvcResult = mvc.perform(get(format("/%s/{id}", ACCOUNT_PATH), id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(id)))
                .andReturn();
        assertNotNull(mvcResult);
        accountElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindById() throws Exception {
        MvcResult mvcResult = mvc.perform(get(format("/%s/{id}", ACCOUNT_PATH), getNonExistingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isNotFound())
                .andReturn();
        assertNotNull(mvcResult);
    }

    @Test
    public void shouldSave() throws Exception {
        Account item = sampleAccountProvider.getItem();
        MvcResult mvcResult = mvc.perform(post(format("/%s", ACCOUNT_PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(item))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(item.getId())))
                .andReturn();
        assertNotNull(mvcResult);
        accountElasticsearchRepository.delete(item);
    }

    @Test
    public void shouldDeleteById() throws Exception {
        Account item = sampleAccountProvider.getItem();
        Account saved = accountElasticsearchRepository.save(item);
        String id = saved.getId();
        MvcResult mvcResult = mvc.perform(delete(format("/%s/{id}", ACCOUNT_PATH), id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isNoContent())
                .andReturn();
        assertNotNull(mvcResult);
        boolean exists = accountElasticsearchRepository.existsById(id);
        assertFalse(exists);
    }

    @Test
    public void shouldNotDeleteById() throws Exception {
        MvcResult mvcResult = mvc.perform(delete(format("/%s/{id}", ACCOUNT_PATH), getNonExistingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isNoContent())
                .andReturn();
        assertNotNull(mvcResult);
    }

    @Test
    public void shouldUpdate() throws Exception {
        Account item = sampleAccountProvider.getItem();
        Account saved = accountElasticsearchRepository.save(item);
        String id = saved.getId();
        MvcResult mvcResult = mvc.perform(put(format("/%s/{id}", ACCOUNT_PATH), id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(item))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(mvcResult);
        Optional<Account> updated = accountElasticsearchRepository.findById(id);
        assertTrue(updated.isPresent());
        accountElasticsearchRepository.deleteById(id);
    }

    @Test
    public void shouldNotUpdate() throws Exception {
        Account item = sampleAccountProvider.getItem();
        Account saved = accountElasticsearchRepository.save(item);
        MvcResult mvcResult = mvc.perform(put(format("/%s/{id}", ACCOUNT_PATH), getNonExistingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(item))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isNotFound())
                .andReturn();
        assertNotNull(mvcResult);
        accountElasticsearchRepository.deleteById(saved.getId());
    }

    @Test
    public void shouldCount() throws Exception {
        List<Account> items = sampleAccountProvider.getItems(numberOfItemsToLoad);
        Iterable<Account> saved = accountElasticsearchRepository.saveAll(items);
        MvcResult mvcResult = mvc.perform(get(format("/%s/%s", ACCOUNT_PATH, COUNT_PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(mvcResult);
        accountElasticsearchRepository.deleteAll(saved);
    }
}
