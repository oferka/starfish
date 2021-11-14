package org.ok.starfish.data.service.vendor;

import org.apache.commons.lang3.RandomUtils;
import org.ok.starfish.data.repository.es.vendor.VendorElasticsearchRepository;
import org.ok.starfish.model.vendor.Vendor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorElasticsearchRepository vendorElasticsearchRepository;

    public VendorServiceImpl(@NotNull VendorElasticsearchRepository vendorElasticsearchRepository) {
        this.vendorElasticsearchRepository = vendorElasticsearchRepository;
    }

    @Override
    public @NotNull List<Vendor> findAll() {
        Iterable<Vendor> items = vendorElasticsearchRepository.findAll();
        return StreamSupport
                .stream(items.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public @NotNull Optional<Vendor> findById(@NotNull String id) {
        return vendorElasticsearchRepository.findById(id);
    }

    @Override
    public @NotNull List<Vendor> findByName(@NotNull String name) {
        return vendorElasticsearchRepository.findByName(name);
    }

    @Override
    public List<Vendor> findByCreatedDate(ZonedDateTime createdDate) {
        return vendorElasticsearchRepository.findByCreatedDate(createdDate);
    }

    @Override
    public Optional<Vendor> findRandom() {
        List<Vendor> items = findAll();
        if(items.isEmpty()) {
            return Optional.empty();
        }
        Vendor item = items.get(RandomUtils.nextInt(0, items.size()));
        return Optional.of(item);
    }

    @Override
    public @NotNull Vendor save(@NotNull Vendor vendor) {
        return vendorElasticsearchRepository.save(vendor);
    }

    @Override
    public @NotNull Iterable<Vendor> saveAll(@NotNull Iterable<Vendor> vendors) {
        return vendorElasticsearchRepository.saveAll(vendors);
    }

    @Override
    public Optional<Vendor> update(@NotNull String id, @NotNull Vendor vendor) {
        Optional<Vendor> result = Optional.empty();
        if(existsById(id)) {
            result = Optional.of(save(vendor));
        }
        return result;
    }

    @Override
    public void deleteById(@NotNull String id) {
        vendorElasticsearchRepository.deleteById(id);
    }

    @Override
    public long count() {
        return vendorElasticsearchRepository.count();
    }

    @Override
    public boolean existsById(@NotNull String id) {
        return vendorElasticsearchRepository.existsById(id);
    }

    @Override
    public boolean exists(@NotNull Vendor vendor) {
        List<Vendor> applications = vendorElasticsearchRepository.findByName(vendor.getName());
        return !applications.isEmpty();
    }
}
