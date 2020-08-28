package services;

import exceptions.GameOverException;
import java.util.function.Predicate;
import models.Mine;
import models.Mines;
import state.GameState;
import utils.RandomGen;

public class HintService {
	private GameState gameState;
	private MineRevealService mineRevealService;

	public HintService(GameState gameState, MineRevealService mineRevealService){
		this.gameState = gameState;
		this.mineRevealService = mineRevealService;
	}

    /**
     * Find a hint and modify the mine to show it.
     */
    public void useHint() {
		var mines = gameState.getMines();

		// Check empty spaces...
		var empty = randomEmptySpace(mines);
		if (empty != null) {
			var index = mines.indexOf(empty);

			try {
				mineRevealService.uncover(index, mines, gameState.getCurrentPuzzleWidth());
			}
			catch (GameOverException ex) {}

			return;
		}

		// If there are no empty spaces left...
		// Click a random numbered square that isn't already uncovered.
		var nonEmpty = randomNonMine(mines);
		if (nonEmpty != null) {
			var index = mines.indexOf(nonEmpty);

			try {
				mineRevealService.uncover(index, mines, gameState.getCurrentPuzzleWidth());
			}
			catch (GameOverException ex) {}
		}
	}
	
	/**
	 * Return a random empty space that isn't already uncovered.
	 * 
	 * @param mines the list of mines to search.
	 * @return a random mine, or null if no mine matches the criteria.
	 */
	private Mine randomEmptySpace(Mines mines) {
		return randomMine(mines, mine -> mine.getSpotValue() == 0 && !mine.uncovered());
	}

	/**
	 * Return a random numbered space that isn't already uncovered.
	 * 
	 * @param mines the list of mines to search.
	 * @return a random mine, or null if no mine matches the criteria.
	 */
	private Mine randomNonMine(Mines mines) {
		return randomMine(mines, mine -> mine.getSpotValue() > 0 && !mine.uncovered());
	}

	/**
	 * Helper method that finds a mine using the provided predicate.
	 * 
	 * @param mines the list of mines to search.
	 * @param predicate the criteria to filter by.
	 * @return a random mine, or null if no mine matches the criteria.
	 */
	private Mine randomMine(Mines mines, Predicate<Mine> predicate) {
		var filteredMines = (Mine[]) mines.stream()
			.filter(mine -> predicate.test(mine))
			.toArray(Mine[]::new);

		if (filteredMines.length > 0) {
			var randomMineIndex = RandomGen.getRandomInt(filteredMines.length);
			return filteredMines[randomMineIndex];
		} else {
			return null;
		}
	}
}