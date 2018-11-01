package logic.util;

import java.util.Random;

public class RandomGen {	

	private static Random randomGenerator;

	public static void init() {
		randomGenerator = new Random();
	}

	public static int getRandomInt(int limit) {
		return randomGenerator.nextInt(limit);
	}

}