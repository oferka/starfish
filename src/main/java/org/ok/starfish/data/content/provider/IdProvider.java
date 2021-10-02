package org.ok.starfish.data.content.provider;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface IdProvider {

    @NotNull @Size(min = 2, max = 64) @NotBlank String getRandom();
}
