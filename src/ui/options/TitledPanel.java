package ui.options;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import ui.utils.JLabelFactory;

@SuppressWarnings("serial")
public class TitledPanel extends Box {
    public TitledPanel(String title, JComponent... content) {
        super(BoxLayout.Y_AXIS);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(titlePanel(title));

        for (var component : content) {
            add(component);
        }
    }

    private JPanel titlePanel(String title) {
        var titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.decode("#111111"));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.add(JLabelFactory.create(title), BorderLayout.LINE_START);
        return titlePanel;
    }
}