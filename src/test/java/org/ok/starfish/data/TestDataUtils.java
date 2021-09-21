package org.ok.starfish.data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class TestDataUtils {

    public static @NotNull String getUniqueId() {
        return UUID.randomUUID().toString();
    }

    public static @NotNull String getNonExistingId() {
        return "No Such ID";
    }
}
