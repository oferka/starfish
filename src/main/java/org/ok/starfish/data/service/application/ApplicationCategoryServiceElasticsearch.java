package org.ok.starfish.data.service.application;

import org.ok.starfish.data.repository.es.application.ApplicationCategoryElasticsearchRepository;
import org.ok.starfish.model.application.ApplicationCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ApplicationCategoryServiceElasticsearch implements ApplicationCategoryService {

    private final ApplicationCategoryElasticsearchRepository applicationCategoryElasticsearchRepository;

    public ApplicationCategoryServiceElasticsearch(ApplicationCategoryElasticsearchRepository applicationCategoryElasticsearchRepository) {
        this.applicationCategoryElasticsearchRepository = applicationCategoryElasticsearchRepository;
    }

    @Override
    public List<ApplicationCategory> findAll() {
        Iterable<ApplicationCategory> applicationCategories = applicationCategoryElasticsearchRepository.findAll();
        return StreamSupport
                .stream(applicationCategories.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ApplicationCategory> findById(String id) {
        return applicationCategoryElasticsearchRepository.findById(id);
    }

    @Override
    public ApplicationCategory save(ApplicationCategory applicationCategory) {
        return applicationCategoryElasticsearchRepository.save(applicationCategory);
    }

    @Override
    public ApplicationCategory update(String id, ApplicationCategory applicationCategory) {
        ApplicationCategory result = null;
        if(existsById(id)) {
            result = save(applicationCategory);
        }
        return result;
    }

    @Override
    public Iterable<ApplicationCategory> save(Iterable<ApplicationCategory> applicationCategories) {
        return applicationCategoryElasticsearchRepository.saveAll(applicationCategories);
    }

    @Override
    public void deleteById(String id) {
        applicationCategoryElasticsearchRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return applicationCategoryElasticsearchRepository.existsById(id);
    }

    @Override
    public long count() {
        return applicationCategoryElasticsearchRepository.count();
    }
}
