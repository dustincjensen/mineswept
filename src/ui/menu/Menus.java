package ui.menu;

import javax.swing.JMenuBar;

/**
 * Sets up the menus for the JFrame.
 */
@SuppressWarnings("serial")
public class Menus extends JMenuBar {
	public Menus(GameMenu gameMenu, ViewMenu viewMenu, HelpMenu helpMenu) {
		add(gameMenu);
		add(viewMenu);
		add(helpMenu);
	}
}