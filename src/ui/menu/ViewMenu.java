package ui.menu;

import events.IEventPublisher;
import events.ShowStatisticsEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * Sets up the view menu
 */
@SuppressWarnings("serial")
public class ViewMenu extends JMenu {
	private IEventPublisher eventPublisher;

	public ViewMenu(IEventPublisher publisher) {
		super("View");
		eventPublisher = publisher;
		add(showStatistics());
	}

	/**
	 * Return a menu item that shows the statistics window.
	 * 
	 * @return the statistics menu item.
	 */
	private JMenuItem showStatistics() {
		var statistics = new JMenuItem("Statistics");
		statistics.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 2));
		statistics.addActionListener(evt -> eventPublisher.publish(new ShowStatisticsEvent()));
		return statistics;
	}
}