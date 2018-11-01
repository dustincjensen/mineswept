package gui.panel.mines;

import gui.events.Pause;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
*	PausePanel.java
*	sets up the pause panel
*	@author Dustin Jensen
*/
public class PausePanel extends JPanel implements ActionListener {

	private static JButton resetButton;

	/**
	*	Constructor
	*/
	public PausePanel() {
		setLayout(new FlowLayout());
		resetButton = new JButton("Continue Playing");
		resetButton.addActionListener(this);
		add(resetButton);
	}//End Constructor

	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == resetButton)
			Pause.doEvent();
	}

}//End class PausePanel