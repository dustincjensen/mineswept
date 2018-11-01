package logic;

import logic.game.*;
import logic.util.*;
import logic.files.*;

/**
*	Logic.java
*	has all the logic components
*	@author Dustin Jensen
*/
public class Logic {

	public static void init() {
		Statistics.init();
		Preferences.init();
		Records.init();
		FileManagement.init();

		/* Preferences need to be loaded before initializing
		the MineField */
		
		GameFeatures.init();
		ClockTimer.init();
		RandomGen.init();
		MineField.init();
	}

}//End class Logic