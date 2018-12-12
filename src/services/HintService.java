package services;

import utils.RandomGen;
import logic.game.GameState;
import models.Mine;
import models.Mines;

public class HintService {
	private GameState gameState;

	public HintService(GameState state) {
		System.out.println("Creating new HINT SERVICE");
		gameState = state;
	}

    /**
     * Find a hint and modify the mine to show it.
     * 
     * @param mines the field of mines.
     */
    public void useHint() {
		Mines mines = gameState.getMines();

		// Check empty spaces...
		Mine empty = randomEmptySpace(mines);
		if (empty != null) {
			empty.setHint(true);
			empty.setProtected(false);
			return;
		}

		// TODO random order OR find numbers that have flags beside them, but are not yet completed... and add a new flag.
		// Find bombs...
		// Will set them to special protected, so they can't be tampered with.
		for (int i=0; i < mines.size(); i++) {
			Mine mine = mines.get(i);

			if (mine.isBomb() && !mine.getAnyProtected()) {
				mine.setHint(true);
				mine.setSpecialProtected(true);
				return;
			}
		}
	}
	
	private Mine randomEmptySpace(Mines mines) {
		Mine[] filteredMines = (Mine[]) mines.stream()
			.filter(mine -> mine.getSpotValue() == 0 && !mine.uncovered() && !mine.isHint())
			.toArray(Mine[]::new);

		if (filteredMines.length > 0) {
			int randomMineIndex = RandomGen.getRandomInt(filteredMines.length);
			return filteredMines[randomMineIndex];
		} else {
			return null;
		}
	}
}