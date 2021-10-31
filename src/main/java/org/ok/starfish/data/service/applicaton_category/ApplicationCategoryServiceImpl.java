package org.ok.starfish.data.service.applicaton_category;

import org.apache.commons.lang3.RandomUtils;
import org.ok.starfish.data.repository.es.applicaton_category.ApplicationCategoryElasticsearchRepository;
import org.ok.starfish.model.applicaton_category.ApplicationCategory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ApplicationCategoryServiceImpl implements ApplicationCategoryService {

    private final ApplicationCategoryElasticsearchRepository applicationCategoryElasticsearchRepository;

    public ApplicationCategoryServiceImpl(@NotNull ApplicationCategoryElasticsearchRepository applicationCategoryElasticsearchRepository) {
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
    public @NotNull List<ApplicationCategory> findByName(@NotNull String name) {
        return applicationCategoryElasticsearchRepository.findByName(name);
    }

    @Override
    public List<ApplicationCategory> findByCreatedDate(ZonedDateTime createdDate) {
        return applicationCategoryElasticsearchRepository.findByCreatedDate(createdDate);
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
    public List<ApplicationCategory> findRandom(int count) {
        List<ApplicationCategory> result = new ArrayList<>();
        for(int i=0; i<count; i++) {
            Optional<ApplicationCategory> applicationCategory = findRandom();
            if(applicationCategory.isPresent()) {
                result.add(applicationCategory.get());
            }
            else {
                throw new RuntimeException("Failed to create a random list of application categories");
            }
        }
        return result;
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
        List<ApplicationCategory> applicationCategories = applicationCategoryElasticsearchRepository.findByName(applicationCategory.getName());
        return !applicationCategories.isEmpty();
    }
}
