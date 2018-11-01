package logic.game;

public class GameFeatures {

	private static boolean gameStarted;
	private static boolean gameOver;

	public static void init() {
		gameStarted = false;
	}

	public static boolean isGameStarted() {
		return gameStarted;
	}

	public static boolean isGameOver() {
		return gameOver;
	}

	public static void setGameStarted(boolean game) {
		gameStarted = game;
	}

	public static void setGameOver(boolean game) {
		gameOver = game;
	}

	public static void reset() {
		gameStarted = false;
		gameOver = false;
	}

}