package org.ok.starfish.data.controller.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ok.starfish.StarfishApplication;
import org.ok.starfish.data.repository.es.application.ApplicationCategoryElasticsearchRepository;
import org.ok.starfish.data.sample.application.SampleApplicationCategoryProvider;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.ok.starfish.data.controller.Paths.APPLICATION_CATEGORY_PATH;
import static org.ok.starfish.data.controller.Paths.COUNT_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = StarfishApplication.class)
public class ApplicationCategoryControllerTest {

    private MockMvc mvc;

    @Autowired
    private ApplicationCategoryElasticsearchRepository applicationCategoryElasticsearchRepository;

    @Autowired
    private SampleApplicationCategoryProvider sampleApplicationCategoryProvider;

    private final int numberOfItems = 10;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldFindAll() throws Exception {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
        MvcResult mvcResult = mvc.perform(get(format("/%s", APPLICATION_CATEGORY_PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(items.get(0).getId())))
                .andReturn();
        assertNotNull(mvcResult);
        applicationCategoryElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldFindById() throws Exception {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
        String id = items.get(0).getId();
        MvcResult mvcResult = mvc.perform(get(format("/%s/{id}", APPLICATION_CATEGORY_PATH), id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(id)))
                .andReturn();
        assertNotNull(mvcResult);
        applicationCategoryElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindById() throws Exception {
        MvcResult mvcResult = mvc.perform(get(format("/%s/{id}", APPLICATION_CATEGORY_PATH), getNonExistingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isNotFound())
                .andReturn();
        assertNotNull(mvcResult);
    }

    @Test
    public void shouldSave() throws Exception {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        MvcResult mvcResult = mvc.perform(post(format("/%s", APPLICATION_CATEGORY_PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(item))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(item.getId())))
                .andReturn();
        assertNotNull(mvcResult);
        applicationCategoryElasticsearchRepository.delete(item);
    }

    @Test
    public void shouldDeleteById() throws Exception {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory saved = applicationCategoryElasticsearchRepository.save(item);
        String id = saved.getId();
        MvcResult mvcResult = mvc.perform(delete(format("/%s/{id}", APPLICATION_CATEGORY_PATH), id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isNoContent())
                .andReturn();
        assertNotNull(mvcResult);
        boolean exists = applicationCategoryElasticsearchRepository.existsById(id);
        assertFalse(exists);
    }

    @Test
    public void shouldNotDeleteById() throws Exception {
        MvcResult mvcResult = mvc.perform(delete(format("/%s/{id}", APPLICATION_CATEGORY_PATH), getNonExistingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isNoContent())
                .andReturn();
        assertNotNull(mvcResult);
    }

    @Test
    public void shouldUpdate() throws Exception {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory saved = applicationCategoryElasticsearchRepository.save(item);
        String id = saved.getId();
        MvcResult mvcResult = mvc.perform(put(format("/%s/{id}", APPLICATION_CATEGORY_PATH), id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(item))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(mvcResult);
        Optional<ApplicationCategory> updated = applicationCategoryElasticsearchRepository.findById(id);
        assertTrue(updated.isPresent());
        applicationCategoryElasticsearchRepository.deleteById(id);
    }

    @Test
    public void shouldNotUpdate() throws Exception {
        ApplicationCategory item = sampleApplicationCategoryProvider.getItem();
        ApplicationCategory saved = applicationCategoryElasticsearchRepository.save(item);
        MvcResult mvcResult = mvc.perform(put(format("/%s/{id}", APPLICATION_CATEGORY_PATH), getNonExistingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(item))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isNotFound())
                .andReturn();
        assertNotNull(mvcResult);
        applicationCategoryElasticsearchRepository.deleteById(saved.getId());
    }

    @Test
    public void shouldCount() throws Exception {
        List<ApplicationCategory> items = sampleApplicationCategoryProvider.getItems(numberOfItems);
        Iterable<ApplicationCategory> saved = applicationCategoryElasticsearchRepository.saveAll(items);
        MvcResult mvcResult = mvc.perform(get(format("/%s/%s", APPLICATION_CATEGORY_PATH, COUNT_PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(mvcResult);
        applicationCategoryElasticsearchRepository.deleteAll(saved);
    }

    private @NotNull String getNonExistingId() {
        return "No Such ID";
    }
}
