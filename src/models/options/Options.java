package models.options;

import ui.options.styles.Material;

public class Options {
    public BorderType raisedBorder;
    public BorderType loweredBorder;

    public String squareColor;
    public String squareAltColor;
    public String clickedColor;
    public String clickedAltColor;
    public String clickedFailColor;

    public String mineNumOneColor;
    public String mineNumTwoColor;
    public String mineNumThreeColor;
    public String mineNumFourColor;
    public String mineNumFiveColor;
    public String mineNumSixColor;
    public String mineNumSevenColor;
    public String mineNumEightColor;

    public String difficulty;

    // Populates the options with defaults.
    public Options() {
        raisedBorder = Material.RAISED_BORDER;
        loweredBorder = Material.LOWERED_BORDER;

        squareColor = Material.MINE_BACKGROUND_COLOR;
        squareAltColor = Material.MINE_ALT_BACKGROUND_COLOR;
        clickedColor = Material.MINE_CLICKED_BACKGROUND_COLOR;
        clickedAltColor = Material.MINE_CLICKED_ALT_BACKGROUND_COLOR;
        clickedFailColor = Material.FAILED_MINE_CLICKED_BACKGROUND_COLOR;

        mineNumOneColor = Material.MINE_NUMBER_COLORS[0];
        mineNumTwoColor = Material.MINE_NUMBER_COLORS[1];
        mineNumThreeColor = Material.MINE_NUMBER_COLORS[2];
        mineNumFourColor = Material.MINE_NUMBER_COLORS[3];
        mineNumFiveColor = Material.MINE_NUMBER_COLORS[4];
        mineNumSixColor = Material.MINE_NUMBER_COLORS[5];
        mineNumSevenColor = Material.MINE_NUMBER_COLORS[6];
        mineNumEightColor = Material.MINE_NUMBER_COLORS[7];

        difficulty = "easy";
    }
}