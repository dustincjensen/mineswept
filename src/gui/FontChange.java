package gui;

import java.awt.Font;

import javax.swing.JComponent;

public class FontChange {
	public static void setFont(JComponent change, int size) {
		Font f = change.getFont();
		change.setFont(new Font(f.getFontName(), f.getStyle(), size));
	}
}