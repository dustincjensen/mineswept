package gui.menu;

import gui.ClassFactory;
import javax.swing.JMenuBar;

/**
 * Sets up the menus for the JFrame.
 */
public class Menus extends JMenuBar {
	public Menus(GameMenu gameMenu, ViewMenu viewMenu, HelpMenu helpMenu) {
		add(gameMenu);
		add(viewMenu);
		add(helpMenu);
	}
}