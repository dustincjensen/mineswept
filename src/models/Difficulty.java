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
}
