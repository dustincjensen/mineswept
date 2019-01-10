package events.handlers;

import events.IEventSubscriber;
import events.StartClockTimerEvent;

public class StartClockTimerEventHandler implements IEventHandler<StartClockTimerEvent> {
    private IEventSubscriber eventSubscriber;

    public StartClockTimerEventHandler(IEventSubscriber subscriber) {
        eventSubscriber = subscriber;
    }

    @Override
    public void execute(StartClockTimerEvent event) {
        eventSubscriber.notify(event);
    }
}