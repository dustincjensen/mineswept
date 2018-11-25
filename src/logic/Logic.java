package logic;

import gui.events.IEventPublisher;
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
	public static void init(GameState gameState, ClockTimer clockTimer, IEventPublisher publisher) {
		// TODO once we have dependency injection, this will all go away.
		MineField.init(gameState, clockTimer, publisher);
	}
}