package ui.components.radioButton;

import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import ui.utils.HexToRgb;

@SuppressWarnings("serial")
public class RadioButton extends JRadioButton {
    public RadioButton(String text, ImageIcon defaultIcon, ImageIcon selectedIcon, ActionListener actionListener) {
        super(text);
        setOpaque(false);
        setFocusPainted(false);
        setForeground(HexToRgb.convert("#ffffff"));
        setIcon(defaultIcon);
        setSelectedIcon(selectedIcon);
        addActionListener(actionListener);
    }
}