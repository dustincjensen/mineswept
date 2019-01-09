package ui.panel.header;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * Renders the header for the application which includes things like the hints,
 * reset and timer.
 */
public class HeaderPanel extends JPanel {
	public HeaderPanel(MineCount mineCount, ResetButton resetButton, TimeCount timeCount) {
		setLayout(new GridLayout(0, 3));
		add(mineCount);
		add(resetButton);
		add(timeCount);
	}
}