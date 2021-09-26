package org.ok.starfish.data.sample.user;

import org.ok.starfish.model.user.User;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SampleUserProvider {

    @NotNull User getItem();

    @NotNull List<User> getItems(int numberOfItems);
}
