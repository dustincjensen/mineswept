package gui;

import java.awt.Font;
import javax.swing.JLabel;

public class FontChange {
	public static void setFont(JLabel change, int size) {
		Font f = change.getFont();
		change.setFont(new Font(f.getFontName(), f.getStyle(), size));
	}
}