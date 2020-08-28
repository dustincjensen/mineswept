package models.options;

import java.util.Map;

public enum BorderType {
    EMPTY,
    BEVEL_RAISED,
    BEVEL_LOWERED;

    private static Map<BorderType, String> lookup = Map.of(
        EMPTY, "Empty",
        BEVEL_RAISED, "Raised Bevel",
        BEVEL_LOWERED, "Lowered Bevel"
    );

    @Override
    public String toString() {
        return lookup.get(this);
    }
}