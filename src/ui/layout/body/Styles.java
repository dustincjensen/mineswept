// TODO this might be temporary so we could allow the customization of the border and such.
// This does allow us to remove some clutter from MineButton.java though.
// Specifically for MineButton.java

package ui.layout.body;

import java.awt.Color;
import javax.swing.border.BevelBorder;

public class Styles {
    /**
     * The colors of the mine count numbers.
     */
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

    /**
     * The color for the background of the mine that was clicked on that caused the game over.
     */
    public static final Color FAILED_MINE_CLICKED_BACKGROUND_COLOR = Color.RED;

    /**
     * The border style for the mine before it has been clicked.
     */
    public static final BevelBorder RAISED_BORDER = new BevelBorder(BevelBorder.RAISED, Color.WHITE, Color.GRAY);

    /**
     * The border style for the mine after it has been clicked.
     */
    public static final BevelBorder LOWERED_BORDER = new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.GRAY);
}