package org.ok.starfish.data.content.provider.user.properties;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

@Service
public class UserStreetNumberProviderImpl implements UserStreetNumberProvider {

    @Override
    public int get() {
        return Integer.parseInt(new Faker().address().streetAddressNumber());
    }
}
