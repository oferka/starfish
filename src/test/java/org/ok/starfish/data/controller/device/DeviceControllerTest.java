package org.ok.starfish.data.controller.device;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ok.starfish.StarfishApplication;
import org.ok.starfish.data.repository.es.device.DeviceElasticsearchRepository;
import org.ok.starfish.data.sample.device.SampleDeviceProvider;
import org.ok.starfish.model.device.Device;
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
import static org.ok.starfish.data.controller.Paths.COUNT_PATH;
import static org.ok.starfish.data.controller.Paths.DEVICE_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = StarfishApplication.class)
public class DeviceControllerTest {

    private MockMvc mvc;

    @Autowired
    private DeviceElasticsearchRepository deviceElasticsearchRepository;

    @Autowired
    private SampleDeviceProvider sampleDeviceProvider;

    private long contentCountBefore;

    private final int numberOfItemsToLoad = 10;

    @BeforeEach
    void captureContentStatus() {
        contentCountBefore = deviceElasticsearchRepository.count();
    }

    @AfterEach
    void verifyContentStatusNotChanged() {
        long contentCountAfter = deviceElasticsearchRepository.count();
        assertEquals(contentCountBefore, contentCountAfter);
    }

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldFindAll() throws Exception {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        MvcResult mvcResult = mvc.perform(get(format("/%s", DEVICE_PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(items.get(0).getId())))
                .andReturn();
        assertNotNull(mvcResult);
        deviceElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldFindById() throws Exception {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        String id = items.get(0).getId();
        MvcResult mvcResult = mvc.perform(get(format("/%s/{id}", DEVICE_PATH), id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(id)))
                .andReturn();
        assertNotNull(mvcResult);
        deviceElasticsearchRepository.deleteAll(saved);
    }

    @Test
    public void shouldNotFindById() throws Exception {
        MvcResult mvcResult = mvc.perform(get(format("/%s/{id}", DEVICE_PATH), getNonExistingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isNotFound())
                .andReturn();
        assertNotNull(mvcResult);
    }

    @Test
    public void shouldSave() throws Exception {
        Device item = sampleDeviceProvider.getItem();
        MvcResult mvcResult = mvc.perform(post(format("/%s", DEVICE_PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(item))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(item.getId())))
                .andReturn();
        assertNotNull(mvcResult);
        deviceElasticsearchRepository.delete(item);
    }

    @Test
    public void shouldDeleteById() throws Exception {
        Device item = sampleDeviceProvider.getItem();
        Device saved = deviceElasticsearchRepository.save(item);
        String id = saved.getId();
        MvcResult mvcResult = mvc.perform(delete(format("/%s/{id}", DEVICE_PATH), id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isNoContent())
                .andReturn();
        assertNotNull(mvcResult);
        boolean exists = deviceElasticsearchRepository.existsById(id);
        assertFalse(exists);
    }

    @Test
    public void shouldNotDeleteById() throws Exception {
        MvcResult mvcResult = mvc.perform(delete(format("/%s/{id}", DEVICE_PATH), getNonExistingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isNoContent())
                .andReturn();
        assertNotNull(mvcResult);
    }

    @Test
    public void shouldUpdate() throws Exception {
        Device item = sampleDeviceProvider.getItem();
        Device saved = deviceElasticsearchRepository.save(item);
        String id = saved.getId();
        MvcResult mvcResult = mvc.perform(put(format("/%s/{id}", DEVICE_PATH), id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(item))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(mvcResult);
        Optional<Device> updated = deviceElasticsearchRepository.findById(id);
        assertTrue(updated.isPresent());
        deviceElasticsearchRepository.deleteById(id);
    }

    @Test
    public void shouldNotUpdate() throws Exception {
        Device item = sampleDeviceProvider.getItem();
        Device saved = deviceElasticsearchRepository.save(item);
        MvcResult mvcResult = mvc.perform(put(format("/%s/{id}", DEVICE_PATH), getNonExistingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(item))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isNotFound())
                .andReturn();
        assertNotNull(mvcResult);
        deviceElasticsearchRepository.deleteById(saved.getId());
    }

    @Test
    public void shouldCount() throws Exception {
        List<Device> items = sampleDeviceProvider.getItems(numberOfItemsToLoad);
        Iterable<Device> saved = deviceElasticsearchRepository.saveAll(items);
        MvcResult mvcResult = mvc.perform(get(format("/%s/%s", DEVICE_PATH, COUNT_PATH))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andReturn();
        assertNotNull(mvcResult);
        deviceElasticsearchRepository.deleteAll(saved);
    }
}
