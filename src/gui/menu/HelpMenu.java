package gui.menu;

import gui.events.About;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
*	HelpMenu.java
*	sets up the help menu
*	@author Dustin Jensen
*/
public class HelpMenu extends JMenu implements ActionListener {

	private static JMenuItem about;

	/**
	*	Constructor
	*	sets up the JMenu
	*/
	public HelpMenu() {
		super("Help");
		about = new JMenuItem("About");
		about.addActionListener(this);
		add(about);
	}//End Constructor

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == about)
			About.doEvent();
	}//End actionPerformed

}//End class HelpMenu