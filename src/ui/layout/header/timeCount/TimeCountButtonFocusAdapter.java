package ui.layout.header.timeCount;

import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;

public class TimeCountButtonFocusAdapter extends FocusAdapter {
    private TimeCountButton button;

    public TimeCountButtonFocusAdapter(TimeCountButton button) {
        this.button = button;
    }

    public void focusGained(FocusEvent e) {
        button.setPauseText();
    }

    public void focusLost(FocusEvent e) {
        button.setFormattedText();
    }
}