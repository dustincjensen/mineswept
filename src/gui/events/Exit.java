package gui.events;

import gui.MineSwept;
import logic.files.FileManagement;

public class Exit {
	public static void init() {
	}

	public static void doEvent() {
		// TODO Show Save Game?
		FileManagement.saveFiles();
		// TODO is this needed?
		// MineSwept.getWindow().dispose();
		System.exit(0);
	}
}