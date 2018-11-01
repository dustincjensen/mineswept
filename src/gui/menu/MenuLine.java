package gui.menu;

import javax.swing.*;
import java.awt.*;

/**
 * Sets up the menu bar for the JFrame.
 */
public class MenuLine extends JMenuBar {
	private static GameMenu gameMenu;
	private static ViewMenu viewMenu;
	private static HelpMenu helpMenu;

	public MenuLine() {
		gameMenu = new GameMenu();
		viewMenu = new ViewMenu();
		helpMenu = new HelpMenu();
		add(gameMenu);
		add(viewMenu);
		add(helpMenu);
	}
}