package gui.events.handlers;

import gui.events.ShowStatisticsEvent;
import gui.statistics.StatisticsWindow;

public class ShowStatisticsEventHandler implements IEventHandler<ShowStatisticsEvent> {
    private StatisticsWindow statisticsWindow;

    public ShowStatisticsEventHandler(StatisticsWindow window) {
        statisticsWindow = window;
    }

    @Override
    public void execute(ShowStatisticsEvent event) {
        statisticsWindow.show(event.show);
    }
}