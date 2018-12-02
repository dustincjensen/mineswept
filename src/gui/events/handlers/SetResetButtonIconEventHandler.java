package gui.events.handlers;

import gui.events.IEventSubscriber;
import gui.events.SetResetButtonIconEvent;

public class SetResetButtonIconEventHandler implements IEventHandler<SetResetButtonIconEvent> {
    private IEventSubscriber eventSubscriber;

    public SetResetButtonIconEventHandler(IEventSubscriber subscriber) {
        eventSubscriber = subscriber;
    }

    @Override
    public void execute(SetResetButtonIconEvent event) {
        eventSubscriber.notify(event);
    }
}