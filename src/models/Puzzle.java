package models;

import java.util.Map;

public class Puzzle {
    /**
     * The widths of puzzles by difficulty.
     */
    public static Map<Difficulty, Integer> widths = Map.of(
        Difficulty.easy, 9,
        Difficulty.medium, 16,
        Difficulty.hard, 30
    );

    /**
     * The heights of puzzles by difficulty.
     */
	public static Map<Difficulty, Integer> heights = Map.of(
        Difficulty.easy, 9,
        Difficulty.medium, 16,
        Difficulty.hard, 16
    );

    /**
     * The number of mines of puzzles by difficulty.
     */
    public static Map<Difficulty, Integer> mines = Map.of(
        Difficulty.easy, 10,
        Difficulty.medium, 40,
        Difficulty.hard, 99
    );
}