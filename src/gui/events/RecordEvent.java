package gui.events;

import gui.menu.RecordWindow;

public class RecordEvent {

	public static void init() {}
	public static void doEvent() {
		RecordWindow.show(true);
	}
}