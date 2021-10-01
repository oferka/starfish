package org.ok.starfish.data.content.provider;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public interface CreationDateProvider {

    @NotNull ZonedDateTime getNow();
}
