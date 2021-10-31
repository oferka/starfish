package org.ok.starfish.data.content.provider.user.properties;

import javax.validation.constraints.*;
import java.time.ZonedDateTime;

public interface UserDateOfBirthProvider {

    @NotNull @Past ZonedDateTime get();
}
