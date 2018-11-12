package gui.panel.header;

import gui.events.ResetGameEvent;
import gui.options.OptionWindow;
import gui.statistics.StatisticsWindow;
import gui.events.EventPublisher;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

/**
 * Renders the reset button in the header.
 */
public class ResetButton extends JPanel implements ActionListener {
	private static JButton smileButton;
	private static Map<SmileyEnum, ImageIcon> smileMap;

	public ResetButton() {
		setLayout(new FlowLayout());
		loadImages();
		setupPanel();
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
		}
	}

	private void setupPanel() {
		smileButton = new JButton(smileMap.get(SmileyEnum.happy));
		smileButton.setToolTipText("Reset the field!");
		smileButton.setBorderPainted(false);
		smileButton.setContentAreaFilled(false);
		smileButton.addActionListener(this);
		add(smileButton);
	}

	public static ImageIcon getSmileyHappy() {
		return smileMap.get(SmileyEnum.happy);
	}

	public static void setSmileyIcon(SmileyEnum smile) {
		smileButton.setIcon(smileMap.get(smile));
	}

	public static void reset() {
		smileButton.setIcon(smileMap.get(SmileyEnum.happy));
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == smileButton) {
			var pub = new EventPublisher(new OptionWindow(), new StatisticsWindow());
			pub.publish(new ResetGameEvent());
		}
	}
}