package org.ok.starfish.data.service.application;

import org.apache.commons.lang3.RandomUtils;
import org.ok.starfish.data.repository.es.application.ApplicationElasticsearchRepository;
import org.ok.starfish.model.application.Application;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationElasticsearchRepository applicationElasticsearchRepository;

    public ApplicationServiceImpl(@NotNull ApplicationElasticsearchRepository applicationElasticsearchRepository) {
        this.applicationElasticsearchRepository = applicationElasticsearchRepository;
    }

    @Override
    public @NotNull List<Application> findAll() {
        Iterable<Application> items = applicationElasticsearchRepository.findAll();
        return StreamSupport
                .stream(items.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public @NotNull Optional<Application> findById(@NotNull String id) {
        return applicationElasticsearchRepository.findById(id);
    }

    @Override
    public @NotNull List<Application> findByName(@NotNull String name) {
        return applicationElasticsearchRepository.findByName(name);
    }

    @Override
    public Optional<Application> findRandom() {
        List<Application> items = findAll();
        if(items.isEmpty()) {
            return Optional.empty();
        }
        Application item = items.get(RandomUtils.nextInt(0, items.size()));
        return Optional.of(item);
    }

    @Override
    public @NotNull Application save(@NotNull Application application) {
        return applicationElasticsearchRepository.save(application);
    }

    @Override
    public @NotNull Iterable<Application> saveAll(@NotNull Iterable<Application> applications) {
        return applicationElasticsearchRepository.saveAll(applications);
    }

    @Override
    public Optional<Application> update(@NotNull String id, @NotNull Application application) {
        Optional<Application> result = Optional.empty();
        if(existsById(id)) {
            result = Optional.of(save(application));
        }
        return result;
    }

    @Override
    public void deleteById(@NotNull String id) {
        applicationElasticsearchRepository.deleteById(id);
    }

    @Override
    public long count() {
        return applicationElasticsearchRepository.count();
    }

    @Override
    public boolean existsById(@NotNull String id) {
        return applicationElasticsearchRepository.existsById(id);
    }

    @Override
    public boolean exists(@NotNull Application application) {
        List<Application> applications = applicationElasticsearchRepository.findByName(application.getName());
        return !applications.isEmpty();
    }
}
