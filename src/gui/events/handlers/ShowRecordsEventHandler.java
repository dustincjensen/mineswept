package gui.events.handlers;

import gui.events.ShowRecordsEvent;
import gui.records.RecordWindow;

public class ShowRecordsEventHandler implements IEventHandler<ShowRecordsEvent> {
    private RecordWindow recordWindow;

    public ShowRecordsEventHandler(RecordWindow window) {
        recordWindow = window;
    }

    @Override
    public void execute(ShowRecordsEvent event) {
        recordWindow.show(event.show);
    }
}