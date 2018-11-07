package gui.menu;

import javax.swing.JMenuBar;

/**
 * Sets up the menus for the JFrame.
 */
public class Menus extends JMenuBar {
	public Menus() {
		add(new GameMenu());
		add(new ViewMenu());
		add(new HelpMenu());
	}
}