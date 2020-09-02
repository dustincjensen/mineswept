package events.handlers;

import events.AboutEvent;
import events.IEventSubscriber;

public class AboutEventHandler implements IEventHandler<AboutEvent> {
    private IEventSubscriber eventSubscriber;

    public AboutEventHandler(IEventSubscriber eventSubscriber) {
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public void execute(AboutEvent event) {
        eventSubscriber.notify(event);
    }
}
