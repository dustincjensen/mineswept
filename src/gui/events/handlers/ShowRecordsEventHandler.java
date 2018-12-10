package gui.events.handlers;

import gui.events.IEventSubscriber;
import gui.events.ShowRecordsEvent;
import gui.records.RecordWindow;
import logic.files.Records;

public class ShowRecordsEventHandler implements IEventHandler<ShowRecordsEvent> {
    private Records records;
    private IEventSubscriber eventSubscriber;
    
    public ShowRecordsEventHandler(Records recordsRef, IEventSubscriber subscriber) {
        records = recordsRef;
        eventSubscriber = subscriber;
    }

    @Override
    public void execute(ShowRecordsEvent event) {
        event.records = records.getAllRecords();
        eventSubscriber.notify(event);
    }
}