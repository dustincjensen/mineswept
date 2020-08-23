package ui.components.radioButton;

import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

public class RadioButtonFactory {
    private ImageIcon defaultIcon;
    private ImageIcon selectedIcon;

    public RadioButtonFactory(
        ImageIcon defaultIcon,
        ImageIcon selectedIcon
    ) {
        this.defaultIcon = defaultIcon;
        this.selectedIcon = selectedIcon;
    }

    public RadioButton create(String text) {
        return new RadioButton(text, defaultIcon, selectedIcon, null);
    }

    public RadioButton create(String text, ActionListener actionListener) {
        return new RadioButton(text, defaultIcon, selectedIcon, actionListener);
    }
}