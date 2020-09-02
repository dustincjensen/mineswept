package models.options;

import ui.options.styles.Material;

public class Options {
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

    public BorderType raisedBorder;
    public BorderType loweredBorder;
    
    public String difficulty;

    // Populates the options with defaults.
    public Options() {
        var style = new Material();
        squareColor = style.mineBackgroundColor();
		squareAltColor = style.mineAltBackgroundColor();
		clickedColor = style.mineClickedBackgroundColor();
		clickedAltColor = style.mineClickedAltBackgroundColor();
		clickedFailColor = style.failedMineClickedBackgroundColor();
		
		var styleNumberColors = style.mineNumberColors();
		mineNumOneColor = styleNumberColors[0];
        mineNumTwoColor = styleNumberColors[1];
        mineNumThreeColor = styleNumberColors[2];
        mineNumFourColor = styleNumberColors[3];
        mineNumFiveColor = styleNumberColors[4];
        mineNumSixColor = styleNumberColors[5];
        mineNumSevenColor = styleNumberColors[6];
        mineNumEightColor = styleNumberColors[7];

        raisedBorder = style.raisedBorder();
        loweredBorder = style.loweredBorder();

        difficulty = "easy";
    }
}