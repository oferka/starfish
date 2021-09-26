package org.ok.starfish.data.content.loader.user;

import org.ok.starfish.model.user.User;

import javax.validation.constraints.NotNull;

public interface UserContentLoader {

    @NotNull Iterable<User> ensureContentLoaded();
}
