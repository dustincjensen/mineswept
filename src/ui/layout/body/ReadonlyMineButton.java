package ui.layout.body;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;

/**
 * Used only to create a grid like view for the pause screen.
 */
@SuppressWarnings("serial")
public class ReadonlyMineButton extends JLabel {
    private static final int w = 48, h = 48;
    private Color backgroundColor = StylesModern.MINE_CLICKED_BACKGROUND_COLOR;
    private Color altBackgroundColor = StylesModern.MINE_CLICKED_ALT_BACKGROUND_COLOR;

    public ReadonlyMineButton (int x, int y) {
        setOpaque(true);

        // Set all these the same to force the layout to behave...
		var size = new Dimension(w, h);
		setPreferredSize(size);
		setMaximumSize(size);
        setMinimumSize(size);
        
        if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) {
			setBackground(backgroundColor);
		} else {
			setBackground(altBackgroundColor);
		}
    }
}