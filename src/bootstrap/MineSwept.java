package bootstrap;

import ui.window.Window;
import utils.ClassFactory;

public class MineSwept {
	private Window window;
	
	public MineSwept(Window window) {
		this.window = window;
	}

	public void start() {
		window.showWindow();
	}

	/**
	 * Main entry point for the application.
	 */
	public static void main(String[] args) {
		ClassFactory.create(MineSwept.class).start();
	}
}