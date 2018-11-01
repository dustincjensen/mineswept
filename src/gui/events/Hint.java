package gui.events;

import logic.game.MineField;
import logic.game.GameFeatures;
import gui.panel.mines.MinePanel;

public class Hint {
	public static void init() {}
	public static void doEvent() {
		if(!GameFeatures.isGameOver()) {
			MineField.getHint();
			MinePanel.update();
		}	
	}
}