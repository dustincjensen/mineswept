package ui.components.comboBox;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import ui.utils.HexToRgb;

public class DefaultComboBoxUi extends BasicComboBoxUI {
    @Override
    protected JButton createArrowButton() {
        var button = new BasicArrowButtonModified(
            BasicArrowButton.SOUTH, 
            HexToRgb.convert("#333333"), 
            HexToRgb.convert("#333333"),
            HexToRgb.convert("#666666"),
            HexToRgb.convert("#333333"));
        button.setName("ComboBox.arrowButton");
        return button;
    }

    @Override
    protected ComboPopup createPopup() {
        var popup = new BasicComboPopup(comboBox);
        popup.setBorder(BorderFactory.createLineBorder(HexToRgb.convert("#444444")));
        
        var list = popup.getList();
        list.setSelectionBackground(HexToRgb.convert("#444444"));
        list.setSelectionForeground(HexToRgb.convert("#ffffff"));

        return popup;
    }
}