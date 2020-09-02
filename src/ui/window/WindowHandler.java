package ui.window;

import events.IEventPublisher;
import events.QuitGameEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class WindowHandler extends WindowAdapter {
    private IEventPublisher eventPublisher;

    public WindowHandler(IEventPublisher publisher) {
        eventPublisher = publisher;
    }

	@Override
    public void windowClosing(WindowEvent e) {
		eventPublisher.publish(new QuitGameEvent());
	}
}
