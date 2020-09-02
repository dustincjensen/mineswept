package ui.utils;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class JLabelFactory {
    /**
     * Create a new JLabel with the text.
     * 
     * @param text the text of the JLabel.
     * @return A new JLabel with the appropriate settings.
     */
    public static JLabel create(String text) {
        return create(text, SwingConstants.LEFT);
    }

    /**
     * Create a new JLabel with the text and alignment.
     * 
     * @param text the text of the JLabel.
     * @param alignment the horizontal alignment of the JLabel.
     * @return A new JLabel with the appropriate settings.
     */
    public static JLabel create(String text, int alignment) {
        var label = new JLabel(text);
        label.setHorizontalAlignment(alignment);
		label.setForeground(Color.decode("#ffffff"));
        return label;
    }
}