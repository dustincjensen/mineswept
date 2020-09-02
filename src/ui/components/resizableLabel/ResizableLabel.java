package ui.components.resizableLabel;

import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * https://stackoverflow.com/a/9312222/718285
 * Allows a resizable number for the MineButton dynamically.
 */
@SuppressWarnings("serial")
public class ResizableLabel extends JLabel {
    private static final int MIN_FONT_SIZE = 3;
    private static final int MAX_FONT_SIZE = 240;
    
    private Graphics graphics;

    public ResizableLabel(String text) {
        super(text);
        
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                adaptLabelFont(ResizableLabel.this);
            }
        });
    }

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.graphics = graphics;
    }

    private void adaptLabelFont(JLabel label) {
        if (graphics == null) {
            return;
        }

        // Get the label bounds and current font.
        // Set the font size to the minimum font size so we can increase it until
        // we have found the font size that best fits our label.
        var labelBounds = label.getBounds();
        var labelFont = label.getFont();
        var fontSize = MIN_FONT_SIZE;
        
        // Create 2 rectangles to contain the sizes of the new labels sizes we are
        // creating. This allows us to compare before and after to find if the current
        // font size or the next font size is the one we should use.
        var r1 = new Rectangle();
        var r2 = new Rectangle();

        while (fontSize < MAX_FONT_SIZE) {
            r1.setSize(getTextSize(label, labelFont.deriveFont(labelFont.getStyle(), fontSize)));
            r2.setSize(getTextSize(label, labelFont.deriveFont(labelFont.getStyle(), fontSize + 1)));

            // Had to modify this from the stack overflow. Contains was always false
            // because the width of the new rectangles was always zero.
            if (labelBounds.height >= r1.height && labelBounds.height <= r2.height) {
                break;
            }
            fontSize++;
        }

        setFont(labelFont.deriveFont(labelFont.getStyle(), fontSize));
        repaint();
    }

    private Dimension getTextSize(JLabel label, Font font) {
        graphics.setFont(font);
        var fontMetrics = graphics.getFontMetrics(font);
        var size = new Dimension();
        size.width = fontMetrics.stringWidth(label.getText());
        size.height = fontMetrics.getHeight();
        return size;
    }
}
