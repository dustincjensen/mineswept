package ui;

import java.awt.Color;

public class HexToRgb {
	/**
	 * Return a color object based on a 7 character hex code.
	 * Example: #123456 including the #.
	 * 
	 * @param hexColorCode the color of the hex code.
	 * @return the new color object.
	 */
    public static Color convert(String hexColorCode) {
		return new Color(
			Integer.valueOf(hexColorCode.substring(1, 3), 16),
			Integer.valueOf(hexColorCode.substring(3, 5), 16),
			Integer.valueOf(hexColorCode.substring(5, 7), 16)
		);
	}
}