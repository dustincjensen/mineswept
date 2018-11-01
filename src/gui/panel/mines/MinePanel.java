package gui.panel.mines;

import logic.game.MineField;
import gui.panel.MainPanel;
import javax.swing.*;
import java.awt.*;

import java.util.Vector;

/**
*	MinePanel.java
*	sets up the mine panel
*	@author Dustin Jensen
*/
public class MinePanel extends JPanel {

	private static JPanel interiorMinePanel;
	private static GridLayout glo;
	private static Vector<MineButton> mineButtons;

	/**
	*	Constructor
	*/
	public MinePanel() {
		setLayout(new FlowLayout());
		mineButtons = new Vector(MineField.getNumMines());
		setupInteriorMinePanel();
		MineButton.init();
	}//End Constructor

	private void setupInteriorMinePanel() {
		int w = MineField.getWidth();
		int h = MineField.getHeight();
		interiorMinePanel = new JPanel();
		glo = new GridLayout(h,w);
		interiorMinePanel.setLayout(glo);
			//gridlayout goes by row, column

		addMines(h,w);
		add(interiorMinePanel);
	}//End setupInteriorMinePanel

	private static void addMines(int h, int w) {
		interiorMinePanel.removeAll();
		mineButtons.clear();
		for(int y=0; y<h; y++) {
			for(int x=0; x<w; x++) {
				MineButton mb = new MineButton();
				mb.setPosition(x,y);
				mineButtons.add(mb);
				interiorMinePanel.add(mb);
			}
		}
	}//End addMines

	public static void update() {
		for(int i=0; i<mineButtons.size(); i++) {
			mineButtons.get(i).decorate();
		}
		MainPanel.getMinePanel().revalidate();
		MainPanel.getMinePanel().repaint();
	}

	public static void reset() {
		int w = MineField.getWidth();
		int h = MineField.getHeight();
		glo.setRows(h);	glo.setColumns(w);
		addMines(h,w);
		MainPanel.getMinePanel().revalidate();
		MainPanel.getMinePanel().repaint();
		MineButton.reset();
	}

}//End class MinePanel