package gui.events;

import gui.menu.OptionWindow;

public class Options {

	public static void init() {}
	public static void doEvent() {
		OptionWindow.show(true);//!optionsWindow.isVisible());
	}
}