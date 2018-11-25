package gui.panel;

import javax.swing.*;
import java.awt.*;

import gui.ClassFactory;
import gui.panel.header.HeaderPanel;
import gui.panel.mines.MinePanel;
import gui.panel.mines.PausePanel;

/**
 * Sets up the JFrame content pane.
 */
public class MainPanel extends JPanel {
	private static MinePanel minePanel;
	private PausePanel pausePanel;

	public MainPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// TODO this should be provided to the main panel...
		minePanel = ClassFactory.create(MinePanel.class);
		// TODO this should be provided to the main panel...
		pausePanel = ClassFactory.create(PausePanel.class);

		// TODO this should be provided to the main panel...
		add(ClassFactory.create(HeaderPanel.class));
		add(minePanel);
	}

	public static MinePanel getMinePanel() {
		return minePanel;
	}

	/**
	 * Remove or show the pause panel.
	 * 
	 * @param pause true if the game should be paused.
	 */
	public void pausePanel(boolean pause) {
		if (pause) {
			remove(minePanel);
			add(pausePanel);
		} else {
			remove(pausePanel);
			add(minePanel);
		}
		repaint();
		revalidate();
	}
}