package org.ok.starfish.data.content.provider.user.properties;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

@Service
public class UserLatitudeProviderImpl implements UserLatitudeProvider {

    @Override
    public double get() {
        return Double.parseDouble(new Faker().address().latitude());
    }
}
