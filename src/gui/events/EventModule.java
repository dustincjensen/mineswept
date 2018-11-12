package gui.events;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import gui.events.IEventPublisher;
import gui.events.handlers.*;
import gui.options.OptionWindow;
import gui.statistics.StatisticsWindow;
import java.util.List;

public class EventModule extends AbstractModule {
    @Override
    public void configure() {
    }

    @Singleton
    @Provides
    public IEventPublisher provideEventPublisher(List<IEventHandler> eventHandlers) {
        return new EventPublisher(eventHandlers);
    }

    @Provides
    public List<IEventHandler> provideEventHandlers(AboutEventHandler about, GetHintEventHandler getHint,
            PauseGameEventHandler pauseGame, QuitGameEventHandler quitGame, ResetGameEventHandler resetGame,
            ShowOptionsEventHandler showOptions, ShowRecordsEventHandler showRecords,
            ShowStatisticsEventHandler showStatistics) {
        return List.of(about, getHint, pauseGame, quitGame, resetGame, showOptions, showRecords, showStatistics);
    }

    @Provides
    public ShowOptionsEventHandler provideShowOptionsEventHandler(OptionWindow optionWindow) {
        return new ShowOptionsEventHandler(optionWindow);
    }

    @Provides
    public ShowStatisticsEventHandler provideShowStatisticsEventHandler(StatisticsWindow statisticsWindow) {
        return new ShowStatisticsEventHandler(statisticsWindow);
    }
}