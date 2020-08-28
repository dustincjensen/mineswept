package ui.options.styles;

import java.awt.Color;
import javax.swing.border.BevelBorder;

public class Classic {
    public static final Color[] MINE_NUMBER_COLORS = { 
        Color.BLUE,                 // One
        new Color(67, 150, 67),     // Two
        Color.RED,                  // Three
        new Color(75, 0, 75),       // Four
        new Color(128, 0, 0),       // Five
        new Color(64, 224, 208),    // Six
        new Color(160, 92, 240),    // Seven
        new Color(0, 0, 0)          // Eight
    };

    // todo being null doesn't set the options color...
    public static final Color MINE_BACKGROUND_COLOR = new Color(0xdddddd);
    public static final Color MINE_ALT_BACKGROUND_COLOR = new Color(0xdddddd);
    public static final Color MINE_CLICKED_BACKGROUND_COLOR = new Color(0xeeeeee);
    public static final Color MINE_CLICKED_ALT_BACKGROUND_COLOR = new Color(0xeeeeee);
    public static final Color FAILED_MINE_CLICKED_BACKGROUND_COLOR = Color.RED;
    
    public static final BevelBorder RAISED_BORDER = new BevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY);
    public static final BevelBorder LOWERED_BORDER = new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.GRAY);
}