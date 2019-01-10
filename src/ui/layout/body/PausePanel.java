package ui.layout.body;

import events.IEventPublisher;
import events.PauseGameEvent;
import javax.swing.JPanel;
import ui.components.button.PrimaryButton;
import ui.FontChange;

/**
 * Renders a pause screen and allows the user to restart the game.
 */
public class PausePanel extends JPanel {
	private IEventPublisher eventPublisher;

	public PausePanel(IEventPublisher publisher) {
		eventPublisher = publisher;
		add(continueButton());
	}

	private PrimaryButton continueButton() {
		var button = new PrimaryButton("Continue Playing", evt -> eventPublisher.publish(new PauseGameEvent(false)));
		FontChange.setFont(button, 24);
		return button;
	}
}