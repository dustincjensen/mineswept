package gui.events.handlers;

import gui.events.IEventSubscriber;
import gui.events.ShowOptionsEvent;

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