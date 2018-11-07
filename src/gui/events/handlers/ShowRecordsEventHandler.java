package gui.events.handlers;

import gui.events.ShowRecordsEvent;
import gui.menu.RecordWindow;

public class ShowRecordsEventHandler implements IEventHandler<ShowRecordsEvent> {
    @Override
    public void execute(ShowRecordsEvent event) {
        RecordWindow.show(event.show);
    }
}