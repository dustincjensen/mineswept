package ui.components.comboBox;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

@SuppressWarnings("serial")
public class DefaultComboBoxRenderer<T> extends JTextField implements ListCellRenderer<T> {
    public DefaultComboBoxRenderer() {
        setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }

    @Override
    public Component getListCellRendererComponent(
        JList<? extends T> list,
        T value,
        int index,
        boolean isSelected,
        boolean cellHasFocus
    ) {
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setText(value.toString());
        return this;
    }
}