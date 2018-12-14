package events.handlers;

import events.IEventSubscriber;
import events.ShowOptionsEvent;

public class ShowOptionsEventHandler implements IEventHandler<ShowOptionsEvent> {
    private IEventSubscriber eventSubscriber;

    public ShowOptionsEventHandler(IEventSubscriber subscriber) {
        eventSubscriber = subscriber;
    }

    @Override
    public void execute(ShowOptionsEvent event) {
        eventSubscriber.notify(event);
    }
}