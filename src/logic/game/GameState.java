package logic.game;

import java.util.Map;
import logic.files.Preferences;

/**
 * The state of the game.
 */
public class GameState {
	private boolean gameStarted;
	private boolean gameOver;
	private boolean gamePaused;
	private Difficulty currentPuzzleDifficulty;
	private Difficulty nextPuzzleDifficulty;
	private Map<Difficulty, Integer> puzzleWidths;
	private Map<Difficulty, Integer> puzzleHeights;
	private Map<Difficulty, Integer> puzzleMines;

	public GameState(Preferences prefs) {
		System.out.println("Creating new game state");
		gameStarted = false;
		gameOver = false;
		gamePaused = false;
		currentPuzzleDifficulty = nextPuzzleDifficulty = prefs.difficulty();
		puzzleWidths = Map.of(
			Difficulty.easy, 9,
			Difficulty.intermediate, 16,
			Difficulty.advanced, 30
		);
		puzzleHeights = Map.of(
			Difficulty.easy, 9,
			Difficulty.intermediate, 16,
			Difficulty.advanced, 16
		);
		puzzleMines = Map.of(
			Difficulty.easy, 10,
			Difficulty.intermediate, 40,
			Difficulty.advanced, 99
		);
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

	/**
	 * Get the current puzzle height.
	 * 
	 * @return the height of the current puzzle.
	 */
	public int getCurrentPuzzleHeight() {
		return puzzleHeights.get(getCurrentPuzzleDifficulty());
	}

	/**
	 * Get the current puzzle width.
	 * 
	 * @return the width of the current puzzle.
	 */
	public int getCurrentPuzzleWidth() {
		return puzzleWidths.get(getCurrentPuzzleDifficulty());
	}

	/**
	 * Get the current puzzle mine count.
	 * 
	 * @return the number of mines in the current puzzle.
	 */
	public int getCurrentPuzzleMineCount() {
		return puzzleMines.get(getCurrentPuzzleDifficulty());
	}

	/**
	 * Get the maximum number of mine field squares.
	 * 
	 * @return the maximum number of mine field squares.
	 */
	public int getMaxNumberOfMineFieldSquares() {
		return puzzleWidths.get(Difficulty.advanced) * puzzleHeights.get(Difficulty.advanced);
	}
}