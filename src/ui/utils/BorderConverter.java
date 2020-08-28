package ui.utils;

import javax.swing.BorderFactory;
import models.options.BorderType;

public class BorderConverter {
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

        if (type == BorderType.BEVEL_RAISED) {
            return BorderFactory.createRaisedBevelBorder();
        } else if (type == BorderType.BEVEL_LOWERED) {
            return BorderFactory.createLoweredBevelBorder();
        } else if (type == BorderType.EMPTY) {
            return BorderFactory.createEmptyBorder();
        } else {
            return null;
        }
    }
}