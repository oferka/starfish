package org.ok.starfish.data.content.provider.application;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class ApplicationCategoriesCsvLineReader {

    public @NotNull ApplicationCategoryLine read(@NotNull String[] line) {
        String name = line[0];
        return new ApplicationCategoryLine(name);
    }
}
