package events.handlers;

import events.QuitGameEvent;

public class QuitGameEventHandler implements IEventHandler<QuitGameEvent> {
    @Override
    public void execute(QuitGameEvent event) {
        // TODO Show Save Game?
        System.exit(0);
    }
}