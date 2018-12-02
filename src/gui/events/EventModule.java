package gui.events;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import gui.events.IEventPublisher;
import gui.events.handlers.*;
import gui.options.OptionWindow;
import gui.panel.header.TimeCount;
import gui.records.RecordWindow;
import gui.statistics.StatisticsWindow;
import java.util.List;
import logic.game.ClockTimer;
import logic.game.GameState;
import logic.game.HintService;


public class EventModule extends AbstractModule {
    @Override
    public void configure() {
    }

    @Singleton
    @Provides
    public IEventPublisher provideEventPublisher(List<IEventHandler> eventHandlers) {
        return new EventPublisher(eventHandlers);
    }

    @Singleton
    @Provides
    public IEventSubscriber provideEventSubscriber() {
        return new EventSubscriber();
    }

    @Provides
    public List<IEventHandler> provideEventHandlers(
        AboutEventHandler about, 
        GetHintEventHandler getHint,
        PauseGameEventHandler pauseGame,
        QuitGameEventHandler quitGame,
        ResetGameEventHandler resetGame,
        SetResetButtonIconEventHandler setResetButtonIcon,
        SetTimeCountEventHandler setTimeCount,
        ShowOptionsEventHandler showOptions,
        ShowRecordsEventHandler showRecords,
        ShowStatisticsEventHandler showStatistics
    ) {
        return List.of(
            about,
            getHint,
            pauseGame,
            quitGame,
            resetGame,
            setResetButtonIcon,
            setTimeCount,
            showOptions,
            showRecords,
            showStatistics
        );
    }

    @Provides
    public GetHintEventHandler provideGetHintEventHandler(GameState gameState, HintService hintService, IEventSubscriber subscriber) {
        return new GetHintEventHandler(gameState, hintService, subscriber);
    }

    @Provides
    public PauseGameEventHandler providePauseGameEventHandler(
        GameState gameState,
        ClockTimer clockTimer,
        IEventSubscriber subscriber
    ) {
        return new PauseGameEventHandler(gameState, clockTimer, subscriber);
    }

    @Provides
    public ResetGameEventHandler provideResetGameEventHandler(
        GameState gameState,
        ClockTimer clockTimer,
        IEventSubscriber subscriber
    ) {
        return new ResetGameEventHandler(gameState, clockTimer, subscriber);
    }

    @Provides
    public SetResetButtonIconEventHandler provideSetResetButtonIconEventHandler(IEventSubscriber subscriber) {
        return new SetResetButtonIconEventHandler(subscriber);
    }

    @Provides
    public SetTimeCountEventHandler provideSetTimeCountEventHandler(TimeCount timeCount) {
        return new SetTimeCountEventHandler(timeCount);
    }

    @Provides
    public ShowOptionsEventHandler provideShowOptionsEventHandler(OptionWindow optionWindow) {
        return new ShowOptionsEventHandler(optionWindow);
    }

    @Provides
    public ShowRecordsEventHandler provideShowRecordsEventHandler(RecordWindow recordWindow) {
        return new ShowRecordsEventHandler(recordWindow);
    }

    @Provides
    public ShowStatisticsEventHandler provideShowStatisticsEventHandler(StatisticsWindow statisticsWindow) {
        return new ShowStatisticsEventHandler(statisticsWindow);
    }
}