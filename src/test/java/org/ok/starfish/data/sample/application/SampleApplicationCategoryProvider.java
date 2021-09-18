package org.ok.starfish.data.sample.application;

import org.ok.starfish.model.application.ApplicationCategory;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SampleApplicationCategoryProvider {

    @NotNull ApplicationCategory getItem();

    @NotNull List<ApplicationCategory> getItems(int numberOfItems);
}

