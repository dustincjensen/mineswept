package gui.panel.header;

import javax.swing.*;
import java.awt.*;

/**
 * Renders the header for the application which includes things like the hints,
 * reset and timer.
 */
public class HeaderPanel extends JPanel {
	private static MineCount mineCount;
	private static ResetButton reset;
	private static TimeCount timer;

	public HeaderPanel() {
		setLayout(new GridLayout(0, 3));

		mineCount = new MineCount();
		reset = new ResetButton();
		timer = new TimeCount();

		add(mineCount);
		add(reset);
		add(timer);
	}
}