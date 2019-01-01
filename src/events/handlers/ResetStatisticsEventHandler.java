package events.handlers;

import events.IEventSubscriber;
import events.ResetStatisticsEvent;
import services.StatisticsService;

public class ResetStatisticsEventHandler implements IEventHandler<ResetStatisticsEvent> {
    private StatisticsService statisticsService;
    private IEventSubscriber eventSubscriber;

    public ResetStatisticsEventHandler(StatisticsService statisticsService, IEventSubscriber eventSubscriber) {
        this.statisticsService = statisticsService;
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public void execute(ResetStatisticsEvent event) {
        event.stats = statisticsService.resetStatistics();
        eventSubscriber.notify(event);
    }
}