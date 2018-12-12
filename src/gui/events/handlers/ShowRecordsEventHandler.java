package gui.events.handlers;

import gui.events.IEventSubscriber;
import gui.events.ShowRecordsEvent;
import services.RecordsService;

public class ShowRecordsEventHandler implements IEventHandler<ShowRecordsEvent> {
    private RecordsService recordsService;
    private IEventSubscriber eventSubscriber;
    
    public ShowRecordsEventHandler(RecordsService service, IEventSubscriber subscriber) {
        recordsService = service;
        eventSubscriber = subscriber;
    }

    @Override
    public void execute(ShowRecordsEvent event) {
        event.records = recordsService.getAllRecords();
        eventSubscriber.notify(event);
    }
}