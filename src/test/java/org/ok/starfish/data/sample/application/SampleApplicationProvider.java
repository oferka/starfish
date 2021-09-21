package org.ok.starfish.data.sample.application;

import org.ok.starfish.model.application.Application;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SampleApplicationProvider {

    @NotNull Application getItem();

    @NotNull List<Application> getItems(int numberOfItems);
}
