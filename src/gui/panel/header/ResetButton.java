package gui.panel.header;

import gui.ResourceLoader;
import gui.Resource;
import gui.events.IEventPublisher;
import gui.events.IEventSubscriber;
import gui.events.ResetGameEvent;
import gui.events.SetResetButtonIconEvent;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

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
		eventSubscriber.subscribe(ResetGameEvent.class, (event) -> {
			System.out.println("Handling ResetGameEvent in ResetButton.");
			smileButton.setIcon(resourceLoader.get(Resource.SmileyHappy));
		});

		eventSubscriber.subscribe(SetResetButtonIconEvent.class, (event) -> {
			System.out.println("Handling SetResetButtonIconEvent in ResetButton.");
			smileButton.setIcon(resourceLoader.get(event.resource));
		});
	}
}