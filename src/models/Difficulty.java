package models;

import java.util.Map;

public enum Difficulty {
    easy, medium, hard;
    
    public static Difficulty getDifficulty(int value) {
        return Map.of(
            0, easy,
            1, medium,
            2, hard
        ).get(value);
    }

    public static Difficulty getDifficulty(String value) {
        return Map.of(
            "easy", Difficulty.easy,
            "medium", Difficulty.medium,
            "hard", Difficulty.hard
        ).get(value);
    }

    public static String getProperName(int value) {
        return Map.of(
            0, "Easy",
            1, "Medium",
            2, "Hard"
        ).get(value);
    }

    public static String getProperName(Difficulty difficulty) {
        return Map.of(
            easy, "Easy",
            medium, "Medium",
            hard, "Hard"
        ).get(difficulty);
    }
}
