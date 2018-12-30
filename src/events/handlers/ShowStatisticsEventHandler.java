package events.handlers;

import events.IEventSubscriber;
import events.ShowStatisticsEvent;
import models.statistics.AllStats;
import models.statistics.LongTermStats;

public class ShowStatisticsEventHandler implements IEventHandler<ShowStatisticsEvent> {
    private IEventSubscriber eventSubscriber;

    public ShowStatisticsEventHandler(IEventSubscriber subscriber) {
        eventSubscriber = subscriber;
    }

    @Override
    public void execute(ShowStatisticsEvent event) {
        // TODO temporary
        var all = new AllStats();
        all.easy = new LongTermStats();
        all.easy.gamesPlayed = 15;
        all.easy.gamesWon = 11;
        all.easy.gamesLost = 4;

        all.medium = new LongTermStats();
        all.medium.gamesPlayed = 5;
        all.medium.gamesWon = 1;
        all.medium.gamesLost = 4;

        all.hard = new LongTermStats();
        all.hard.gamesPlayed = 155;
        all.hard.gamesWon = 11;
        all.hard.gamesLost = 144;
        
        event.stats = all;

        eventSubscriber.notify(event);
    }
}