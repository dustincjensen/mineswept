package state;

import factories.MinesFactory;
import models.Difficulty;
import models.Mine;
import models.Mines;
import java.util.Map;
import services.PreferencesService;

// TODO clock timer's time part of game state?

/**
 * The state of the game.
 */
public class GameState {
	private MinesFactory minesFactory;

	private boolean gameStarted;
	private boolean gameOver;
	private boolean gamePaused;
	private Difficulty currentPuzzleDifficulty;
	private Difficulty nextPuzzleDifficulty;
	private Map<Difficulty, Integer> puzzleWidths;
	private Map<Difficulty, Integer> puzzleHeights;
	private Map<Difficulty, Integer> puzzleMines;
	private Mines gameMines;

	public GameState(PreferencesService prefs, MinesFactory mines) {
		minesFactory = mines;

		System.out.println("Creating new game state");
		gameStarted = false;
		gameOver = false;
		gamePaused = false;
		currentPuzzleDifficulty = nextPuzzleDifficulty = prefs.difficulty();
		puzzleWidths = Map.of(
			Difficulty.easy, 9,
			Difficulty.medium, 16,
			Difficulty.hard, 30
		);
		puzzleHeights = Map.of(
			Difficulty.easy, 9,
			Difficulty.medium, 16,
			Difficulty.hard, 16
		);
		puzzleMines = Map.of(
			Difficulty.easy, 10,
			Difficulty.medium, 40,
			Difficulty.hard, 99
		);

		gameMines = minesFactory.create(
			getCurrentPuzzleWidth(),
			getCurrentPuzzleHeight(),
			getCurrentPuzzleMineCount()
		);
	}

	/**
	 * Reset the game state.
	 */
	public void reset() {
		gameStarted = false;
		gameOver = false;
		gamePaused = false;

		setCurrentPuzzleToNextPuzzle();
		resetMines();
	}

	private void resetMines() {
		gameMines = minesFactory.create(
			getCurrentPuzzleWidth(),
			getCurrentPuzzleHeight(),
			getCurrentPuzzleMineCount()
		);
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
		return puzzleWidths.get(Difficulty.hard) * puzzleHeights.get(Difficulty.hard);
	}

	/**
	 * Get the field of mines.
	 * 
	 * @return the field of mines.
	 */
	public Mines getMines() {
		return gameMines;
	}

	/**
	 * Get the mine at x, y.
	 * 
	 * @param x the x coordinate of the mine.
	 * @param y the y coordinate of the mine.
	 * @return the mine at x, y.
	 */
	public Mine getMine(int x, int y) {
		return gameMines.get(x, y);
	}

	/**
	 * Get the number of mines left.
	 * 
	 * @return the number of mines in the puzzle subtract the number of protected flags used.
	 */
	public int getMineCount() {
		int puzzleMineCount = getCurrentPuzzleMineCount();
		long numberOfMinesProtected = gameMines
			.stream()
			.filter(mine -> mine.getAnyProtected())
			.count();

		return puzzleMineCount - (int)numberOfMinesProtected;
	}

	/**
	 * Updates the game condition.
	 * 
	 * @return true if the game was won, false if the game is still continuing.
	 */
	public boolean updateGameCondition() {
		int uncoveredPieces = 0;
		boolean bombBlew = false;

		for (int i=0; i < gameMines.size(); i++) {
			Mine mine = gameMines.get(i);

			if (mine.blewUp()) {
				bombBlew = true;
			}
			
			if (mine.uncovered()) {
				uncoveredPieces++;
			}
		}

		// Winning condition
		int width = getCurrentPuzzleWidth();
		int height = getCurrentPuzzleHeight();
		int numMines = getCurrentPuzzleMineCount();
		int maxUncoverablePieces = width * height - numMines;

		if (uncoveredPieces == maxUncoverablePieces && !bombBlew) {
			setGameOver(true);
			return true;
		}

		return false;
	}
}