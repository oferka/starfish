package org.ok.starfish.data.content.provider;

import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static java.time.ZonedDateTime.now;

@Service
public class CreationDateProviderImpl implements CreationDateProvider {

    @Override
    public ZonedDateTime getNow() {
        return now(ZoneOffset.UTC);
    }
}
