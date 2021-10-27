package org.ok.starfish.data.content.provider.account;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class AccountsCsvLineReader {

    public @NotNull AccountLine read(@NotNull String[] line) {
        String symbol = line[0];
        String name = line[1];
        String sector = line[2];
        return new AccountLine(
                symbol,
                name,
                sector
        );
    }
}
