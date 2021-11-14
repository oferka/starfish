package org.ok.starfish.data.content.provider.vendor.properties;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

public interface VendorLogoProvider {

    @NotNull @URL String get();
}
