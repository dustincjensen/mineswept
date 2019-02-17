package ui.layout.body;

import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import ui.utils.HexToRgb;

public class StylesModern {
    public static final Color[] MINE_NUMBER_COLORS = { 
        HexToRgb.convert("#2196f3"),
        HexToRgb.convert("#4caf50"),
        HexToRgb.convert("#f44336"),
        HexToRgb.convert("#9c27b0"),
        HexToRgb.convert("#e91e63"),
        HexToRgb.convert("#00bcd4"),
        HexToRgb.convert("#ff5722"),
        HexToRgb.convert("#ffffff") 
    };
    public static final Color MINE_BACKGROUND_COLOR = HexToRgb.convert("#1565c0");
    public static final Color MINE_ALT_BACKGROUND_COLOR = HexToRgb.convert("#1F6FcA");
    public static final Color MINE_CLICKED_BACKGROUND_COLOR = HexToRgb.convert("#212121");
    public static final Color MINE_CLICKED_ALT_BACKGROUND_COLOR = HexToRgb.convert("#2B2B2B");
    public static final Color FAILED_MINE_CLICKED_BACKGROUND_COLOR = HexToRgb.convert("#b71c1c");
    public static final Border RAISED_BORDER = BorderFactory.createEmptyBorder();
    public static final Border LOWERED_BORDER = BorderFactory.createEmptyBorder();
}