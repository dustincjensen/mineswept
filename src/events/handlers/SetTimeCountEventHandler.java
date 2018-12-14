package events.handlers;

import events.IEventSubscriber;
import events.SetTimeCountEvent;

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