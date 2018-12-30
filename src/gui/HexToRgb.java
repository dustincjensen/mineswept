package gui;

import java.awt.Color;

public class HexToRgb {
    public static Color convert(String hexColorCode) {
		return new Color(
			Integer.valueOf(hexColorCode.substring(1, 3), 16),
			Integer.valueOf(hexColorCode.substring(3, 5), 16),
			Integer.valueOf(hexColorCode.substring(5, 7), 16)
		);
	}
}