package org.ok.starfish.data.service.applicaton_category;

import org.ok.starfish.model.applicaton_category.ApplicationCategory;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface ApplicationCategoryService {

    @NotNull List<ApplicationCategory> findAll();

    @NotNull Optional<ApplicationCategory> findById(@NotNull String id);

    @NotNull List<ApplicationCategory> findByName(@NotNull String name);

    @NotNull List<ApplicationCategory> findByCreatedDate(@NotNull ZonedDateTime createdDate);

    @NotNull Optional<ApplicationCategory> findRandom();

    @NotNull List<ApplicationCategory> findRandom(int count);

    long count();

    @NotNull ApplicationCategory save(@NotNull ApplicationCategory applicationCategory);

    @NotNull Iterable<ApplicationCategory> saveAll(@NotNull Iterable<ApplicationCategory> applicationCategories);

    @NotNull Optional<ApplicationCategory> update(@NotNull String id, @NotNull ApplicationCategory applicationCategory);

    void deleteById(@NotNull String id);

    boolean existsById(@NotNull String id);

    boolean exists(@NotNull ApplicationCategory applicationCategory);
}
