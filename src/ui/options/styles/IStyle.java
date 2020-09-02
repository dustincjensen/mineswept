package ui.options.styles;

import models.options.BorderType;

public interface IStyle {
    String[] mineNumberColors();
    String mineBackgroundColor();
    String mineAltBackgroundColor();
    String mineClickedBackgroundColor();
    String mineClickedAltBackgroundColor();
    String failedMineClickedBackgroundColor();
    BorderType raisedBorder();
    BorderType loweredBorder();
}