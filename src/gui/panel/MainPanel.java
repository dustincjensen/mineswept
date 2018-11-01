package gui.panel;

import javax.swing.*;
import java.awt.*;
import gui.panel.header.HeaderPanel;
import gui.panel.mines.MinePanel;
import gui.panel.mines.PausePanel;

/**
 * Sets up the JFrame content pane.
 */
public class MainPanel extends JPanel {
	private static HeaderPanel hp;
	private static MinePanel mp;
	private static PausePanel pp;

	public MainPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		hp = new HeaderPanel();
		mp = new MinePanel();
		pp = new PausePanel();
		add(hp);
		add(mp);
	}

	public static HeaderPanel getHeaderPanel() {
		return hp;
	}

	public static MinePanel getMinePanel() {
		return mp;
	}

	public static PausePanel getPausePanel() {
		return pp;
	}

	/**
	 * Remove or show the pause panel.
	 * 
	 * @param pause true if the game should be paused.
	 */
	public void pausePanel(boolean pause) {
		if (pause) {
			remove(mp);
			add(pp);
		} else {
			remove(pp);
			add(mp);
		}
		this.repaint();
		this.revalidate();
	}
}