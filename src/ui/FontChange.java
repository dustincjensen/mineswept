package ui;

import java.awt.Font;
import javax.swing.JComponent;

public class FontChange {
	/**
	 * Set the font size of a JComponent.
	 * 
	 * @param component the component to update font size.
	 * @param size the new font size to use.
	 */
	public static void setFont(JComponent component, int size) {
		var font = component.getFont();
		var name = font.getFontName();
		var style = font.getStyle();
		component.setFont(new Font(name, style, size));
	}
}