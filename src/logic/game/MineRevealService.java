package logic.game;

import java.util.ArrayList;

public class MineRevealService {
    private OctoCheckService octoCheckService;

    public MineRevealService(OctoCheckService octoService) {
        octoCheckService = octoService;
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
        Mine currentMine = mines.get(index);
		
		// If we are a bomb and am not protected, we need to blow up.
		if (currentMine.isBomb() && !currentMine.getAnyProtected()) {
			currentMine.setBlewUp(true);
            uncoverAll(mines);
            throw new GameOverException();
		}

		// If we are already uncovered, nothing to do here. Or it is protected... move on.
		if (currentMine.uncovered() || currentMine.getAnyProtected()) {
			return;
		}

		// Uncover the current mine if it is not protected.
		currentMine.setUncovered(true);

		// If the current spot is 0, then in addition we must get the positions around this one and uncover them as well.
		if (currentMine.getSpotValue() == 0) {
			ArrayList<Integer> positionsToCheck = octoCheckService.getPositionsToCheck(index, puzzleWidth, mines.size());
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
		ArrayList<Integer> positionsToCheck = octoCheckService.getPositionsToCheck(index, puzzleWidth, mines.size());

		// Count the number of flags in the positions around the current.
		var flagCount = positionsToCheck
			.stream()
			.map(position -> mines.get(position).getAnyProtected())
			.filter(isProtected -> isProtected)
			.count();

		// If the spot you clicked has appropriate flags marked do the uncovering
		if (flagCount == mines.get(index).getSpotValue()) {
			for (int position : positionsToCheck) {
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
        for (int i = 0; i < mines.size(); i++) {
			Mine mine = mines.get(i);
			boolean isBombAndNotProtected = mine.isBomb() && !mine.getAnyProtected();
			boolean isNotBombAndIsProtected = mine.getAnyProtected() && !mine.isBomb();

			if (isBombAndNotProtected || isNotBombAndIsProtected) {
				mine.setUncovered(true);
			}
		}
    }
}