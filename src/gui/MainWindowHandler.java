

package gui;

import gui.events.IEventPublisher;
import gui.events.QuitGameEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class MainWindowHandler extends WindowAdapter {
    private IEventPublisher eventPublisher;

    public MainWindowHandler(IEventPublisher publisher) {
        eventPublisher = publisher;
    }

	@Override
    public void windowClosing(WindowEvent e) {
		eventPublisher.publish(new QuitGameEvent());
	}
}
