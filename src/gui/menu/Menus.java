package gui.menu;

import gui.ClassFactory;
import javax.swing.JMenuBar;

/**
 * Sets up the menus for the JFrame.
 */
public class Menus extends JMenuBar {
	public Menus() {
		add(ClassFactory.create(GameMenu.class));
		add(ClassFactory.create(ViewMenu.class));
		add(ClassFactory.create(HelpMenu.class));
	}
}