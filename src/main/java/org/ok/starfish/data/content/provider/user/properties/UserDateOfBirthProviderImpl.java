package org.ok.starfish.data.content.provider.user.properties;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class UserDateOfBirthProviderImpl implements UserDateOfBirthProvider {

    @Override
    public ZonedDateTime get() {
        long daysAgo = RandomUtils.nextLong(5000, 80000);
        return ZonedDateTime.now().minusDays(daysAgo);
    }
}
