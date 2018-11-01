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
	public static void init() {
		Statistics.init();
		// Preferences need to be loaded before initializing the MineField
		Preferences.init();
		Records.init();
		FileManagement.init();
		
		GameFeatures.init();
		ClockTimer.init();
		RandomGen.init();
		MineField.init();
	}
}