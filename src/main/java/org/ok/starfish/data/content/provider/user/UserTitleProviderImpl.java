package org.ok.starfish.data.content.provider.user;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static java.util.Arrays.asList;

@Service
public class UserTitleProviderImpl implements UserTitleProvider {

    @Override
    public @NotNull @Size(min = 2, max = 64) @NotBlank String get() {
        List<String> values = asList(
                "Mr",
                "Ms",
                "Monsieur",
                "Mrs",
                "Miss"
        );
        return values.get(RandomUtils.nextInt(0, values.size()));
    }
}
