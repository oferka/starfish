package org.ok.starfish.data.content.provider.user;

import org.ok.starfish.model.user.User;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserContentProvider {

    @NotNull List<User> get(int numberOfItems);
}
