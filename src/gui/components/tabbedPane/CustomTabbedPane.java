package gui.components.tabbedPane;

import gui.HexToRgb;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

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
        ActionListener action = evt -> {
            selectedIndex = names.indexOf(tabName);
            for (int i = 0; i < tabHeader.getComponents().length; i++) {
                CustomTabButton c = (CustomTabButton)tabHeader.getComponent(i);
                c.setSelected(selectedIndex == i);
            }
            ((CardLayout) tabBody.getLayout()).show(tabBody, tabName);
        };

        names.add(tabName);
        tabHeader.add(new CustomTabButton(tabName, action, names.indexOf(tabName) == 0));
        tabBody.add(component, tabName);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    private JPanel tabHeader() {
        tabHeader = new JPanel();
        tabHeader.setBackground(HexToRgb.convert("#333333"));
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