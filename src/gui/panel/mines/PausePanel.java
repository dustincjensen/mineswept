package gui.panel.mines;

import gui.events.IEventPublisher;
import gui.events.PauseGameEvent;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Renders a pause screen and allows the user to restart the game.
 */
public class PausePanel extends JPanel {
	private IEventPublisher eventPublisher;

	public PausePanel(IEventPublisher publisher) {
		eventPublisher = publisher;

		setLayout(new FlowLayout());
		add(continueButton());
	}

	public JButton continueButton() {
		var button = new JButton("Continue Playing");
		button.addActionListener(evt -> eventPublisher.publish(new PauseGameEvent(false)));
		return button;
	}
}