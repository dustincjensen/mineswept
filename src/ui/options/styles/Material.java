package ui.options.styles;

import models.options.BorderType;

public class Material implements IStyle {
    @Override
    public String[] mineNumberColors() {
        return new String[] { 
            "#2196f3",
            "#4caf50",
            "#f44336",
            "#9c27b0",
            "#e91e63",
            "#00bcd4",
            "#ff5722",
            "#ffffff" 
        };
    }

    @Override
    public String mineBackgroundColor() {
        return "#1565c0";
    }

    @Override
    public String mineAltBackgroundColor() {
        return "#1F6FcA";
    }

    @Override
    public String mineClickedBackgroundColor() {
        return "#212121";
    }

    @Override
    public String mineClickedAltBackgroundColor() {
        return "#2B2B2B";
    }

    @Override
    public String failedMineClickedBackgroundColor() {
        return "#b71c1c";
    }

    @Override
    public BorderType raisedBorder() {
        return BorderType.EMPTY;
    }

    @Override
    public BorderType loweredBorder() {
        return BorderType.EMPTY;
    }
}