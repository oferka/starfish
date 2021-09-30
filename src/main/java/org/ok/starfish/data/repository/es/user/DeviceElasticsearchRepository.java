package org.ok.starfish.data.repository.es.user;

import org.ok.starfish.model.user.Device;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceElasticsearchRepository extends ElasticsearchRepository<Device, String> {

    List<Device> findByName(String name);
}
