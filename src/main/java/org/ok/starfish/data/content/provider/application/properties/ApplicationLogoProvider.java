package org.ok.starfish.data.content.provider.application.properties;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

public interface ApplicationLogoProvider {

    @NotNull @URL String get();
}
