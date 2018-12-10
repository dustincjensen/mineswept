package gui.events.handlers;

import gui.events.IEventSubscriber;
import gui.events.ShowStatisticsEvent;

public class ShowStatisticsEventHandler implements IEventHandler<ShowStatisticsEvent> {
    private IEventSubscriber eventSubscriber;

    public ShowStatisticsEventHandler(IEventSubscriber subscriber) {
        eventSubscriber = subscriber;
    }

    @Override
    public void execute(ShowStatisticsEvent event) {
        eventSubscriber.notify(event);
    }
}