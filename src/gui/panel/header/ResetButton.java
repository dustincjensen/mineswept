package gui.panel.header;

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
	private static JButton smileButton;
	// TODO make non-static
	private static Map<SmileyEnum, ImageIcon> smileMap;

	public ResetButton(IEventPublisher publisher) {
		eventPublisher = publisher;
		setLayout(new FlowLayout());
		setupPanel();
	}

	// TODO make non-static
	public static ImageIcon getSmileyHappy() {
		return smileMap.get(SmileyEnum.happy);
	}

	// TODO make non-static
	public static void setSmileyIcon(SmileyEnum smile) {
		smileButton.setIcon(smileMap.get(smile));
	}

	// TODO make non-static
	public static void reset() {
		smileButton.setIcon(smileMap.get(SmileyEnum.happy));
	}

	private void setupPanel() {
		loadImages();

		smileButton = new JButton(smileMap.get(SmileyEnum.happy));
		smileButton.setToolTipText("Reset the field!");
		smileButton.setBorderPainted(false);
		smileButton.setContentAreaFilled(false);
		smileButton.addActionListener(evt -> {
			eventPublisher.publish(new ResetGameEvent());
		});
		add(smileButton);
	}

	private void loadImages() {
		try {
			smileMap = new HashMap<SmileyEnum, ImageIcon>();
			smileMap.put(SmileyEnum.happy, new ImageIcon(getClass().getResource("/icons/smiley-happy.png")));
			smileMap.put(SmileyEnum.sad, new ImageIcon(getClass().getResource("/icons/smiley-sad.png")));
			smileMap.put(SmileyEnum.surprised, new ImageIcon(getClass().getResource("/icons/smiley-surprised.png")));
			smileMap.put(SmileyEnum.cool, new ImageIcon(getClass().getResource("/icons/smiley-cool.png")));
			smileMap.put(SmileyEnum.record, new ImageIcon(getClass().getResource("/icons/smiley-record.png")));
			smileMap.put(SmileyEnum.paused, new ImageIcon(getClass().getResource("/icons/smiley-paused.png")));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}