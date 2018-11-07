package gui.events.handlers;

import gui.events.AboutEvent;
import javax.swing.JOptionPane;

public class AboutEventHandler implements IEventHandler<AboutEvent> {
    @Override
    public void execute(AboutEvent event) {
        JOptionPane.showMessageDialog(null, event.getMessage(), event.getTitle(), JOptionPane.PLAIN_MESSAGE);
    }
}