package org.ok.starfish.data.content.provider.application;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class ApplicationsCsvLineReader {

    public @NotNull ApplicationLine read(@NotNull String[] line) {
        String name = line[0];
        String logo = line[1];
        String categories = line[2];
        return new ApplicationLine(name, logo, categories);
    }
}
