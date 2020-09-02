package ui.options.styles;

import models.options.BorderType;

public class Forest implements IStyle {
    @Override
    public String[] mineNumberColors() {
        return new String[] { 
            "#0077ff",
            "#008800",
            "#ff6600",
            "#0044ff",
            "#005500",
            "#ff3300",
            "#002200",
            "#001144"
        };
    }

    @Override
    public String mineBackgroundColor() {
        return "#003300";
    }

    @Override
    public String mineAltBackgroundColor() {
        return "#004400";
    }

    @Override
    public String mineClickedBackgroundColor() {
        return "#321000";
    }

    @Override
    public String mineClickedAltBackgroundColor() {
        return "#432100";
    }

    @Override
    public String failedMineClickedBackgroundColor() {
        return "#ff8d00";
    }

    @Override
    public BorderType raisedBorder() {
        return BorderType.BEVEL_RAISED;
    }

    @Override
    public BorderType loweredBorder() {
        return BorderType.EMPTY;
    }
}