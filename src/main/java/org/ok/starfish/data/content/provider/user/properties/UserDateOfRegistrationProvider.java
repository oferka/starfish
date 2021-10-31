package org.ok.starfish.data.content.provider.user.properties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.ZonedDateTime;

public interface UserDateOfRegistrationProvider {

    @NotNull @Past ZonedDateTime get();
}
