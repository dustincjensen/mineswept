package gui.events.handlers;

import gui.events.IEventSubscriber;
import gui.events.SetTimeCountEvent;

public class SetTimeCountEventHandler implements IEventHandler<SetTimeCountEvent> {
    private IEventSubscriber eventSubscriber;

    public SetTimeCountEventHandler(IEventSubscriber subscriber) {
        eventSubscriber = subscriber;
    }

    @Override
    public void execute(SetTimeCountEvent event) {
        eventSubscriber.notify(event);
    }
}