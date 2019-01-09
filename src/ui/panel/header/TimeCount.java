package ui.panel.header;

import events.IEventPublisher;
import events.IEventSubscriber;
import events.PauseGameEvent;
import events.SetTimeCountEvent;
import ui.FontChange;
import ui.ResourceLoader;
import java.awt.*;
import javax.swing.*;
import models.Resource;
import state.GameState;

/**
 * Renders the time count in the header.
 */
public class TimeCount extends JPanel {
	private GameState gameState;
	private IEventPublisher eventPublisher;
	private IEventSubscriber eventSubscriber;
	private ResourceLoader resourceLoader;
	private JLabel clockCount;

	public TimeCount(
		GameState state, 
		IEventPublisher publisher,
		IEventSubscriber subscriber,
		ResourceLoader loader
	) {
		gameState = state;
		eventPublisher = publisher;
		eventSubscriber = subscriber;
		resourceLoader = loader;
		
		setLayout(new FlowLayout(FlowLayout.TRAILING));
		setupPanel();
		setupSubscriptions();
	}

	private void setupPanel() {
		clockCount = new JLabel("000");
		FontChange.setFont(clockCount, 24);
		add(clockCount);

		var clockIcon = new JButton(resourceLoader.get(Resource.Clock));
		clockIcon.setToolTipText("Pause or Continue");
		clockIcon.setBorderPainted(false);
		clockIcon.setContentAreaFilled(false);
		clockIcon.addActionListener(evt -> {
			eventPublisher.publish(new PauseGameEvent(!gameState.isGamePaused()));
		});
		add(clockIcon);
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(SetTimeCountEvent.class, (event) -> {
			clockCount.setText(event.time);
		});
	}
}