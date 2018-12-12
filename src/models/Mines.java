package models;

import java.util.Vector;

public class Mines extends Vector<Mine> {
	public Mines(int size) {
		super(size);
	}

	/**
	 * Returns the index of the mine if it exists.
	 * 
	 * @param x the x coordinate of the mine.
	 * @param y the y coordinate of the mine.
	 * @return the index of the mine or -1 if it does not exist.
	 */
	public int contains(int x, int y) {
		for (int i = 0; i < size(); i++) {
			if (x == get(i).x() && y == get(i).y()) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the mine at position (x, y).
	 * 
	 * @param x the x coordinate of the mine.
	 * @param y the y coordinate of the mine.
	 * @return the mine or null if it does not exist.
	 */
	public Mine get(int x, int y) {
		for (int i = 0; i < size(); i++) {
			if (x == get(i).x() && y == get(i).y()) {
				return get(i);
			}
		}
		return null;
	}
}