package org.ok.starfish.data.content.provider;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdProviderImpl implements IdProvider {

    @Override
    public String getRandom() {
        return UUID.randomUUID().toString();
    }
}
