package org.ok.starfish.data.sample.applicaton_category;

import org.ok.starfish.model.applicaton_category.ApplicationCategory;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SampleApplicationCategoryProvider {

    @NotNull ApplicationCategory getItem();

    @NotNull List<ApplicationCategory> getItems(int numberOfItems);
}

