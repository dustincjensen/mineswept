package ui.components.comboBox;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.Color;

public class DefaultComboBoxUi extends BasicComboBoxUI {
    @Override
    protected JButton createArrowButton() {
        var button = new BasicArrowButtonModified(
            BasicArrowButton.SOUTH, 
            Color.decode("#333333"), 
            Color.decode("#333333"),
            Color.decode("#666666"),
            Color.decode("#333333"));
        button.setName("ComboBox.arrowButton");
        return button;
    }

    @Override
    protected ComboPopup createPopup() {
        var popup = new BasicComboPopup(comboBox);
        popup.setBorder(BorderFactory.createLineBorder(Color.decode("#444444")));
        
        var list = popup.getList();
        list.setSelectionBackground(Color.decode("#444444"));
        list.setSelectionForeground(Color.decode("#ffffff"));

        return popup;
    }
}