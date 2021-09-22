package org.ok.starfish.data.service.application;

import org.apache.commons.lang3.RandomUtils;
import org.ok.starfish.data.repository.es.application.ApplicationCategoryElasticsearchRepository;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ApplicationCategoryServiceElasticsearch implements ApplicationCategoryService {

    private final ApplicationCategoryElasticsearchRepository applicationCategoryElasticsearchRepository;

    public ApplicationCategoryServiceElasticsearch(@NotNull ApplicationCategoryElasticsearchRepository applicationCategoryElasticsearchRepository) {
        this.applicationCategoryElasticsearchRepository = applicationCategoryElasticsearchRepository;
    }

    @Override
    public @NotNull List<ApplicationCategory> findAll() {
        Iterable<ApplicationCategory> items = applicationCategoryElasticsearchRepository.findAll();
        return StreamSupport
                .stream(items.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public @NotNull Optional<ApplicationCategory> findById(@NotNull String id) {
        return applicationCategoryElasticsearchRepository.findById(id);
    }

    @Override
    public Optional<ApplicationCategory> findRandom() {
        List<ApplicationCategory> items = findAll();
        if(items.isEmpty()) {
            return Optional.empty();
        }
        ApplicationCategory item = items.get(RandomUtils.nextInt(0, items.size()));
        return Optional.of(item);
    }

    @Override
    public @NotNull ApplicationCategory save(@NotNull ApplicationCategory applicationCategory) {
        return applicationCategoryElasticsearchRepository.save(applicationCategory);
    }

    @Override
    public @NotNull Iterable<ApplicationCategory> saveAll(@NotNull Iterable<ApplicationCategory> applicationCategories) {
        return applicationCategoryElasticsearchRepository.saveAll(applicationCategories);
    }

    @Override
    public Optional<ApplicationCategory> update(@NotNull String id, @NotNull ApplicationCategory applicationCategory) {
        Optional<ApplicationCategory> result = Optional.empty();
        if(existsById(id)) {
            result = Optional.of(save(applicationCategory));
        }
        return result;
    }

    @Override
    public void deleteById(@NotNull String id) {
        applicationCategoryElasticsearchRepository.deleteById(id);
    }

    @Override
    public long count() {
        return applicationCategoryElasticsearchRepository.count();
    }

    @Override
    public boolean existsById(@NotNull String id) {
        return applicationCategoryElasticsearchRepository.existsById(id);
    }

    @Override
    public boolean exists(@NotNull @NotNull ApplicationCategory applicationCategory) {
        Optional<ApplicationCategory> optionalApplicationCategory = applicationCategoryElasticsearchRepository.findByName(applicationCategory.getName());
        return optionalApplicationCategory.isPresent();
    }
}
