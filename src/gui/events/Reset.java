package gui.events;

import gui.MineSwept;
import gui.panel.header.*;
import gui.panel.mines.MinePanel;
import logic.game.*;

public class Reset {
	public static void init() {
	}

	public static void doEvent() {
		GameFeatures.reset();
		MineField.reset();
		ClockTimer.reset();
		MinePanel.reset();
		MineCount.reset();

		// Incase reset is pressed while paused.
		Pause.init();
		MineSwept.getMainPanel().pausePanel(false);

		MineSwept.refresh();
	}
}