package gui.events.handlers;

import gui.events.ShowStatisticsEvent;
import gui.menu.StatisticsWindow;

public class ShowStatisticsEventHandler implements IEventHandler<ShowStatisticsEvent> {
    @Override
    public void execute(ShowStatisticsEvent event) {
        StatisticsWindow.show(true);
    }
}