package events.handlers;

import events.IEventSubscriber;
import events.ResetStatisticsEvent;
import services.RecordsService;
import services.StatisticsService;

public class ResetStatisticsEventHandler implements IEventHandler<ResetStatisticsEvent> {
    private StatisticsService statisticsService;
    private RecordsService recordsService;
    private IEventSubscriber eventSubscriber;

    public ResetStatisticsEventHandler(
        StatisticsService statisticsService,
        RecordsService recordsService,
        IEventSubscriber eventSubscriber
    ) {
        this.statisticsService = statisticsService;
        this.recordsService = recordsService;
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public void execute(ResetStatisticsEvent event) {
        // Reset records may return some default computer records to populate the board.
        event.records = recordsService.resetRecords(event.difficulty);
        event.stats = statisticsService.resetStatistics(event.difficulty);
        eventSubscriber.notify(event);
    }
}