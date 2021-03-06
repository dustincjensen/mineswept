package ui.menu;

import events.AboutEvent;
import events.IEventPublisher;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Sets up the help menu.
 */
@SuppressWarnings("serial")
public class HelpMenu extends JMenu {
	private IEventPublisher eventPublisher;

	public HelpMenu(IEventPublisher publisher) {
		super("Help");
		eventPublisher = publisher;
		add(about());
	}

	/**
	 * Return a menu item that activates a dialog with information about the game.
	 * 
	 * @return the about menu item.
	 */
	private JMenuItem about() {
		var about = new JMenuItem("About");
		about.addActionListener(evt -> eventPublisher.publish(new AboutEvent()));
		return about;
	}
}