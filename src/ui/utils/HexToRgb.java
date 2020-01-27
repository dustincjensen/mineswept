package ui.utils;

public class HexToRgb {
	/**
	 * Return a color object based on a 7 character hex code.
	 * Example: #123456 including the #.
	 * 
	 * @param hexColorCode the color of the hex code.
	 * @return the new java.awt.Color object.
	 */
    public static java.awt.Color convert(String hexColorCode) {
		return new java.awt.Color(
			Integer.valueOf(hexColorCode.substring(1, 3), 16),
			Integer.valueOf(hexColorCode.substring(3, 5), 16),
			Integer.valueOf(hexColorCode.substring(5, 7), 16)
		);
	}

	/**
	 * Return a color object based on a 7 character hex code.
	 * Example: #123456 including the #.
	 * 
	 * @param hexColorCode the color of the hex code.
	 * @return the new models.options.Color object.
	 */
	public static models.options.Color toOptionsColor(String hexColorCode) {
		return new models.options.Color(
			Integer.valueOf(hexColorCode.substring(1, 3), 16),
			Integer.valueOf(hexColorCode.substring(3, 5), 16),
			Integer.valueOf(hexColorCode.substring(5, 7), 16)
		);
	}
}