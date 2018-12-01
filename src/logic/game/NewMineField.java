package logic.game;

import java.util.ArrayList;
import logic.util.RandomGen;

public class NewMineField {
    public NewMineField() {

    }

    /**
     * Create a new field of mines.
     * 
     * @param width the width of the field.
     * @param height the height of the field.
     * @param maxSize the maximum size of the field.
     * @return the field of mines.
     */
    public Mines createMines(int width, int height, int maxSize) {
        Mines mines = new Mines(maxSize);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mines.add(new Mine(x, y));
            }
        }
        return mines;
    }

    /**
     * Fills the field with mines.
     * 
     * @param mines the field to fill.
     * @param numberOfMines the number of mines to fill it with.
     */
    public void fillMines(Mines mines, int numberOfMines) {
		for (int i=0; i < numberOfMines; i++) {
			while (true) {
				int randomNumber = RandomGen.getRandomInt(mines.size());
				Mine mine = mines.get(randomNumber);

				if (!mine.isBomb()) {
					mine.setBomb(true);
					break;
				}
			}
		}
	}

    /**
     * Sets all the numbers on the field.
     * 
     * @param mines the field to fill.
     * @param width the width of the field.
     */
	public void fillNumbers(Mines mines, int width) {
		for (int i=0; i < mines.size(); i++) {
			Mine mine = mines.get(i);

			// If we are a bomb, we don't need a number.
			if (mine.isBomb()) {
				continue;
			}

			long count = getPositionsToCheck(i, width, mines.size())
				.stream()
				.map(position -> mines.get(position).isBomb())
				.filter(isBomb -> isBomb)
				.count();

			mine.setSpotValue((int)count);
		}
    }
    
    /**
     * Find a hint and modify the mine to show it.
     * 
     * @param mines the field of mines.
     */
    public void getHint(Mines mines) {
        // Do empty spaces first
		for (int i=0; i < mines.size(); i++) {
			Mine mine = mines.get(i);

			if (mine.getSpotValue() == 0 && !mine.uncovered()) {
				mine.setHint(true);
				mine.setProtected(false);
				return;
			}
		}

		// Find bombs...
		// Will use a blue flag and make it unchangeable.
		for (int i=0; i < mines.size(); i++) {
			Mine mine = mines.get(i);

			if (mine.isBomb() && !mine.getAnyProtected()) {
				mine.setHint(true);
				mine.setSpecialProtected(true);
				return;
			}
		}
    }

	// TODO make this private again, or abstract out into another file once MineField has been replaced.
    public static ArrayList<Integer> getPositionsToCheck(int index, int width, int maxLength) {
		ArrayList<Integer> positionsToCheck = new ArrayList<Integer>(8);
		if (index - width >= 0) {
			positionsToCheck.add(index - width);
		}
		if (index + width < maxLength) {
			positionsToCheck.add(index + width);
		}

		// You are not on the left side of the puzzle.
		if (index % width != 0) {
			if (index - width - 1 >= 0) {
				positionsToCheck.add(index - width - 1);
			}
			if (index - 1 >= 0) {
				positionsToCheck.add(index - 1);
			}
			if (index + width - 1 < maxLength) {
				positionsToCheck.add(index + width - 1);
			}
		}

		// You are not on the right side of the puzzle.
		if ((index + 1) % width != 0) {
			if (index - width + 1 >= 0) {
				positionsToCheck.add(index - width + 1);
			}
			if (index + 1 < maxLength) {
				positionsToCheck.add(index + 1);
			}
			if (index + width + 1 < maxLength) {
				positionsToCheck.add(index + width + 1);
			}
		}

		return positionsToCheck;
	}
}