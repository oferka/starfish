package org.ok.starfish.data.content.provider.user.properties;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class UserDateOfRegistrationProviderImpl implements UserDateOfRegistrationProvider {

    @Override
    public ZonedDateTime get() {
        long daysAgo = RandomUtils.nextLong(3, 1000);
        return ZonedDateTime.now().minusDays(daysAgo);
    }
}
