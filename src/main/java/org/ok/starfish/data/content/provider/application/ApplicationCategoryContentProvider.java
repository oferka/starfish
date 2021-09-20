package org.ok.starfish.data.content.provider.application;

import org.ok.starfish.model.application.ApplicationCategory;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ApplicationCategoryContentProvider {

    @NotNull List<ApplicationCategory> get();
}
