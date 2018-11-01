package gui.panel.header;

import gui.events.Reset;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Renders the reset button in the header.
 */
public class ResetButton extends JPanel implements ActionListener {
	private static ImageIcon smileyHappy;
	private static ImageIcon smileySad;
	private static ImageIcon smileySurprised;
	private static ImageIcon smileyCool;
	private static ImageIcon smileyRecord;
	private static ImageIcon smileyPaused;
	private static JButton smileButton;

	public ResetButton() {
		setLayout(new FlowLayout());
		loadImages();
		setupPanel();
	}

	private void loadImages() {
		try {
			smileyHappy = new ImageIcon(getClass().getResource("/icons/smiley-happy.png"));
			smileySad = new ImageIcon(getClass().getResource("/icons/smiley-sad.png"));
			smileySurprised = new ImageIcon(getClass().getResource("/icons/smiley-surprised.png"));
			smileyCool = new ImageIcon(getClass().getResource("/icons/smiley-cool.png"));
			smileyRecord = new ImageIcon(getClass().getResource("/icons/smiley-record.png"));
			smileyPaused = new ImageIcon(getClass().getResource("/icons/smiley-paused.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setupPanel() {
		smileButton = new JButton(smileyHappy);
		smileButton.setToolTipText("Reset the field!");
		smileButton.setBorderPainted(false);
		smileButton.setContentAreaFilled(false);
		smileButton.addActionListener(this);
		add(smileButton);
	}

	public static ImageIcon getSmileyHappy() {
		return smileyHappy;
	}

	public static void setSmileyIcon(int which) {
		if (which == 1)
			smileButton.setIcon(smileyHappy);
		else if (which == 2)
			smileButton.setIcon(smileySad);
		else if (which == 3)
			smileButton.setIcon(smileySurprised);
		else if (which == 4)
			smileButton.setIcon(smileyCool);
		else if (which == 5)
			smileButton.setIcon(smileyRecord);
		else if (which == 6)
			smileButton.setIcon(smileyPaused);
	}

	public static void reset() {
		smileButton.setIcon(smileyHappy);
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == smileButton) {
			Reset.doEvent();
			reset();
		}
	}
}