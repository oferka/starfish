package org.ok.starfish.data.content.provider.user.properties;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static java.lang.String.format;

@Service
public class UserTimezoneOffsetProviderImpl implements UserTimezoneOffsetProvider {

    @Override
    public @NotNull @Size(min = 2, max = 64) @NotBlank String get() {
        String sign = RandomUtils.nextBoolean()?"+":"-";
        int offset = RandomUtils.nextInt(1,13);
        return format("%s%s:00", sign, offset);
    }
}
