package org.ok.starfish.data.content.provider.vendor.properties;

import com.github.javafaker.Faker;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class VendorLogoProviderImpl implements VendorLogoProvider {

    @Override
    public @NotNull @URL String get() {
        return new Faker().avatar().image();
    }
}
