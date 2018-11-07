package gui.menu;

import gui.events.*;
import gui.events.EventPublisher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * Sets up the view menu
 */
public class ViewMenu extends JMenu implements ActionListener {
	private static JMenuItem records;
	private static JMenuItem statistics;

	public ViewMenu() {
		super("View");

		// Initialize menu items
		records = new JMenuItem("Records");
		statistics = new JMenuItem("Statistics");

		// Set mnemonics
		records.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 2));
		statistics.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 2));

		// Add actionlisteners
		records.addActionListener(this);
		statistics.addActionListener(this);

		// Add to view menu
		add(records);
		add(statistics);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		var pub = new EventPublisher();

		if (evt.getSource() == records) {
			pub.publish(new ShowRecordsEvent(true));
		} else if (evt.getSource() == statistics) {
			pub.publish(new ShowStatisticsEvent(true));
		}
	}
}