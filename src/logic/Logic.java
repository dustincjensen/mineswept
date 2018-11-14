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
	public static void init(Preferences prefs, Records records) {
		// TODO once we have dependency injection, this will all go away.
		GameFeatures.init();
		ClockTimer.init();
		RandomGen.init();
		MineField.init(prefs);
	}
}