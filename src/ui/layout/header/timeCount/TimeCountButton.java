package ui.layout.header.timeCount;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import ui.components.button.LightButton;
import ui.utils.FontChange;

@SuppressWarnings("serial")
public class TimeCountButton extends LightButton {
    private int lastTime;
    private boolean isHovering;

    public TimeCountButton(int time, ImageIcon clockIcon, ActionListener listener) {
        super("", true, listener);
        this.lastTime = time;
        this.isHovering = false;

        setFormattedText();
        setIcon(clockIcon);
		setIconTextGap(20);
		setHorizontalAlignment(SwingConstants.RIGHT);
		setHorizontalTextPosition(SwingConstants.LEFT);
		setPreferredSize(new Dimension(156, getHeight()));
		FontChange.setFont(this, 24);

		addFocusListener(new TimeCountButtonFocusAdapter(this));
		addMouseListener(new TimeCountButtonMouseAdapter(this));
    }

    public void setTime(int value) {
        if (lastTime != value) {
            lastTime = value;
            setFormattedText();
        }
    }

    public void setHovering(boolean value) {
        isHovering = value;
    }

    public void setPauseText() {
        setText("Pause");
    }

    public void setFormattedText() {
        if (!isFocusOwner() && !isHovering) {
            setText(String.format("%-7s", String.format("%03d", lastTime)));
        }
    }
}