package gui.events;

import gui.panel.mines.MinePanel;
import logic.game.MineField;
import logic.game.GameFeatures;

public class Hint {
	public static void init() {
	}

	public static void doEvent() {
		if (!GameFeatures.isGameOver()) {
			MineField.getHint();
			MinePanel.update();
		}
	}
}