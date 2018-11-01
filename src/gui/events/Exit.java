package gui.events;

import gui.MineSwept;
import logic.files.FileManagement;

public class Exit {
	public static void init() {}
	public static void doEvent() {
		//SHOW SAVE GAME ??
		FileManagement.saveFiles();
		//MineSwept.getWindow().dispose();
		System.exit(0);
	}
}