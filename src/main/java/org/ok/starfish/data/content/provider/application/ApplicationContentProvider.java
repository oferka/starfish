package org.ok.starfish.data.content.provider.application;

import org.ok.starfish.model.application.Application;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ApplicationContentProvider {

    @NotNull List<Application> get(int numberOfItems);
}
