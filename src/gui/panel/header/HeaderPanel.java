package gui.panel.header;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * Renders the header for the application which includes things like the hints,
 * reset and timer.
 */
public class HeaderPanel extends JPanel {
	public HeaderPanel() {
		setLayout(new GridLayout(0, 3));
		add(new MineCount());
		add(new ResetButton());
		add(new TimeCount());
	}
}