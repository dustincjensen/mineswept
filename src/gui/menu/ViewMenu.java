package gui.menu;

import gui.events.EventPublisher;
import gui.events.ShowRecordsEvent;
import gui.events.ShowStatisticsEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * Sets up the view menu
 */
public class ViewMenu extends JMenu {
	private EventPublisher service;

	public ViewMenu() {
		super("View");

		// TODO replace with dependency injection.
		// Create an event publisher for the menu items to use.
		service = new EventPublisher();

		add(showRecords());
		add(showStatistics());
	}

	/**
	 * Return a menu item that shows the records window.
	 * 
	 * @return the records menu item.
	 */
	public JMenuItem showRecords() {
		var records = new JMenuItem("Records");
		records.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 2));
		records.addActionListener(evt -> service.publish(new ShowRecordsEvent(true)));
		return records;
	}

	/**
	 * Return a menu item that shows the statistics window.
	 * 
	 * @return the statistics menu item.
	 */
	public JMenuItem showStatistics() {
		var statistics = new JMenuItem("Statistics");
		statistics.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 2));
		statistics.addActionListener(evt -> service.publish(new ShowStatisticsEvent(true)));
		return statistics;
	}
}