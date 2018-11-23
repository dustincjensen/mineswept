package logic.game;

import gui.Resource;
import gui.panel.header.ResetButton;
import java.util.ArrayList;
import java.util.Map;
import logic.util.RandomGen;
import logic.files.Preferences;

public class MineField {
	private static Mines mines;

	private static Map<String, Difficulty> difficultyMap = Map.of(
		"easy", Difficulty.easy,
		"medium", Difficulty.intermediate,
		"hard", Difficulty.advanced);

	private static GameState gameState;
	private static ClockTimer clockTimer;

	// TODO this will be the next non-static class.
	// Seems like most of the logic classes are singletons and their state is only initialized
	// when they are first requested.
	// I think this class should not be a physical entity, but the mines should be part of GameState.
	// This class would contain methods to get a list of mines, run methods like uncover, on a list of mines,
	// but the mines would be part of the game state... then the mine field is not being injected into other places.
	// It is likely that the clock timer time, should also be part of game state.
	public static void init(Preferences prefs, GameState state, ClockTimer timer) {
		gameState = state;
		clockTimer = timer;

		Difficulty difficulty = difficultyMap.get(
			prefs.difficulty().toLowerCase()
		);

		gameState.setCurrentPuzzleDifficulty(difficulty);
		gameState.setNextPuzzleDifficulty(difficulty);

		// Initial size is the capacity, which will never have to be increased,
		// but calling .size() only gives the ones that are filled in.
		mines = new Mines(gameState.getMaxNumberOfMineFieldSquares());
		setupField();
	}

	public static void reset() {
		mines.clear();
		gameState.setCurrentPuzzleToNextPuzzle();
		setupField();
	}

	public static void leftClicked(int x, int y) {
		int i = mines.contains(x,y);
		var mine = mines.get(i);

		// If the mine is protected we cannot do anything to it.
		if (mine.getAnyProtected()) {
			return;
		}

		// If already uncovered, use a special uncover.
		if (mine.uncovered()) {
			specialUncover(i);
		}

		// If we are a bomb, then uncover everything and mark the bomb that caused the failure.
		if (mine.isBomb()) {
			uncoverAll();
			mine.setBlewUp(true);
		}
		else {
			// Otherwise uncover the square
			uncover(i);
		}
		
		checkGameCondition();
	}

	public static Mine rightClicked(int x, int y) {
		return mines.get(x,y);
	}

	public static void getHint() {
		// Do empty spaces first
		for (int i=0; i < mines.size(); i++) {
			Mine m = mines.get(i);

			// Empty space
			if (m.getSpotValue() == 0 && !m.uncovered()) {
				m.setHint(true);
				m.setProtected(false);
				return;
			}
		}

		// Find bombs ...
		// will use a blue flag and make it unchangeable
		for (int i=0; i < mines.size(); i++) {
			Mine m = mines.get(i);

			if (m.isBomb() && !m.getAnyProtected()) {
				m.setHint(true);
				m.setSpecialProtected(true);
				return;
			}
		}
	}

	public static Mine getMine(int x, int y) {
		return mines.get(x,y);
	}

	public static int getMineCount() {
		int count = 0;
		for (int i=0; i<mines.size(); i++) {
			if (mines.get(i).getAnyProtected()) {
				count++;
			}
		}
		return getNumMines()-count;
	}

	public static int getWidth() {
		return gameState.getCurrentPuzzleWidth();
	}

	public static int getHeight() {
		return gameState.getCurrentPuzzleHeight();
	}

	public static int getNumMines() {
		return gameState.getCurrentPuzzleMineCount();
	}

	private static void setupField() {
		initMines();
		fillMines();
		fillNumbers();
	}

	private static void initMines() {
		int height = gameState.getCurrentPuzzleHeight();
		int width = gameState.getCurrentPuzzleWidth();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				mines.add(new Mine(x,y));
			}
		}
	}

	private static void fillMines() {
		for (int i=0; i < gameState.getCurrentPuzzleMineCount(); i++) {
			while (true) {
				int randomNumber = RandomGen.getRandomInt(mines.size());
				var mine = mines.get(randomNumber);

				if (!mine.isBomb()) {
					mine.setBomb(true);
					break;
				}
			}
		}
	}

	private static void fillNumbers() {
		for (int i=0; i < mines.size(); i++) {
			var mine = mines.get(i);

			// If we are a bomb, we don't need a number.
			if (mine.isBomb()) {
				continue;
			}

			var count = getPositionsToCheck(i, gameState.getCurrentPuzzleWidth(), mines.size())
				.stream()
				.map(position -> mines.get(position).isBomb())
				.filter(isBomb -> isBomb)
				.count();

			mine.setSpotValue((int)count);
		}
	}
	
	private static ArrayList<Integer> getPositionsToCheck(int index, int width, int maxLength) {
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

	private static void specialUncover(int i) {
		// Retrieve the squares around this position on which we should check for flags.
		ArrayList<Integer> positionsToCheck = getPositionsToCheck(i, gameState.getCurrentPuzzleWidth(), mines.size());

		// Count the number of flags in the positions around the current.
		var flagCount = positionsToCheck
			.stream()
			.map(position -> mines.get(position).getAnyProtected())
			.filter(isProtected -> isProtected)
			.count();

		// If the spot you clicked has appropriate flags marked do the uncovering
		if (flagCount == mines.get(i).getSpotValue()) {
			for (var pos : positionsToCheck) {
				specialUncoverOne(pos);
			}
		}
	}

	private static void specialUncoverOne(int i) {
		Mine currentMine = mines.get(i);
		
		// If we are a bomb and am not protected, we need to blow up.
		if (currentMine.isBomb() && !currentMine.getAnyProtected()) {
			currentMine.setBlewUp(true);
			uncoverAll();
			return;
		}

		// If we are already uncovered, nothing to do here. Or it is protected... move on.
		if (currentMine.uncovered() || currentMine.getAnyProtected()) {
			return;
		}

		// Uncover the current mine if it is not protected.
		if (!currentMine.getAnyProtected()) {
			currentMine.setUncovered(true);
		}

		// If the current spot is 0, then in addition we must get the positions around this one and uncover them as well.
		if (currentMine.getSpotValue() == 0) {
			ArrayList<Integer> positionsToCheck = getPositionsToCheck(i, gameState.getCurrentPuzzleWidth(), mines.size());
			for (var position : positionsToCheck) {
				specialUncoverOne(position);
			}
		}
	}

	private static void uncoverAll() {
		for (int i=0; i < mines.size(); i++) {
			Mine mine = mines.get(i);
			boolean isBombAndNotProtected = mine.isBomb() && !mine.getAnyProtected();
			boolean isNotBombAndIsProtected = mine.getAnyProtected() && !mine.isBomb();

			if(isBombAndNotProtected || isNotBombAndIsProtected) {
				mine.setUncovered(true);
			}
		}

		gameState.setGameOver(true);
		clockTimer.stop();
		ResetButton.setSmileyIcon(Resource.SmileySad);
	}

	private static void uncover(int i) {
		Mine currentMine = mines.get(i);

		// If we are already uncovered, protected or are a bomb, nothing to do here.
		if (currentMine.isBomb() || currentMine.uncovered() || currentMine.getAnyProtected()) {
			return;
		}

		// Uncover the current mine.
		currentMine.setUncovered(true);

		// If the current spot is 0, then in addition we must get the positions around this one and uncover them as well.
		if (currentMine.getSpotValue() == 0) {
			ArrayList<Integer> positionsToCheck = getPositionsToCheck(i, gameState.getCurrentPuzzleWidth(), mines.size());
			for (var position : positionsToCheck) {
				uncover(position);
			}
		}
	}

	private static void checkGameCondition() {
		int uncoveredPieces = 0;
		boolean bombBlew = false;

		for (int i=0; i<mines.size(); i++) {
			if (mines.get(i).blewUp()) {
				bombBlew = true;
			}
			
			if (mines.get(i).uncovered()) {
				uncoveredPieces++;
			}
		}

		// Winning condition
		int width = gameState.getCurrentPuzzleWidth();
		int height = gameState.getCurrentPuzzleHeight();
		int numMines = gameState.getCurrentPuzzleMineCount();
		int maxUncoverablePieces = width * height - numMines;

		if (uncoveredPieces == maxUncoverablePieces && !bombBlew) {
			gameState.setGameOver(true);
			clockTimer.stop();
			ResetButton.setSmileyIcon(Resource.SmileyCool);
		}
	}
}