package org.ok.starfish.data.content.provider.user.properties;

import javax.validation.constraints.Positive;

public interface UserStreetNumberProvider {

    @Positive int get();
}
