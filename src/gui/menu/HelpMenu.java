package gui.menu;

import gui.events.AboutEvent;
import gui.events.IEventPublisher;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import com.google.inject.Inject;

/**
 * Sets up the help menu.
 */
public class HelpMenu extends JMenu {
	@Inject
	private IEventPublisher service;

	public HelpMenu() {
		super("Help");
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