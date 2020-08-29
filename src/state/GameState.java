package state;

import factories.MinesFactory;
import models.Difficulty;
import models.Mine;
import models.Mines;
import models.Puzzle;
import services.OptionsService;

/**
 * The state of the game.
 */
public class GameState {
	private MinesFactory minesFactory;
	private OptionsService optionsService;

	private boolean gameStarted;
	private boolean gameOver;
	private boolean gamePaused;
	private boolean hintUsed;
	private Difficulty currentPuzzleDifficulty;
	private Mines gameMines;

	public GameState(OptionsService optionsService, MinesFactory minesFactory) {
		this.optionsService = optionsService;
		this.minesFactory = minesFactory;
		init();
	}

	/**
	 * Initialize the game state.
	 */
	public void init() {
		gameStarted = false;
		gameOver = false;
		gamePaused = false;
		hintUsed = false;
		currentPuzzleDifficulty = optionsService.difficulty();
		
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
	 * Set gameStarted to true.
	 */
	public void setGameStarted() {
		gameStarted = true;
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
	 * Set gameOver to true.
	 */
	public void setGameOver() {
		gameOver = true;
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
	 * Returns if a hint has been used.
	 * 
	 * @return true if a hint has been used.
	 */
	public boolean wasHintUsed() {
		return hintUsed;
	}

	/**
	 * Set hintUsed to true.
	 */
	public void setHintUsed() {
		hintUsed = true;
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
	 * Get the current puzzle height.
	 * 
	 * @return the height of the current puzzle.
	 */
	public int getCurrentPuzzleHeight() {
		return Puzzle.heights.get(currentPuzzleDifficulty);
	}

	/**
	 * Get the current puzzle width.
	 * 
	 * @return the width of the current puzzle.
	 */
	public int getCurrentPuzzleWidth() {
		return Puzzle.widths.get(currentPuzzleDifficulty);
	}

	/**
	 * Get the current puzzle mine count.
	 * 
	 * @return the number of mines in the current puzzle.
	 */
	public int getCurrentPuzzleMineCount() {
		return Puzzle.mines.get(currentPuzzleDifficulty);
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
			.filter(mine -> mine.isProtected())
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
			setGameOver();
			return true;
		}

		return false;
	}
}