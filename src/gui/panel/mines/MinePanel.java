package gui.panel.mines;

import gui.ClassFactory;
import gui.events.IEventSubscriber;
import gui.events.UpdateMinePanelEvent;
import gui.events.ResetMinePanelEvent;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import logic.game.GameState;

/**
 * Renders the the mine panel that allows the player to play the game.
 */
public class MinePanel extends JPanel {
	private GameState gameState;
	private IEventSubscriber eventSubscriber;
	private JPanel minePanel;
	private GridLayout minePanelLayout;
	private Vector<MineButton> mineButtons;
	
	public MinePanel(
		// TODO could we remove game state by providing width and height to constructor?
		// Could we also provide it on the reset event?
		GameState state,
		IEventSubscriber subscriber
	) {
		gameState = state;
		eventSubscriber = subscriber;

		setLayout(new FlowLayout());
		mineButtons = new Vector(state.getCurrentPuzzleMineCount());
		setupInteriorMinePanel();
		MineButton.init();

		setupSubscriptions();
	}

	private void setupInteriorMinePanel() {
		int w = gameState.getCurrentPuzzleWidth();
		int h = gameState.getCurrentPuzzleHeight();
		minePanel = new JPanel();

		// GridLayout goes by row, column
		minePanelLayout = new GridLayout(h, w);
		minePanel.setLayout(minePanelLayout);

		addMines(h, w);
		add(minePanel);
	}

	private void addMines(int h, int w) {
		minePanel.removeAll();
		mineButtons.clear();
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				// TODO this is temporary for now... Need to figure out how to create mines. Perhaps this IS the best way.
				MineButton mb = ClassFactory.create(MineButton.class);
				mb.setPosition(x, y);
				mineButtons.add(mb);
				minePanel.add(mb);
			}
		}
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(UpdateMinePanelEvent.class, (event) -> {
			for (int i = 0; i < mineButtons.size(); i++) {
				mineButtons.get(i).decorate();
			}
			revalidate();
			repaint();
		});

		eventSubscriber.subscribe(ResetMinePanelEvent.class, (event) -> {
			int w = gameState.getCurrentPuzzleWidth();
			int h = gameState.getCurrentPuzzleHeight();

			minePanelLayout.setRows(h);
			minePanelLayout.setColumns(w);
			addMines(h, w);
			
			revalidate();
			repaint();

			// TODO should this be moved?
			MineButton.reset();
		});
	}
}