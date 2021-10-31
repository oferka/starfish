package org.ok.starfish.data.content.loader.applicaton_category;

import org.ok.starfish.model.applicaton_category.ApplicationCategory;

import javax.validation.constraints.NotNull;

public interface ApplicationCategoryContentLoader {

    @NotNull Iterable<ApplicationCategory> ensureContentLoaded();
}
