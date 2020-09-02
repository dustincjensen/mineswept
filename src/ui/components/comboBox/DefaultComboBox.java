package ui.components.comboBox;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import java.awt.Color;

@SuppressWarnings("serial")
public class DefaultComboBox<T> extends JComboBox<T> {
    public DefaultComboBox(T[] array) {
        super(array);

        setBorder(BorderFactory.createLineBorder(Color.decode("#444444")));
        setBackground(Color.decode("#333333"));
        setForeground(Color.decode("#ffffff"));
        setRenderer(new DefaultComboBoxRenderer<T>());
        setUI(new DefaultComboBoxUi());
    }
}