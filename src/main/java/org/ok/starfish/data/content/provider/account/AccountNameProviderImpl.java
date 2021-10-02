package org.ok.starfish.data.content.provider.account;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountNameProviderImpl implements AccountNameProvider {

    @Override
    public @NotNull @Size(min = 2, max = 64) @NotBlank String get() {
        return new Faker().company().name();
    }

    @Override
    public List<@NotNull @Size(min = 2, max = 64) @NotBlank String> get(int numberOfItems) {
        List<String> result = new ArrayList<>();
        for(int i=0; i<numberOfItems; i++) {
            result.add(get());
        }
        return result;
    }
}
