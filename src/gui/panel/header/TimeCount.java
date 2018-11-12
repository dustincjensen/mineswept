package gui.panel.header;

import gui.events.IEventPublisher;
import gui.events.PauseGameEvent;
import gui.FontChange;
import java.awt.*;
import javax.swing.*;
import logic.game.GameFeatures;

/**
 * Renders the time count in the header.
 */
public class TimeCount extends JPanel {
	private IEventPublisher eventPublisher;
	private ImageIcon clockImage;
	private JButton clockIcon;
	// TODO make non-static
	private static JLabel clockCount;

	public TimeCount(IEventPublisher publisher) {
		eventPublisher = publisher;
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

		clockIcon = new JButton(loadImage());
		clockIcon.setToolTipText("Pause or Continue");
		clockIcon.setBorderPainted(false);
		clockIcon.setContentAreaFilled(false);
		clockIcon.addActionListener(evt -> {
			eventPublisher.publish(new PauseGameEvent(!GameFeatures.isGamePaused()));
		});
		add(clockIcon);
	}

	private ImageIcon loadImage() {
		try {
			return new ImageIcon(getClass().getResource("/icons/clock.png"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}