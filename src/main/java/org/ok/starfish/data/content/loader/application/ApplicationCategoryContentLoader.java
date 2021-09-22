package org.ok.starfish.data.content.loader.application;

import org.ok.starfish.model.application.ApplicationCategory;

import javax.validation.constraints.NotNull;

public interface ApplicationCategoryContentLoader {

    @NotNull Iterable<ApplicationCategory> ensureContentLoaded();
}
