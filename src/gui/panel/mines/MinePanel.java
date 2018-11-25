package gui.panel.mines;

import gui.ClassFactory;
import gui.panel.MainPanel;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import logic.game.GameState;

/**
 * Renders the the mine panel that allows the player to play the game.
 */
public class MinePanel extends JPanel {
	private static JPanel interiorMinePanel;
	private static GridLayout glo;
	private static Vector<MineButton> mineButtons;

	private static GameState gameState;
	// TODO clean this up
	private static MinePanel _self;

	public MinePanel(GameState state) {
		_self = this;

		gameState = state;

		setLayout(new FlowLayout());
		mineButtons = new Vector(state.getCurrentPuzzleMineCount());
		setupInteriorMinePanel();
		MineButton.init();
	}

	private void setupInteriorMinePanel() {
		int w = gameState.getCurrentPuzzleWidth();
		int h = gameState.getCurrentPuzzleHeight();
		interiorMinePanel = new JPanel();

		// GridLayout goes by row, column
		glo = new GridLayout(h, w);
		interiorMinePanel.setLayout(glo);

		addMines(h, w);
		add(interiorMinePanel);
	}

	public static void update() {
		for (int i = 0; i < mineButtons.size(); i++) {
			mineButtons.get(i).decorate();
		}
		_self.revalidate();
		_self.repaint();
	}

	public static void reset() {
		int w = gameState.getCurrentPuzzleWidth();
		int h = gameState.getCurrentPuzzleHeight();
		glo.setRows(h);
		glo.setColumns(w);
		addMines(h, w);
		_self.revalidate();
		_self.repaint();
		MineButton.reset();
	}

	private static void addMines(int h, int w) {
		interiorMinePanel.removeAll();
		mineButtons.clear();
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				// TODO this is temporary for now... Need to figure out how to create mines. Perhaps this IS the best way.
				MineButton mb = ClassFactory.create(MineButton.class);
				mb.setPosition(x, y);
				mineButtons.add(mb);
				interiorMinePanel.add(mb);
			}
		}
	}
}