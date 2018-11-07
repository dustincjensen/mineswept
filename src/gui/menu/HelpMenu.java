package gui.menu;

import gui.events.AboutEvent;
import gui.events.EventPublisher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * Sets up the help menu.
 */
public class HelpMenu extends JMenu implements ActionListener {
	private static JMenuItem about;

	public HelpMenu() {
		super("Help");
		about = new JMenuItem("About");
		about.addActionListener(this);
		add(about);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == about) {
			var pub = new EventPublisher();
			pub.publish(new AboutEvent());
		}
	}
}