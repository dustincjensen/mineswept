package ui.panel.mines;

import events.IEventPublisher;
import events.PauseGameEvent;
import ui.FontChange;
import ui.components.button.PrimaryButton;

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

	private JButton continueButton() {
		var button = new PrimaryButton("Continue Playing", evt -> eventPublisher.publish(new PauseGameEvent(false)));
		FontChange.setFont(button, 24);
		return button;
	}
}