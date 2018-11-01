package gui.menu;

import gui.events.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
*	ViewMenu.java
*	sets up the view menu
*	@author Dustin Jensen
*/
public class ViewMenu extends JMenu implements ActionListener {

	private static JMenuItem records;
	private static JMenuItem statistics;

	/**
	*	Constructor
	*	sets up the JMenu
	*/
	public ViewMenu() {
		super("View");

		//initialize menu items
		records = new JMenuItem("Records");
		statistics = new JMenuItem("Statistics");

		//set mnemonics
		records.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 2));
		statistics.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 2));

		//add actionlisteners
		records.addActionListener(this);
		statistics.addActionListener(this);

		//add to view menu
		add(records);
		add(statistics);
	}//End Constructor

	/**
	*
	*/
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == records)
			RecordEvent.doEvent();
		else if(evt.getSource() == statistics)
			StatisticsEvent.doEvent();
	}

}//End class ViewMenu