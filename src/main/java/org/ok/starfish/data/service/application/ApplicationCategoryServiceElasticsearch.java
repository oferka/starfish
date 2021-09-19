package org.ok.starfish.data.service.application;

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
        Iterable<ApplicationCategory> applicationCategories = applicationCategoryElasticsearchRepository.findAll();
        return StreamSupport
                .stream(applicationCategories.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public @NotNull Optional<ApplicationCategory> findById(@NotNull String id) {
        return applicationCategoryElasticsearchRepository.findById(id);
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
    public ApplicationCategory update(@NotNull String id, @NotNull ApplicationCategory applicationCategory) {
        ApplicationCategory result = null;
        if(existsById(id)) {
            result = save(applicationCategory);
        }
        return result;
    }

    @Override
    public void deleteById(@NotNull String id) {
        applicationCategoryElasticsearchRepository.deleteById(id);
    }

    @Override
    public boolean existsById(@NotNull String id) {
        return applicationCategoryElasticsearchRepository.existsById(id);
    }

    @Override
    public long count() {
        return applicationCategoryElasticsearchRepository.count();
    }
}
