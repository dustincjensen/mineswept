package gui.panel.mines;

import gui.events.EventPublisher;
import gui.events.PauseGameEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Renders a pause screen and allows the user to restart the game.
 */
public class PausePanel extends JPanel implements ActionListener {
	private static JButton continueButton;

	public PausePanel() {
		setLayout(new FlowLayout());
		continueButton = new JButton("Continue Playing");
		continueButton.addActionListener(this);
		add(continueButton);
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == continueButton) {
			var pub = new EventPublisher();
			pub.publish(new PauseGameEvent(false));
		}
	}
}