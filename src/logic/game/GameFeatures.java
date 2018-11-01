package logic.game;

/**
 * The state of the game.
 */
public class GameFeatures {
	private static boolean gameStarted;
	private static boolean gameOver;

	/**
	 * Initialize the game state.
	 */
	public static void init() {
		gameStarted = false;
		gameOver = false;
	}

	/**
	 * Reset the game state.
	 */
	public static void reset() {
		init();
	}

	/**
	 * Returns if the game has been started.
	 * 
	 * @return true if the game has started, false otherwise.
	 */
	public static boolean isGameStarted() {
		return gameStarted;
	}

	/**
	 * Returns if the game has ended.
	 * 
	 * @return true if the game has ended, false otherwise.
	 */
	public static boolean isGameOver() {
		return gameOver;
	}

	/**
	 * Set the gameStarted state.
	 * 
	 * @param started true if the game has started.
	 */
	public static void setGameStarted(boolean started) {
		gameStarted = started;
	}

	/**
	 * Set the gameOver state.
	 * 
	 * @param over true if the player received game over.
	 */
	public static void setGameOver(boolean over) {
		gameOver = over;
	}
}