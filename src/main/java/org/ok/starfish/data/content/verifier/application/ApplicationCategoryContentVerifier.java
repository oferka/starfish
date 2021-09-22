package org.ok.starfish.data.content.verifier.application;

import org.ok.starfish.model.application.ApplicationCategory;

import javax.validation.constraints.NotNull;

public interface ApplicationCategoryContentVerifier {

    @NotNull Iterable<ApplicationCategory> findLoaded(@NotNull Iterable<ApplicationCategory> applicationCategories);

    @NotNull Iterable<ApplicationCategory> findNotLoaded(@NotNull Iterable<ApplicationCategory> applicationCategories);
}
