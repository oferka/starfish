package org.ok.starfish.data.content.provider.account;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class AccountsCsvLineReader {

    public @NotNull AccountLine read(@NotNull String[] line) {
        String accountSymbol = line[0];
        String accountName = line[1];
        String accountSector = line[2];
        return new AccountLine(
                accountSymbol,
                accountName,
                accountSector
        );
    }
}
