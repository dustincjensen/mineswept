package gui.panel.header;

import gui.events.Pause;
import gui.FontChange;
import gui.panel.MainPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Renders the time count in the header.
 */
public class TimeCount extends JPanel implements ActionListener {
	private static ImageIcon clockImage;
	private static JButton clockIcon;
	private static JLabel clockCount;

	public TimeCount() {
		setLayout(new FlowLayout(FlowLayout.TRAILING));
		loadImages();
		setupPanel();
	}

	private void loadImages() {
		try {
			clockImage = new ImageIcon(getClass().getResource("/icons/clock.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setupPanel() {
		clockIcon = new JButton(clockImage);
		clockIcon.setToolTipText("Pause or Continue");
		clockIcon.setBorderPainted(false);
		clockIcon.setContentAreaFilled(false);
		clockIcon.addActionListener(this);
		clockCount = new JLabel("000");
		FontChange.setFont(clockCount, 24);
		add(clockCount);
		add(clockIcon);
	}

	public static void setClockCount(String time) {
		clockCount.setText(time);
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == clockIcon) {
			Pause.doEvent();
		}
	}
}