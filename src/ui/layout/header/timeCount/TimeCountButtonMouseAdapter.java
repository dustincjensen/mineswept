package ui.layout.header.timeCount;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class TimeCountButtonMouseAdapter extends MouseAdapter {
    private TimeCountButton button;

    public TimeCountButtonMouseAdapter(TimeCountButton button) {
        this.button = button;
    }

    public void mouseEntered(MouseEvent evt) {
        button.setHovering(true);
        button.setPauseText();
    }

    public void mouseExited(MouseEvent evt) {
        button.setHovering(false);
        button.setFormattedText();
    }
}