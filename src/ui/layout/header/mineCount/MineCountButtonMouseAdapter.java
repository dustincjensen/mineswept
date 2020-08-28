package ui.layout.header.mineCount;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class MineCountButtonMouseAdapter extends MouseAdapter {
    private MineCountButton button;

    public MineCountButtonMouseAdapter(MineCountButton button) {
        this.button = button;
    }

    public void mouseEntered(MouseEvent evt) {
        button.setHintText();
    }

    public void mouseExited(MouseEvent evt) {
        if (!button.isFocusOwner()) {
            button.setFormattedText();
        }
    }
}