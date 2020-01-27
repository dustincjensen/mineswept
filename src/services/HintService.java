package services;

import utils.RandomGen;
import state.GameState;
import models.Mine;
import models.Mines;

public class HintService {
	private GameState gameState;
	private MineRevealService _mineRevealService;

	public HintService(GameState state, MineRevealService mineRevealService){
		System.out.println("Creating new HINT SERVICE");
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

		// TODO random order OR find numbers that have flags beside them, but are not yet completed... and add a new flag.
		// Find bombs...
		// Will set them to special protected, so they can't be tampered with.
		for (int i=0; i < mines.size(); i++) {
			var mine = mines.get(i);

			if (mine.isBomb() && !mine.getAnyProtected()) {
				mine.setHint(true);
				mine.setSpecialProtected(true);
				return;
			}
		}
	}
	
	private Mine randomEmptySpace(Mines mines) {
		var filteredMines = (Mine[]) mines.stream()
			.filter(mine -> mine.getSpotValue() == 0 && !mine.uncovered() && !mine.isHint())
			.toArray(Mine[]::new);

		if (filteredMines.length > 0) {
			var randomMineIndex = RandomGen.getRandomInt(filteredMines.length);
			return filteredMines[randomMineIndex];
		} else {
			return null;
		}
	}
}