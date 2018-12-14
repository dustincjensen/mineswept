package events.handlers;

import events.AboutEvent;
import javax.swing.JOptionPane;

public class AboutEventHandler implements IEventHandler<AboutEvent> {
    @Override
    public void execute(AboutEvent event) {
        // TODO we should not be creating a dialog from the event handlers...
        JOptionPane.showMessageDialog(null, event.getMessage(), event.getTitle(), JOptionPane.PLAIN_MESSAGE);
    }
}