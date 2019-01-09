package ui.panel.header;

import events.IEventPublisher;
import events.IEventSubscriber;
import events.ResetGameEvent;
import events.SetResetButtonIconEvent;
import ui.ResourceLoader;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import models.Resource;

/**
 * Renders the reset button in the header.
 */
public class ResetButton extends JPanel {
	private IEventPublisher eventPublisher;
	private IEventSubscriber eventSubscriber;
	private ResourceLoader resourceLoader;
	private JButton smileButton;

	public ResetButton(
		IEventPublisher publisher,
		IEventSubscriber subscriber,
		ResourceLoader loader
	) {
		eventPublisher = publisher;
		eventSubscriber = subscriber;
		resourceLoader = loader;
		
		setLayout(new FlowLayout());
		setupPanel();
		setupSubscriptions();
	}

	private void setupPanel() {
		smileButton = new JButton(resourceLoader.get(Resource.SmileyHappy));
		smileButton.setToolTipText("Reset the field!");
		smileButton.setBorderPainted(false);
		smileButton.setContentAreaFilled(false);
		smileButton.addActionListener(evt -> {
			eventPublisher.publish(new ResetGameEvent());
		});
		add(smileButton);
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(SetResetButtonIconEvent.class, (event) -> {
			smileButton.setIcon(resourceLoader.get(event.resource));
		});
	}
}