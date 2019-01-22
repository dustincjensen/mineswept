package ui.utils;

public class ColorConverter {
    /**
     * Convert a model.options.Color to a java.awt.Color.
     * 
     * @param colorToConvert the model.options.Color to convert.
     * @return a new instance of a java.awt.Color.
     */
    public static java.awt.Color convert(models.options.Color colorToConvert) {
        return new java.awt.Color(
            colorToConvert.r,
            colorToConvert.g,
            colorToConvert.b
        );
    }
}