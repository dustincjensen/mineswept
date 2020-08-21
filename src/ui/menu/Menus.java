package ui.menu;

import javax.swing.BorderFactory;
import javax.swing.JMenuBar;

import ui.utils.HexToRgb;

/**
 * Sets up the menus for the JFrame.
 */
@SuppressWarnings("serial")
public class Menus extends JMenuBar {
	public Menus(GameMenu gameMenu, ViewMenu viewMenu, HelpMenu helpMenu) {
		add(gameMenu);
		add(viewMenu);
		add(helpMenu);

		setBackground(HexToRgb.convert("#333333"));
		setBorder(BorderFactory.createEmptyBorder());
	}
}