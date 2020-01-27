package ui.layout.body;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 * Used only to create a grid like view for the pause screen.
 */
@SuppressWarnings("serial")
public class ReadonlyMineButton extends JLabel {
    private static final int w = 48, h = 48;
    private Border loweredBorder = StylesModern.LOWERED_BORDER;

    public ReadonlyMineButton (
        int x, int y, 
        Color backgroundColor,
        Color altBackgroundColor
    ) {
        setOpaque(true);

        // Set all these the same to force the layout to behave...
		var size = new Dimension(w, h);
		setPreferredSize(size);
		setMaximumSize(size);
        setMinimumSize(size);

        setBorder(loweredBorder);
        
        if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) {
			setBackground(backgroundColor);
		} else {
			setBackground(altBackgroundColor);
		}
    }
}