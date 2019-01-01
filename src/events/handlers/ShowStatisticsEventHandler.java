package events.handlers;

import events.IEventSubscriber;
import events.ShowStatisticsEvent;
import services.StatisticsService;

public class ShowStatisticsEventHandler implements IEventHandler<ShowStatisticsEvent> {
    private StatisticsService _statisticsService;
    private IEventSubscriber _eventSubscriber;

    public ShowStatisticsEventHandler(StatisticsService statisticsService, IEventSubscriber eventSubscriber) {
        _statisticsService = statisticsService;
        _eventSubscriber = eventSubscriber;
    }

    @Override
    public void execute(ShowStatisticsEvent event) {
        event.stats = _statisticsService.getStatistics();
        _eventSubscriber.notify(event);
    }
}