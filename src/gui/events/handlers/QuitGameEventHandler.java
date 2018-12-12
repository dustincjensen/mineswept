package gui.events.handlers;

import gui.events.QuitGameEvent;

public class QuitGameEventHandler implements IEventHandler<QuitGameEvent> {
    @Override
    public void execute(QuitGameEvent event) {
        // TODO Show Save Game?
        // FileService.saveFiles();
        System.exit(0);
    }
}