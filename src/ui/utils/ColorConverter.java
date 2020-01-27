package ui.utils;

public class ColorConverter {
    /**
     * Convert a model.options.Color to a java.awt.Color.
     * 
     * @param colorToConvert the model.options.Color to convert.
     * @return a new instance of a java.awt.Color.
     */
    public static java.awt.Color convert(models.options.Color colorToConvert) {
        if (colorToConvert == null) {
            return null;
        }

        return new java.awt.Color(
            colorToConvert.r,
            colorToConvert.g,
            colorToConvert.b
        );
    }

    /**
     * Convert a java.awt.Color to a models.options.Color.
     * 
     * @param colorToConvert the java.awt.Color to convert.
     * @return a new instance of a models.options.Color.
     */
    public static models.options.Color convertBack(java.awt.Color colorToConvert) {
        if (colorToConvert == null) {
            return null;
        }

        return new models.options.Color(
            colorToConvert.getRed(),
            colorToConvert.getGreen(),
            colorToConvert.getBlue()
        );
    }
}