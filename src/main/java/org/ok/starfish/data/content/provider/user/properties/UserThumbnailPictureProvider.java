package org.ok.starfish.data.content.provider.user.properties;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

public interface UserThumbnailPictureProvider {

    @NotNull @URL String get();
}
