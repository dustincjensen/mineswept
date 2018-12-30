package services;

import java.util.ArrayList;

public class OctoCheckService {
    /**
     * Returns a list of integer positions that should be checked around an index.
     * Since the mines array is not multidimensional, there is some calculations
     * needed to determine what indexes the surrounded 8 squares are located at.
     * 
     * @param index the index of the square to check.
     * @param width the width of the mine field.
     * @param maxLength the maximum size of the mine field.
     * @return an array list of integer index positions.
     */
    public ArrayList<Integer> getPositionsToCheck(int index, int width, int maxLength) {
		var positionsToCheck = new ArrayList<Integer>(8);
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
