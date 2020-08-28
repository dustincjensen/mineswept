package ui.components.comboBox;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import ui.utils.HexToRgb;

@SuppressWarnings("serial")
public class DefaultComboBox<T> extends JComboBox<T> {
    public DefaultComboBox(T[] array) {
        super(array);

        setBorder(BorderFactory.createLineBorder(HexToRgb.convert("#444444")));
        setBackground(HexToRgb.convert("#333333"));
        setForeground(HexToRgb.convert("#ffffff"));
        setRenderer(new DefaultComboBoxRenderer<T>());
        setUI(new DefaultComboBoxUi());
    }
}