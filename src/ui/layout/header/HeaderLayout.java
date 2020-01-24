package ui.layout.header;

import java.awt.GridLayout;
import javax.swing.JPanel;
import ui.utils.HexToRgb;

/**
 * Renders the header for the application which includes things like the hints,
 * reset and timer.
 */
@SuppressWarnings("serial")
public class HeaderLayout extends JPanel {
	public HeaderLayout(MineCount mineCount, ResetButton resetButton, TimeCount timeCount) {
		setLayout(new GridLayout(0, 3));
		add(mineCount);
		add(resetButton);
		add(timeCount);

		setBackground(HexToRgb.convert("#333333"));
	}
}