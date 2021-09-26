package org.ok.starfish.data.content.verifier.user;

import org.ok.starfish.model.user.User;

import javax.validation.constraints.NotNull;

public interface UserContentVerifier {

    @NotNull Iterable<User> findLoaded(@NotNull Iterable<User> users);

    @NotNull Iterable<User> findNotLoaded(@NotNull Iterable<User> users);
}
