package gui.events;

import gui.menu.StatisticsWindow;

public class StatisticsEvent {
	public static void init() {
	}

	public static void doEvent() {
		StatisticsWindow.show(true);
	}
}