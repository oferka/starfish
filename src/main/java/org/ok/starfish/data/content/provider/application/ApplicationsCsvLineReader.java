package org.ok.starfish.data.content.provider.application;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class ApplicationsCsvLineReader {

    public @NotNull ApplicationLine read(@NotNull String[] line) {
        String name = line[0];
        String vendor = line[1];
        String logo = line[2];
        String categories = line[3];
        return new ApplicationLine(name, vendor, logo, categories);
    }
}
