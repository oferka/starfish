package org.ok.starfish.data.service.application;

import org.ok.starfish.model.application.Application;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface ApplicationService {

    @NotNull List<Application> findAll();

    @NotNull Optional<Application> findById(@NotNull String id);

    @NotNull List<Application> findByName(@NotNull String name);

    @NotNull List<Application> findByCreatedDate(@NotNull ZonedDateTime createdDate);

    @NotNull Optional<Application> findRandom();

    long count();

    @NotNull Application save(@NotNull Application application);

    @NotNull Iterable<Application> saveAll(@NotNull Iterable<Application> applications);

    @NotNull Optional<Application> update(@NotNull String id, @NotNull Application application);

    void deleteById(@NotNull String id);

    boolean existsById(@NotNull String id);

    boolean exists(@NotNull @NotNull Application application);
}
