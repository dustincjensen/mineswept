package gui.events;

import gui.MineSwept;
import gui.panel.header.ResetButton;
import logic.game.*;

public class Pause {
	private static boolean paused;

	public static void init() {
		paused = false;
	}

	public static void doEvent() {
		if (GameFeatures.isGameStarted() && !GameFeatures.isGameOver()) {
			if (!paused) {
				paused = true;
				ClockTimer.stop();
				ResetButton.setSmileyIcon(6);
				MineSwept.getMainPanel().pausePanel(true);
			} else {
				paused = false;
				ClockTimer.start();
				ResetButton.setSmileyIcon(1);
				MineSwept.getMainPanel().pausePanel(false);
			}
		}
	}
}