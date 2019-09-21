package ui.layout.header;

import events.IEventPublisher;
import events.IEventSubscriber;
import events.PauseGameEvent;
import events.SetTimeCountEvent;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import state.GameState;
import ui.utils.FontChange;
import ui.utils.HexToRgb;

/**
 * Renders the time count in the header.
 */
public class TimeCount extends JPanel {
	private GameState gameState;
	private IEventPublisher eventPublisher;
	private IEventSubscriber eventSubscriber;
	private ImageIcon clockIcon;
	private JLabel clockCount;

	public TimeCount(
		GameState gameState,
		IEventPublisher eventPublisher,
		IEventSubscriber eventSubscriber,
		ImageIcon clockIcon
	) {
		this.gameState = gameState;
		this.eventPublisher = eventPublisher;
		this.eventSubscriber = eventSubscriber;
		this.clockIcon = clockIcon;
		
		setLayout(new FlowLayout(FlowLayout.TRAILING));
		setBackground(null);
		setupPanel();
		setupSubscriptions();
	}

	private void setupPanel() {
		clockCount = new JLabel("000");
		clockCount.setForeground(HexToRgb.convert("#ffffff"));
		FontChange.setFont(clockCount, 24);
		add(clockCount);

		var button = new JButton(clockIcon);
		button.setToolTipText("Pause or Continue");
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.addActionListener(evt -> {
			eventPublisher.publish(new PauseGameEvent(!gameState.isGamePaused()));
		});
		add(button);
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(SetTimeCountEvent.class, (event) -> {
			var formattedTime = String.format("%03d", event.time);
			clockCount.setText(formattedTime);
		});
	}
}