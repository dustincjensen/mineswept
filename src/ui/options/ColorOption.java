package ui.options;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ui.components.button.PrimaryButton;
import ui.utils.ColorConverter;
import ui.utils.JLabelFactory;

@SuppressWarnings("serial")
public class ColorOption extends Box {
    private JLabel colorSwatch;
    private Color selectedColor;

    public ColorOption(Color base, String colorName) {
        super(BoxLayout.X_AXIS);
        this.selectedColor = base;
        
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
        add(colorSwatchPanel(base));
        add(Box.createHorizontalStrut(5));
        add(labelAndButtonPanel(base, colorName));
    }

    /**
     * Gets the selected color.
     * 
     * @return the selected color.
     */
    public Color getSelectedColor() {
        return selectedColor;
    }

    /**
     * Set the selected color and update the
     * swatch color to match.
     * 
     * @param color the new color.
     */
    public void setSelectedColor(Color color) {
        selectedColor = color;
        colorSwatch.setBackground(selectedColor);
    }

    /**
     * Returns a hex string representation of the color.
     * 
     * @return the hex string of the color.
     */
    public String getSaveableColor() {
        return ColorConverter.convertBack(selectedColor);
    }

    private JPanel colorSwatchPanel(Color base) {
        colorSwatch = new JLabel();
        colorSwatch.setPreferredSize(new Dimension(40, 40));
        colorSwatch.setOpaque(true);
        colorSwatch.setBackground(base);

        var panel = new JPanel(new GridLayout(0, 1));
        panel.add(colorSwatch);
        panel.setBorder(BorderFactory.createLineBorder(Color.decode("#444444"), 1));
        return panel;
    }

    private Box labelAndButtonPanel(Color base, String colorName) {
        var labelAndButton = new Box(BoxLayout.Y_AXIS);
		labelAndButton.setOpaque(false);
		labelAndButton.add(JLabelFactory.create(colorName));
		labelAndButton.add(Box.createVerticalStrut(5));

		labelAndButton.add(new PrimaryButton("Select New Color", evt -> {
			var newColor = JColorChooser.showDialog(null, "Select Base Color", base);
			if (newColor != null) {
				colorSwatch.setBackground(newColor);
				selectedColor = newColor;
			}
        }));
        
        return labelAndButton;
    }
}