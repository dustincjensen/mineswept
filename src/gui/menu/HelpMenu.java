package gui.menu;

import gui.events.AboutEvent;
import gui.events.EventPublisher;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Sets up the help menu.
 */
public class HelpMenu extends JMenu {
	private EventPublisher service;

	public HelpMenu() {
		super("Help");

		// TODO replace with dependency injection.
		// Create an event publisher for the menu items to use.
		service = new EventPublisher();

		add(about());
	}

	/**
	 * Return a menu item that activates a dialog with information about the game.
	 * 
	 * @return the about menu item.
	 */
	public JMenuItem about() {
		var about = new JMenuItem("About");
		about.addActionListener(evt -> service.publish(new AboutEvent()));
		return about;
	}
}