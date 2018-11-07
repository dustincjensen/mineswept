package gui.events.handlers;

import gui.events.QuitGameEvent;
import logic.files.FileManagement;

public class QuitGameEventHandler implements IEventHandler<QuitGameEvent> {
    @Override
    public void execute(QuitGameEvent event) {
        // TODO Show Save Game?
        FileManagement.saveFiles();
        System.exit(0);
    }
}