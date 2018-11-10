package gui.panel.mines;

import gui.events.IEventPublisher;
import gui.events.PauseGameEvent;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.google.inject.Inject;

/**
 * Renders a pause screen and allows the user to restart the game.
 */
public class PausePanel extends JPanel {
	@Inject
	private IEventPublisher service;

	public PausePanel() {
		setLayout(new FlowLayout());
		add(continueButton());
	}

	public JButton continueButton() {
		var button = new JButton("Continue Playing");
		button.addActionListener(evt -> service.publish(new PauseGameEvent(false)));
		return button;
	}
}