package org.ok.starfish.data.repository.es.vendor;

import org.ok.starfish.model.vendor.Vendor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface VendorElasticsearchRepository extends ElasticsearchRepository<Vendor, String> {

    List<Vendor> findByName(String name);

    List<Vendor> findByCreatedDate(ZonedDateTime createdDate);
}
