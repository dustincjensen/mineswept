package services;

import utils.RandomGen;
import state.GameState;
import models.Mine;
import models.Mines;
import java.util.function.Predicate;

public class HintService {
	private GameState gameState;
	private MineRevealService _mineRevealService;

	public HintService(GameState state, MineRevealService mineRevealService){
		gameState = state;
		_mineRevealService = mineRevealService;
	}

    /**
     * Find a hint and modify the mine to show it.
     * 
     * @param mines the field of mines.
     */
    public void useHint() {
		var mines = gameState.getMines();

		// Check empty spaces...
		var empty = randomEmptySpace(mines);
		if (empty != null) {
			var index = mines.indexOf(empty);

			try {
				_mineRevealService.uncover(index, mines, gameState.getCurrentPuzzleWidth());
			}
			catch (Exception ex) {}

			return;
		}

		// If there are no empty spaces left...
		// Click a random numbered square that isn't already uncovered.
		var nonEmpty = randomNonMine(mines);
		if (nonEmpty != null) {
			var index = mines.indexOf(nonEmpty);

			try {
				_mineRevealService.uncover(index, mines, gameState.getCurrentPuzzleWidth());
			}
			catch (Exception ex) {}

			return;
		}
	}
	
	private Mine randomEmptySpace(Mines mines) {
		return randomMine(mines, mine -> mine.getSpotValue() == 0 && !mine.uncovered() && !mine.isHint());
	}

	private Mine randomNonMine(Mines mines) {
		return randomMine(mines, mine -> mine.getSpotValue() > 0 && !mine.uncovered() && !mine.isHint());
	}

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