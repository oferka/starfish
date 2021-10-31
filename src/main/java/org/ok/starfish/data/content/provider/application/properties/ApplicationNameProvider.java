package org.ok.starfish.data.content.provider.application.properties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface ApplicationNameProvider {

    @NotNull @Size(min = 2, max = 64) @NotBlank String get();
}
