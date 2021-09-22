package org.ok.starfish.data.content.verifier.application;

import org.ok.starfish.model.application.Application;

import javax.validation.constraints.NotNull;

public interface ApplicationContentVerifier {

    @NotNull Iterable<Application> findLoaded(@NotNull Iterable<Application> applications);

    @NotNull Iterable<Application> findNotLoaded(@NotNull Iterable<Application> applications);
}
