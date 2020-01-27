package models.options;

public class Options {
    public Color squareColor;
    public Color squareAltColor;
    public String difficulty;

    public Options() {
        squareColor = new Color(21, 101, 192);
        squareAltColor = new Color(31, 111, 202);
        difficulty = "easy";
    }
}