package logic.game;

/**
 * The state of the game.
 */
public class GameState {
	private boolean gameStarted;
	private boolean gameOver;
	private boolean gamePaused;

	public GameState() {
		System.out.println("Creating new game state");
		init();
	}

	/**
	 * Initialize the game state.
	 */
	public void init() {
		gameStarted = false;
		gameOver = false;
		gamePaused = false;
	}

	/**
	 * Reset the game state.
	 */
	public void reset() {
		init();
	}

	/**
	 * Returns if the game has been started.
	 * 
	 * @return true if the game has started, false otherwise.
	 */
	public boolean isGameStarted() {
		return gameStarted;
	}

	/**
	 * Returns if the game has ended.
	 * 
	 * @return true if the game has ended, false otherwise.
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * Returns if the game is paused.
	 * 
	 * @return true if the game is paused, false otherwise;
	 */
	public boolean isGamePaused() {
		return gamePaused;
	}

	/**
	 * Set the gameStarted state.
	 * 
	 * @param started true if the game has started.
	 */
	public void setGameStarted(boolean started) {
		gameStarted = started;
	}

	/**
	 * Set the gameOver state.
	 * 
	 * @param over true if the player received game over.
	 */
	public void setGameOver(boolean over) {
		gameOver = over;
	}

	/**
	 * Set the gamePaused state.
	 * 
	 * @param paused true if the game should be paused.
	 */
	public void setGamePaused(boolean paused) {
		gamePaused = paused;
	}
}