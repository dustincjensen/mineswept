package ui.layout.header.mineCount;

import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;

public class MineCountButtonFocusAdapter extends FocusAdapter {
    private MineCountButton button;

    public MineCountButtonFocusAdapter(MineCountButton button) {
        this.button = button;
    }

    public void focusGained(FocusEvent e) {
        button.setHintText();
    }

    public void focusLost(FocusEvent e) {
        button.setFormattedText();
    }
}