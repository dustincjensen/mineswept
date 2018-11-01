package gui.menu;

import javax.swing.*;
import java.awt.*;

/**
*	MenuLine.java
*	sets up the menu bar for the JFrame
*	@author Dustin Jensen
*/
public class MenuLine extends JMenuBar {

	private static GameMenu gm;
	private static ViewMenu vm;
	private static HelpMenu hm;

	/**
	*	Constructor
	*	sets up the JMenuBar
	*/
	public MenuLine() {
		gm = new GameMenu();
		vm = new ViewMenu();
		hm = new HelpMenu();
		add(gm);
		add(vm);
		add(hm);
	}//End Constructor

}//End class MenuLine