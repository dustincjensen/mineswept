package ui.options;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class OptionsWindowHandler extends WindowAdapter {
    private OptionsWindow window;

    public OptionsWindowHandler(OptionsWindow window) {
        this.window = window;
    }

	@Override
    public void windowClosing(WindowEvent e) {
        window.closed();
	}
}
