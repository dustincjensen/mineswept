package logic;

import logic.game.*;
import logic.util.*;
import logic.files.*;

/**
 * Houses all the logic initialization.
 */
public class Logic {
	/**
	 * Initialize the logic for the game.
	 */
	public static void init(Preferences prefs, Records records, GameState gameState) {
		// TODO once we have dependency injection, this will all go away.
		ClockTimer.init();
		MineField.init(prefs, gameState);
	}
}