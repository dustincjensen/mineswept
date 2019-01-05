package gui;

public class MineSwept {
	private MainWindow window;
	
	public MineSwept(MainWindow window) {
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