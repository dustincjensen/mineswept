package services;

import exceptions.GameOverException;
import models.Mines;

public class MineRevealService {
    private OctoCheckService octoService;

    public MineRevealService(OctoCheckService octoService) {
        this.octoService = octoService;
    }

    /**
     * Uncover the mine at the index position.
     * This method is recursive if the mine is an empty square.
     * 
     * @param index the position of the mine in the array.
     * @param mines the array of mines.
     * @param puzzleWidth the width of the puzzle.
     */
    public void uncover(int index, Mines mines, int puzzleWidth) throws GameOverException {
        var currentMine = mines.get(index);
		
		if (currentMine.uncovered() || currentMine.isProtected()) {
			return;
		}
		
		if (currentMine.isBomb()) {
			currentMine.setBlewUp(true);
            uncoverAll(mines);
            throw new GameOverException();
		}

		// Uncover the current mine if it is not protected.
		currentMine.setUncovered(true);

		// If the current spot is 0, then in addition we must get the positions around this one and uncover them as well.
		if (currentMine.getSpotValue() == 0) {
			var positionsToCheck = octoService.getPositionsToCheck(index, puzzleWidth, mines.size());
			for (var position : positionsToCheck) {
				uncover(position, mines, puzzleWidth);
			}
		}
    }

    /**
     * Uncover all the mines around the current mine position if the
     * required number of bombs have been marked.
     * 
     * @param index the position of the mine in the array.
     * @param mines the array of mines.
     * @param puzzleWidth the width of the puzzle.
     */
    public void specialUncover(int index, Mines mines, int puzzleWidth) throws GameOverException {
		// Retrieve the squares around this position on which we should check for flags.
		var positionsToCheck = octoService.getPositionsToCheck(index, puzzleWidth, mines.size());

		// Count the number of flags in the positions around the current.
		var flagCount = positionsToCheck
			.stream()
			.map(position -> mines.get(position).isProtected())
			.filter(isProtected -> isProtected)
			.count();

		// If the spot you clicked has appropriate flags marked do the uncovering
		if (flagCount == mines.get(index).getSpotValue()) {
			for (var position : positionsToCheck) {
				uncover(position, mines, puzzleWidth);
			}
		}
	}

	/**
     * Uncover all the mines on the field.
     * This is used when the game is lost and we want to show the mines.
     * 
     * @param mines the array of mines.
     */
    private void uncoverAll(Mines mines) {
        for (var i = 0; i < mines.size(); i++) {
			var mine = mines.get(i);
			var isBombAndNotProtected = mine.isBomb() && !mine.isProtected();
			var isNotBombAndIsProtected = mine.isProtected() && !mine.isBomb();

			if (isBombAndNotProtected || isNotBombAndIsProtected) {
				mine.setUncovered(true);
			}
		}
    }
}