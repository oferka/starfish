package org.ok.starfish.data.content.loader.application;

import org.ok.starfish.model.application.Application;

import javax.validation.constraints.NotNull;

public interface ApplicationContentLoader {

    @NotNull Iterable<Application> load();
}
