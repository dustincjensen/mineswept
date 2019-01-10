package events.handlers;

import events.IEventSubscriber;
import events.StopClockTimerEvent;

public class StopClockTimerEventHandler implements IEventHandler<StopClockTimerEvent> {
    private IEventSubscriber eventSubscriber;

    public StopClockTimerEventHandler(IEventSubscriber subscriber) {
        eventSubscriber = subscriber;
    }

    @Override
    public void execute(StopClockTimerEvent event) {
        eventSubscriber.notify(event);
    }
}