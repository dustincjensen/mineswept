package logic.game;

import gui.events.IEventPublisher;
import gui.events.SetResetButtonIconEvent;
import gui.Resource;
import java.util.ArrayList;
import logic.util.RandomGen;

public class MineField {
	private static GameState gameState;
	private static ClockTimer clockTimer;
	private static IEventPublisher eventPublisher;

	public static void init(GameState state, ClockTimer timer, IEventPublisher publisher) {
		gameState = state;
		clockTimer = timer;
		eventPublisher = publisher;
	}

	public static void leftClicked(int x, int y) {
		Mines mines = gameState.getMines();
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
		
		// TODO these will probably be taken care of in the event handler.
		if (gameState.updateGameCondition()) {
			clockTimer.stop();
			eventPublisher.publish(new SetResetButtonIconEvent(Resource.SmileyCool));
		};
	}

	private static void specialUncover(int i) {
		Mines mines = gameState.getMines();

		// Retrieve the squares around this position on which we should check for flags.
		ArrayList<Integer> positionsToCheck = NewMineField.getPositionsToCheck(i, gameState.getCurrentPuzzleWidth(), mines.size());

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
		Mines mines = gameState.getMines();
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
			ArrayList<Integer> positionsToCheck = NewMineField.getPositionsToCheck(i, gameState.getCurrentPuzzleWidth(), mines.size());
			for (var position : positionsToCheck) {
				specialUncoverOne(position);
			}
		}
	}

	private static void uncoverAll() {
		Mines mines = gameState.getMines();
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
		eventPublisher.publish(new SetResetButtonIconEvent(Resource.SmileySad));
	}

	private static void uncover(int i) {
		Mines mines = gameState.getMines();
		Mine currentMine = mines.get(i);

		// If we are already uncovered, protected or are a bomb, nothing to do here.
		if (currentMine.isBomb() || currentMine.uncovered() || currentMine.getAnyProtected()) {
			return;
		}

		// Uncover the current mine.
		currentMine.setUncovered(true);

		// If the current spot is 0, then in addition we must get the positions around this one and uncover them as well.
		if (currentMine.getSpotValue() == 0) {
			ArrayList<Integer> positionsToCheck = NewMineField.getPositionsToCheck(i, gameState.getCurrentPuzzleWidth(), mines.size());
			for (var position : positionsToCheck) {
				uncover(position);
			}
		}
	}
}