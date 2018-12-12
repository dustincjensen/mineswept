package logic.util;

import java.util.Random;

/**
 * Create a static random generator for use within the application.
 */
public class RandomGen {	
	private static Random randomGenerator;

	/**
	 * Retrieve a random integer.
	 * 
	 * @param upperLimit the maximum integer.
	 * @return A random integer from 0 (inclusive) to upperLimit (exclusive).
	 */
	public static int getRandomInt(int upperLimit) {
		if (randomGenerator == null) {
			randomGenerator = new Random();
		}
		return randomGenerator.nextInt(upperLimit);
	}
}