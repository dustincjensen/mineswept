package gui.panel.header;

import gui.ResourceLoader;
import gui.Resource;
import gui.events.IEventPublisher;
import gui.events.ResetGameEvent;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Renders the reset button in the header.
 */
public class ResetButton extends JPanel {
	private IEventPublisher eventPublisher;
	// TODO make non-static
	private static ResourceLoader resourceLoader;

	// TODO make non-static
	private static JButton smileButton;

	public ResetButton(IEventPublisher publisher, ResourceLoader loader) {
		eventPublisher = publisher;
		resourceLoader = loader;
		setLayout(new FlowLayout());
		setupPanel();
	}

	// TODO remove this
	public static ImageIcon getSmileyHappy() {
		return resourceLoader.get(Resource.SmileyHappy);
	}

	// TODO make non-static
	public static void setSmileyIcon(Resource resource) {
		smileButton.setIcon(resourceLoader.get(resource));
	}

	// TODO make non-static
	public static void reset() {
		smileButton.setIcon(getSmileyHappy());
	}

	private void setupPanel() {
		smileButton = new JButton(getSmileyHappy());
		smileButton.setToolTipText("Reset the field!");
		smileButton.setBorderPainted(false);
		smileButton.setContentAreaFilled(false);
		smileButton.addActionListener(evt -> {
			eventPublisher.publish(new ResetGameEvent());
		});
		add(smileButton);
	}
}