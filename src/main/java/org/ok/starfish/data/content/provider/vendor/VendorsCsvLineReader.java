package org.ok.starfish.data.content.provider.vendor;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class VendorsCsvLineReader {

    public @NotNull VendorLine read(@NotNull String[] line) {
        String name = line[0];
        String logo = line[1];
        return new VendorLine(name, logo);
    }
}
