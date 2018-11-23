package logic.game;

/**
 * The state of the game.
 */
public class GameState {
	private boolean gameStarted;
	private boolean gameOver;
	private boolean gamePaused;
	private Difficulty currentPuzzleDifficulty;
	private Difficulty nextPuzzleDifficulty;

	public GameState() {
		System.out.println("Creating new game state");
		gameStarted = false;
		gameOver = false;
		gamePaused = false;
		currentPuzzleDifficulty = nextPuzzleDifficulty = Difficulty.easy;
	}

	/**
	 * Reset the game state.
	 */
	public void reset() {
		gameStarted = false;
		gameOver = false;
		gamePaused = false;
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
	 * Set the gameStarted state.
	 * 
	 * @param started true if the game has started.
	 */
	public void setGameStarted(boolean started) {
		gameStarted = started;
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
	 * Set the gameOver state.
	 * 
	 * @param over true if the player received game over.
	 */
	public void setGameOver(boolean over) {
		gameOver = over;
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
	 * Set the gamePaused state.
	 * 
	 * @param paused true if the game should be paused.
	 */
	public void setGamePaused(boolean paused) {
		gamePaused = paused;
	}

	/**
	 * Returns the current puzzle difficulty.
	 * 
	 * @return the difficulty of the current puzzle.
	 */
	public Difficulty getCurrentPuzzleDifficulty() {
		return currentPuzzleDifficulty;
	}

	/**
	 * Set the current puzzle difficulty.
	 * 
	 * @param difficulty the difficulty of the current puzzle.
	 */
	public void setCurrentPuzzleDifficulty(Difficulty difficulty) {
		currentPuzzleDifficulty = difficulty;
	}

	/**
	 * Update the current puzzle difficulty to be the next puzzle difficulty.
	 */
	public void setCurrentPuzzleToNextPuzzle() {
		currentPuzzleDifficulty = nextPuzzleDifficulty;
	}

	/**
	 * Returns the next puzzle difficulty.
	 * 
	 * @return the difficulty of the next puzzle.
	 */
	public Difficulty getNextPuzzleDifficulty() {
		return nextPuzzleDifficulty;
	}

	/**
	 * Set the next puzzle difficulty.
	 * 
	 * @param difficulty the difficulty of the next puzzle.
	 */
	public void setNextPuzzleDifficulty(Difficulty difficulty) {
		nextPuzzleDifficulty = difficulty;
	}
}