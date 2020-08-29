package events;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import events.handlers.*;
import java.util.List;
import services.HintService;
import services.MineRevealService;
import services.RecordsService;
import services.StatisticsService;
import state.GameState;
import ui.ClockTimer;

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
        MineClickedEventHandler mineClicked,
        PauseGameEventHandler pauseGame,
        QuitGameEventHandler quitGame,
        ResetGameEventHandler resetGame,
        ResetStatisticsEventHandler resetStatistics,
        SetResetButtonIconEventHandler setResetButtonIcon,
        SetTimeCountEventHandler setTimeCount,
        ShowOptionsEventHandler showOptions,
        ShowStatisticsEventHandler showStatistics,
        StartClockTimerEventHandler startClockTimer,
        StopClockTimerEventHandler stopClockTimer
    ) {
        return List.of(
            about,
            getHint,
            mineClicked,
            pauseGame,
            quitGame,
            resetGame,
            resetStatistics,
            setResetButtonIcon,
            setTimeCount,
            showOptions,
            showStatistics,
            startClockTimer,
            stopClockTimer
        );
    }

    @Provides
    public GetHintEventHandler provideGetHintEventHandler(GameState gameState, HintService hintService, IEventSubscriber subscriber) {
        return new GetHintEventHandler(gameState, hintService, subscriber);
    }

    @Provides
    public MineClickedEventHandler provideMineClickedEventHandler(
        GameState state,
        MineRevealService service,
        RecordsService records,
        StatisticsService statistics,
        ClockTimer timer,
        IEventSubscriber subscriber
    ) {
        return new MineClickedEventHandler(state, service, records, statistics, timer, subscriber);
    }

    @Provides
    public PauseGameEventHandler providePauseGameEventHandler(
        GameState gameState,
        IEventSubscriber subscriber
    ) {
        return new PauseGameEventHandler(gameState, subscriber);
    }

    @Provides
    public ResetGameEventHandler provideResetGameEventHandler(
        GameState gameState,
        IEventSubscriber subscriber
    ) {
        return new ResetGameEventHandler(gameState, subscriber);
    }

    @Provides
    public ResetStatisticsEventHandler provideResetStatisticsEventHandler(
        StatisticsService statisticsService,
        RecordsService recordsService,
        IEventSubscriber eventSubscriber
    ) {
        return new ResetStatisticsEventHandler(statisticsService, recordsService, eventSubscriber);
    }

    @Provides
    public SetResetButtonIconEventHandler provideSetResetButtonIconEventHandler(IEventSubscriber subscriber) {
        return new SetResetButtonIconEventHandler(subscriber);
    }

    @Provides
    public SetTimeCountEventHandler provideSetTimeCountEventHandler(IEventSubscriber subscriber) {
        return new SetTimeCountEventHandler(subscriber);
    }

    @Provides
    public ShowOptionsEventHandler provideShowOptionsEventHandler(IEventSubscriber subscriber) {
        return new ShowOptionsEventHandler(subscriber);
    }

    @Provides
    public ShowStatisticsEventHandler provideShowStatisticsEventHandler(
        StatisticsService statisticsService,
        RecordsService recordsService,
        IEventSubscriber subscriber
    ) {
        return new ShowStatisticsEventHandler(statisticsService, recordsService, subscriber);
    }

    @Provides
    public StartClockTimerEventHandler provideStartClockTimerEventHandler(IEventSubscriber subscriber) {
        return new StartClockTimerEventHandler(subscriber);
    }

    @Provides
    public StopClockTimerEventHandler provideStopClockTimerEventHandler(IEventSubscriber subscriber) {
        return new StopClockTimerEventHandler(subscriber);
    }
}