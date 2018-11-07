package gui.panel.mines;

import gui.events.EventPublisher;
import gui.events.PauseGameEvent;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Renders a pause screen and allows the user to restart the game.
 */
public class PausePanel extends JPanel {
	private EventPublisher service;

	public PausePanel() {
		setLayout(new FlowLayout());

		// TODO replace with dependency injection.
		// Create an event publisher for the menu items to use.
		service = new EventPublisher();

		add(continueButton());
	}

	public JButton continueButton() {
		var button = new JButton("Continue Playing");
		button.addActionListener(evt -> service.publish(new PauseGameEvent(false)));
		return button;
	}
}