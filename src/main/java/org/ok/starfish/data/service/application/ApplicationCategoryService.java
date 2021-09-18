package org.ok.starfish.data.service.application;

import org.ok.starfish.model.application.ApplicationCategory;

import java.util.List;
import java.util.Optional;

public interface ApplicationCategoryService {

    List<ApplicationCategory> findAll();

    Optional<ApplicationCategory> findById(String id);

    long count();

    ApplicationCategory save(ApplicationCategory applicationCategory);

    ApplicationCategory update(String id, ApplicationCategory applicationCategory);

    Iterable<ApplicationCategory> save(Iterable<ApplicationCategory> applicationCategories);

    void deleteById(String id);

    boolean existsById(String id);
}
