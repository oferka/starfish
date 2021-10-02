package org.ok.starfish.data.content.provider.application;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface ApplicationCategoryNameProvider {

    @NotNull @Size(min = 2, max = 64) @NotBlank String get();
}
