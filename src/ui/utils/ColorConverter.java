package ui.utils;

public class ColorConverter {
    /**
     * Convert a java.awt.Color to a models.options.Color.
     * 
     * @param color the java.awt.Color to convert.
     * @return a new instance of a models.options.Color.
     */
    public static String convertBack(java.awt.Color color) {
        if (color == null) {
            return null;
        }

        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}