package gui.panel.header;

import gui.ResourceLoader;
import gui.Resource;
import gui.events.IEventPublisher;
import gui.events.ResetGameEvent;
import java.awt.*;
import javax.swing.*;

/**
 * Renders the reset button in the header.
 */
public class ResetButton extends JPanel {
	private IEventPublisher eventPublisher;
	private ResourceLoader resourceLoader;
	private JButton smileButton;

	public ResetButton(IEventPublisher publisher, ResourceLoader loader) {
		eventPublisher = publisher;
		resourceLoader = loader;
		setLayout(new FlowLayout());
		setupPanel();
	}

	public void setSmileyIcon(Resource resource) {
		smileButton.setIcon(resourceLoader.get(resource));
	}

	public void reset() {
		smileButton.setIcon(resourceLoader.get(Resource.SmileyHappy));
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
}