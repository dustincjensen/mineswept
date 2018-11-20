package gui.panel.header;

import gui.events.IEventPublisher;
import gui.events.PauseGameEvent;
import gui.FontChange;
import gui.Resource;
import gui.ResourceLoader;
import java.awt.*;
import javax.swing.*;
import logic.game.GameState;

/**
 * Renders the time count in the header.
 */
public class TimeCount extends JPanel {
	private GameState gameState;
	private IEventPublisher eventPublisher;
	private ResourceLoader resourceLoader;
	private JButton clockIcon;
	// TODO make non-static
	private static JLabel clockCount;

	public TimeCount(GameState state, IEventPublisher publisher, ResourceLoader loader) {
		gameState = state;
		eventPublisher = publisher;
		resourceLoader = loader;
		setLayout(new FlowLayout(FlowLayout.TRAILING));
		setupPanel();
	}

	// TODO make non-static
	public static void setClockCount(String time) {
		clockCount.setText(time);
	}

	private void setupPanel() {
		clockCount = new JLabel("000");
		FontChange.setFont(clockCount, 24);
		add(clockCount);

		clockIcon = new JButton(resourceLoader.get(Resource.Clock));
		clockIcon.setToolTipText("Pause or Continue");
		clockIcon.setBorderPainted(false);
		clockIcon.setContentAreaFilled(false);
		clockIcon.addActionListener(evt -> {
			eventPublisher.publish(new PauseGameEvent(!gameState.isGamePaused()));
		});
		add(clockIcon);
	}
}