package org.ok.starfish.data.content.provider.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountLine {

    @Getter
    private String accountSymbol;

    @Getter
    private String accountName;

    @Getter
    private String accountSector;
}
