package org.ok.starfish.data;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.UUID;

public class TestDataUtils {

    public static @NotNull String getUniqueId() {
        return UUID.randomUUID().toString();
    }

    public static @NotNull String getNonExistingId() {
        return "No Such ID";
    }

    public static @NotNull String getNonExistingName() {
        return "No Such Name";
    }

    public static @NotNull int getNonExistingInteger() {
        return 13457452;
    }

    public static @NotNull double getNonExistingDouble() {
        return 134.57452D;
    }

    public static @NotNull ZonedDateTime getNonExistingDate() {
        return ZonedDateTime.now().plusSeconds(1234567L);
    }
}
