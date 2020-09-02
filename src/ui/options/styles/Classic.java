package ui.options.styles;

import models.options.BorderType;

public class Classic implements IStyle {
    @Override
    public String[] mineNumberColors() {
        return new String[] { 
            "#0000ff",
            "#449644",
            "#ff0000",
            "#4a004a",
            "#800000",
            "#41e0d1",
            "#000000",
            "#444444",
        };
    }

    @Override
    public String mineBackgroundColor() {
        return "#dddddd";
    }

    @Override
    public String mineAltBackgroundColor() {
        return "#dddddd";
    }

    @Override
    public String mineClickedBackgroundColor() {
        return "#eeeeee";
    }

    @Override
    public String mineClickedAltBackgroundColor() {
        return "#eeeeee";
    }

    @Override
    public String failedMineClickedBackgroundColor() {
        return "#ff0000";
    }

    @Override
    public BorderType raisedBorder() {
        return BorderType.CLASSIC_BEVEL_RAISED;
    }

    @Override
    public BorderType loweredBorder() {
        return BorderType.CLASSIC_BEVEL_LOWERED;
    }
}