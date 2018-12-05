

package gui;

import gui.events.IEventPublisher;
import gui.events.QuitGameEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainWindowHandler implements WindowListener {
    private IEventPublisher eventPublisher;

    public MainWindowHandler(IEventPublisher publisher) {
        eventPublisher = publisher;
    }

    public void windowActivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {
		eventPublisher.publish(new QuitGameEvent());
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
}
