package org.ok.starfish.data.service.user;

import org.ok.starfish.model.user.User;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface UserService {

    @NotNull List<User> findAll();

    @NotNull Optional<User> findById(@NotNull String id);

    @NotNull List<User> findByGender(@NotNull String gender);

    @NotNull List<User> findByTitle(@NotNull String title);

    @NotNull List<User> findByFirstName(@NotNull String firstName);

    @NotNull List<User> findByCreatedDate(@NotNull ZonedDateTime createdDate);

    @NotNull Optional<User> findRandom();

    long count();

    @NotNull User save(@NotNull User user);

    @NotNull Iterable<User> saveAll(@NotNull Iterable<User> users);

    @NotNull Optional<User> update(@NotNull String id, @NotNull User user);

    void deleteById(@NotNull String id);

    boolean existsById(@NotNull String id);

    boolean exists(@NotNull @NotNull User user);
}
