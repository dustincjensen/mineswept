package models.options;

import java.util.Map;

public enum BorderType {
    EMPTY,
    BEVEL_RAISED,
    BEVEL_LOWERED,
    CLASSIC_BEVEL_RAISED,
    CLASSIC_BEVEL_LOWERED;

    private static Map<BorderType, String> lookup = Map.of(
        EMPTY, "Empty",
        BEVEL_RAISED, "Raised Bevel",
        BEVEL_LOWERED, "Lowered Bevel",
        CLASSIC_BEVEL_RAISED, "Classic Raised Bevel",
        CLASSIC_BEVEL_LOWERED, "Classic Lowered Bevel"
    );

    @Override
    public String toString() {
        return lookup.get(this);
    }
}