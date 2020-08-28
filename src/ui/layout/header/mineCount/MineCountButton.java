package ui.layout.header.mineCount;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import ui.components.button.LightButton;
import ui.utils.FontChange;

@SuppressWarnings("serial")
public class MineCountButton extends LightButton {
    private int mineCount;

    public MineCountButton(int mineCount, ImageIcon bombHintIcon, ActionListener listener) {
        super("", true, listener);
        this.mineCount = mineCount;

        setFormattedText();
        setIcon(bombHintIcon);
        setIconTextGap(20);
        setHorizontalAlignment(SwingConstants.LEFT);
		setHorizontalTextPosition(SwingConstants.RIGHT);
        setPreferredSize(new Dimension(156, getHeight()));
        FontChange.setFont(this, 24);

        addFocusListener(new MineCountButtonFocusAdapter(this));
        addMouseListener(new MineCountButtonMouseAdapter(this));
    }

    public void setMineCount(int value) {
        if (mineCount != value) {
            mineCount = value;
            setFormattedText();
        }
    }

    public void setHintText() {
        setText(String.format("%1$7s", "Hint"));
    }

    public void setFormattedText() {
        setText(String.format("%1$8s", String.format("%02d", mineCount)));
    }
}

