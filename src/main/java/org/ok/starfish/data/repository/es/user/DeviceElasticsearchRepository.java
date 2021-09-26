package org.ok.starfish.data.repository.es.user;

import org.ok.starfish.model.user.Device;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceElasticsearchRepository extends ElasticsearchRepository<Device, String> {

    Optional<Device> findByName(String name);
}
