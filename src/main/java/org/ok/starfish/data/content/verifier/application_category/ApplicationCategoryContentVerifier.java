package org.ok.starfish.data.content.verifier.application_category;

import org.ok.starfish.model.applicaton_category.ApplicationCategory;

import javax.validation.constraints.NotNull;

public interface ApplicationCategoryContentVerifier {

    @NotNull Iterable<ApplicationCategory> findLoaded(@NotNull Iterable<ApplicationCategory> applicationCategories);

    @NotNull Iterable<ApplicationCategory> findNotLoaded(@NotNull Iterable<ApplicationCategory> applicationCategories);
}
