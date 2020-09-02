package ui.options;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import models.options.BorderType;
import ui.components.comboBox.DefaultComboBox;
import ui.utils.JLabelFactory;

@SuppressWarnings("serial")
public class BorderPanel extends JPanel {
    private static final BorderType[] RAISED_OPTIONS = { 
        BorderType.BEVEL_RAISED,
        BorderType.CLASSIC_BEVEL_RAISED,
        BorderType.EMPTY
    };
    private static final BorderType[] LOWERED_OPTIONS = {
        BorderType.BEVEL_LOWERED,
		BorderType.CLASSIC_BEVEL_LOWERED,
        BorderType.EMPTY
    };
    
    private DefaultComboBox<BorderType> raisedBorder, loweredBorder;

    public BorderPanel(BorderType initialRaisedBorder, BorderType initialLoweredBorder) {
        super(new GridLayout(0, 2, 10, 0));
        setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
		setOpaque(false);

		raisedBorder = new DefaultComboBox<BorderType>(RAISED_OPTIONS);
		raisedBorder.setSelectedItem(initialRaisedBorder);
        add(wrapper("Default Border", raisedBorder));

        loweredBorder = new DefaultComboBox<BorderType>(LOWERED_OPTIONS);
		loweredBorder.setSelectedItem(initialLoweredBorder);
		add(wrapper("Clicked Border", loweredBorder));
    }

    public void setRaisedBorder(BorderType type) {
        raisedBorder.setSelectedItem(type);
    }

    public Object getRaisedBorder() {
        return raisedBorder.getSelectedItem();
    }

    public void setLoweredBorder(BorderType type) {
        loweredBorder.setSelectedItem(type);
    }

    public Object getLoweredBorder() {
        return loweredBorder.getSelectedItem();
    }

    private JPanel wrapper(String title, DefaultComboBox<BorderType> comboBox) {
        var panel = new JPanel(new GridLayout(0, 1));
		panel.setOpaque(false);
		panel.add(JLabelFactory.create(title));
        panel.add(comboBox);
        return panel;
    }
}