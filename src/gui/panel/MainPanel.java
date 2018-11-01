package gui.panel;

import gui.panel.header.HeaderPanel;
import gui.panel.mines.MinePanel;
import gui.panel.mines.PausePanel;

import javax.swing.*;
import java.awt.*;

/**
*	MainPanel.java
*	sets up the JFrame content pane
*	@author Dustin Jensen
*/
public class MainPanel extends JPanel {

	private static HeaderPanel hp;
	private static MinePanel mp;
	private static PausePanel pp;

	/**
	*	Constructor
	*/
	public MainPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		hp = new HeaderPanel();
		mp = new MinePanel();
		pp = new PausePanel();
		add(hp); add(mp);
	}//End Constructor

	/**
	*	getHeaderPanel
	*	@return hp the header panel reference
	*/
	public static HeaderPanel getHeaderPanel() {
		return hp;
	}//End getHeaderPanel

	/**
	*	getMinePanel
	*	@return mp the mine panel reference
	*/
	public static MinePanel getMinePanel() {
		return mp;
	}//End getMinePanel

	/**
	*	getPausePanel
	*	@return pp the pause panel reference
	*/
	public static PausePanel getPausePanel() {
		return pp;
	}//End getPausePanel

	/**
	*
	*/
	public void pausePanel(boolean load) {
		if(load) {
			remove(mp);
			add(pp);
		} else {
			remove(pp);
			add(mp);
		}
		this.repaint();
		this.revalidate();
	}

}//End class MainPanel