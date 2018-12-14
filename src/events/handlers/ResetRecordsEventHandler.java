package events.handlers;

import events.IEventSubscriber;
import events.ResetRecordsEvent;
import services.RecordsService;

public class ResetRecordsEventHandler implements IEventHandler<ResetRecordsEvent> {
    private RecordsService recordsService;
    private IEventSubscriber eventSubscriber;

    public ResetRecordsEventHandler(RecordsService records, IEventSubscriber subscriber) {
        recordsService = records;
        eventSubscriber = subscriber;
    }

    @Override
    public void execute(ResetRecordsEvent event) {
        var records = recordsService.resetRecords(event.difficulty);
        // Reset records may return some default computer records to populate the board.
        event.records = records;
        eventSubscriber.notify(event);
    }
}