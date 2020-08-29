package ui.components.tabbedPane;

import java.awt.Color;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CustomTabbedPane extends JPanel {
    private ArrayList<String> names;
    private int selectedIndex;
    private JPanel tabHeader;
    private JPanel tabBody;

    public CustomTabbedPane() {
        names = new ArrayList<String>();
        selectedIndex = 0;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(tabHeader());
        add(tabBody());
    }

    public void add(String tabName, JComponent component) {
        names.add(tabName);
        tabHeader.add(new CustomTabButton(
            tabName,
            evt -> setSelectedIndex(tabName),
            names.indexOf(tabName) == 0)
        );
        tabBody.add(component, tabName);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(String tabName) {
        selectedIndex = names.indexOf(tabName);
        for (var i = 0; i < tabHeader.getComponents().length; i++) {
            var c = (CustomTabButton)tabHeader.getComponent(i);
            c.setSelected(selectedIndex == i);
        }
        ((CardLayout) tabBody.getLayout()).show(tabBody, tabName);
    }

    private JPanel tabHeader() {
        tabHeader = new JPanel();
        tabHeader.setBackground(Color.decode("#333333"));
        tabHeader.setLayout(new GridLayout(1, 0));
        tabHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        tabHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        return tabHeader;
    }

    private JComponent tabBody() {
        tabBody = new JPanel();
        tabBody.setLayout(new CardLayout());
        return tabBody;
    }
}
