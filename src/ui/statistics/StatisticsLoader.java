package ui.statistics;

import events.IEventSubscriber;
import events.ShowStatisticsEvent;
import utils.ClassFactory;

public class StatisticsLoader {
    public StatisticsLoader(IEventSubscriber eventSubscriber) {
        eventSubscriber.subscribe(ShowStatisticsEvent.class, event -> {
            ClassFactory.create(StatisticsWindow.class).show(event);
        });
    }
}