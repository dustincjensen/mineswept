package gui.panel.header;

import javax.swing.*;
import java.awt.*;

/**
*	HeaderPanel.java
*	sets up the header panel
*	@author Dustin Jensen
*/
public class HeaderPanel extends JPanel {

	private static MineCount mc;
	private static ResetButton rb;
	private static TimeCount tc;

	/**
	*	Constructor
	*/
	public HeaderPanel() {
		setLayout(new GridLayout(0,3));
		
		mc = new MineCount();
		rb = new ResetButton();
		tc = new TimeCount();

		add(mc); add(rb); add(tc);
	}//End Constructor

}//End class HeaderPanel