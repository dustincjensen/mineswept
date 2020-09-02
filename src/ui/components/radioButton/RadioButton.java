package ui.components.radioButton;

import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import java.awt.Color;

@SuppressWarnings("serial")
public class RadioButton extends JRadioButton {
    public RadioButton(String text, ImageIcon defaultIcon, ImageIcon selectedIcon, ActionListener actionListener) {
        super(text);
        setOpaque(false);
        setFocusPainted(false);
        setForeground(Color.decode("#ffffff"));
        setIcon(defaultIcon);
        setSelectedIcon(selectedIcon);
        addActionListener(actionListener);
    }
}