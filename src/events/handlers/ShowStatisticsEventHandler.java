package events.handlers;

import events.IEventSubscriber;
import events.ShowStatisticsEvent;
import services.RecordsService;
import services.StatisticsService;

public class ShowStatisticsEventHandler implements IEventHandler<ShowStatisticsEvent> {
    private StatisticsService statisticsService;
    private RecordsService recordsService;
    private IEventSubscriber eventSubscriber;

    public ShowStatisticsEventHandler(
        StatisticsService statisticsService,
        RecordsService recordsService,
        IEventSubscriber eventSubscriber
    ) {
        this.statisticsService = statisticsService;
        this.recordsService = recordsService;
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public void execute(ShowStatisticsEvent event) {
        event.stats = statisticsService.getStatistics();
        event.records = recordsService.getAllRecords();
        eventSubscriber.notify(event);
    }
}