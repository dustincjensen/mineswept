package models.options;

import ui.utils.HexToRgb;

public class Options {
    public BorderType raisedBorder;
    public BorderType loweredBorder;

    public Color squareColor;
    public Color squareAltColor;
    public Color clickedColor;
    public Color clickedAltColor;
    public Color clickedFailColor;

    public Color mineNumOneColor;
    public Color mineNumTwoColor;
    public Color mineNumThreeColor;
    public Color mineNumFourColor;
    public Color mineNumFiveColor;
    public Color mineNumSixColor;
    public Color mineNumSevenColor;
    public Color mineNumEightColor;

    public String difficulty;

    // Populates the options with defaults.
    public Options() {
        raisedBorder = BorderType.EMPTY;
        loweredBorder = BorderType.EMPTY;

        squareColor = HexToRgb.toOptionsColor("#1565c0");
        squareAltColor = HexToRgb.toOptionsColor("#1F6FcA");
        clickedColor = HexToRgb.toOptionsColor("#212121");
        clickedAltColor = HexToRgb.toOptionsColor("#2B2B2B");
        clickedFailColor = HexToRgb.toOptionsColor("#b71c1c");

        mineNumOneColor = HexToRgb.toOptionsColor("#2196f3");
        mineNumTwoColor = HexToRgb.toOptionsColor("#4caf50");
        mineNumThreeColor = HexToRgb.toOptionsColor("#f44336");
        mineNumFourColor = HexToRgb.toOptionsColor("#9c27b0");
        mineNumFiveColor = HexToRgb.toOptionsColor("#e91e63");
        mineNumSixColor = HexToRgb.toOptionsColor("#00bcd4");
        mineNumSevenColor = HexToRgb.toOptionsColor("#ff5722");
        mineNumEightColor = HexToRgb.toOptionsColor("#ffffff");

        difficulty = "easy";
    }
}