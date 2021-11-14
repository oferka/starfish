package org.ok.starfish.data.content.provider.vendor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VendorLine {

    @Getter
    private String name;

    @Getter
    private String logo;
}
