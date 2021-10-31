package org.ok.starfish.data.content.provider.applicaton_category;

import org.ok.starfish.model.applicaton_category.ApplicationCategory;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ApplicationCategoryContentProvider {

    @NotNull List<ApplicationCategory> get(int numberOfItems);
}
