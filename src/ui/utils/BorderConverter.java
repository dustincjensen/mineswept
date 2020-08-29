package ui.utils;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import models.options.BorderType;
import java.util.function.Supplier;
import java.util.Map;
import java.awt.Color;

public class BorderConverter {
    private static Map<BorderType, Supplier<Border>> lookup = Map.of(
        BorderType.EMPTY, (Supplier<Border>)() -> BorderFactory.createEmptyBorder(),
        BorderType.BEVEL_LOWERED, (Supplier<Border>)() -> BorderFactory.createLoweredBevelBorder(),
        BorderType.BEVEL_RAISED, (Supplier<Border>)() -> BorderFactory.createRaisedBevelBorder(),
        BorderType.CLASSIC_BEVEL_LOWERED, (Supplier<Border>)() -> BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.GRAY),
        BorderType.CLASSIC_BEVEL_RAISED, (Supplier<Border>)() -> BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY)
    );

    /**
     * Convert a model.options.Border to a javax.swing.border.Border.
     * 
     * @param borderToConvert the model.options.Border to convert.
     * @return a new instance of javax.swing.border.Border.
     */
    public static javax.swing.border.Border convert(models.options.BorderType type) {
        if (type == null) {
            return null;
        }

        var supplier = lookup.get(type);
        if (supplier == null) {
            return null;
        }
        
        var value = supplier.get();
        if (value == null) {
            return null;
        }
        
        return value;
    }
}