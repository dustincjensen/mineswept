package models.preferences;

public class Preference {
    public Color squareColor;
    public String difficulty;

    public Preference() {
        squareColor = new Color(50, 125, 240);
        difficulty = "easy";
    }
}