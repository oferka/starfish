package org.ok.starfish.data.content.provider.user.properties;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

public interface UserMediumPictureProvider {

    @NotNull @URL String get();
}
