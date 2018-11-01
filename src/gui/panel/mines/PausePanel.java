package gui.panel.mines;

import gui.events.Pause;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Renders a pause screen and allows the user to restart the game.
 */
public class PausePanel extends JPanel implements ActionListener {
	private static JButton resetButton;

	public PausePanel() {
		setLayout(new FlowLayout());
		resetButton = new JButton("Continue Playing");
		resetButton.addActionListener(this);
		add(resetButton);
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == resetButton) {
			Pause.doEvent();
		}
	}
}