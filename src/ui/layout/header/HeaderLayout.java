package ui.layout.header;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import ui.layout.header.mineCount.MineCount;
import ui.layout.header.timeCount.TimeCount;
import java.awt.Color;

/**
 * Renders the header for the application which includes things like the hints,
 * reset and timer.
 */
@SuppressWarnings("serial")
public class HeaderLayout extends JPanel {
	public HeaderLayout(MineCount mineCount, ResetButton resetButton, TimeCount timeCount) {
		setLayout(new BorderLayout());
		add(mineCount, BorderLayout.LINE_START);
		add(resetButton, BorderLayout.CENTER);
		add(timeCount, BorderLayout.LINE_END);

		setBackground(Color.decode("#333333"));
	}
}
