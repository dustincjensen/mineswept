package ui.layout.header.timeCount;

import events.IEventPublisher;
import events.IEventSubscriber;
import events.PauseGameEvent;
import events.SetTimeCountEvent;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Renders the time count in the header.
 */
@SuppressWarnings("serial")
public class TimeCount extends JPanel {
	private IEventPublisher eventPublisher;
	private IEventSubscriber eventSubscriber;
	private TimeCountButton timeCountButton;

	public TimeCount(
		ImageIcon clockIcon,
		IEventPublisher eventPublisher,
		IEventSubscriber eventSubscriber
	) {
		this.eventPublisher = eventPublisher;
		this.eventSubscriber = eventSubscriber;
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setOpaque(false);
		setupPanel(clockIcon);
		setupSubscriptions();
	}

	private void setupPanel(ImageIcon clockIcon) {
		timeCountButton = new TimeCountButton(0, clockIcon, evt -> {
			eventPublisher.publish(new PauseGameEvent());
		});
		add(timeCountButton, BorderLayout.LINE_END);
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(SetTimeCountEvent.class, event -> {
			timeCountButton.setTime(event.time);
		});
	}
}