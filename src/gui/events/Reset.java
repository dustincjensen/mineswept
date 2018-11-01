package gui.events;

import logic.game.*;
import gui.MineSwept;
import gui.panel.mines.MinePanel;
import gui.panel.header.*;

public class Reset {
	public static void init() {}
	public static void doEvent() {
		GameFeatures.reset();
		MineField.reset();
		ClockTimer.reset();
		MinePanel.reset();
		MineCount.reset();

		//INCASE RESET IS PRESSED WHILE PAUSED
		Pause.init();
		MineSwept.getMainPanel().pausePanel(false);
		
		MineSwept.refresh();
	}
}