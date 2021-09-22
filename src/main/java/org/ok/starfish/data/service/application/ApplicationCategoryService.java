package org.ok.starfish.data.service.application;

import org.ok.starfish.model.application.ApplicationCategory;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ApplicationCategoryService {

    @NotNull List<ApplicationCategory> findAll();

    @NotNull Optional<ApplicationCategory> findById(@NotNull String id);

    @NotNull Optional<ApplicationCategory> findRandom();

    long count();

    @NotNull ApplicationCategory save(@NotNull ApplicationCategory applicationCategory);

    @NotNull Iterable<ApplicationCategory> saveAll(@NotNull Iterable<ApplicationCategory> applicationCategories);

    @NotNull Optional<ApplicationCategory> update(@NotNull String id, @NotNull ApplicationCategory applicationCategory);

    void deleteById(@NotNull String id);

    boolean existsById(@NotNull String id);

    boolean exists(@NotNull ApplicationCategory applicationCategory);
}
