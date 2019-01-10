package ui;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import events.IEventPublisher;
import events.IEventSubscriber;
import services.OptionsService;
import state.GameState;
import ui.main.MainModule;
import ui.menu.MenuModule;
import ui.options.OptionsModule;
import ui.layout.header.HeaderModule;
import ui.layout.header.HeaderLayout;
import ui.layout.body.BodyLayout;
import ui.layout.body.MineButton;
import ui.layout.body.MinePanel;
import ui.layout.body.PausePanel;
import ui.records.RecordsModule;
import ui.statistics.StatisticsModule;

public class UiModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new MainModule());
        install(new MenuModule());
        install(new OptionsModule());
        install(new RecordsModule());
        install(new StatisticsModule());
        
        // TODO this one might be redone...
        install(new HeaderModule());
    }

    // TODO this should be moved into a different module?
    @Singleton
    @Provides
    public ClockTimer provideClockTimer(IEventPublisher eventPublisher, IEventSubscriber eventSubscriber) {
        return new ClockTimer(eventPublisher, eventSubscriber);
    }

    // TODO this should be moved into a different module?
    @Singleton
    @Provides
    public ResourceLoader provideResourceLoader() {
        return new ResourceLoader();
    }

    // TODO move this into it's own module...
    @Provides
    public PausePanel providePausePanel(IEventPublisher publisher) {
        return new PausePanel(publisher);
    }

    // TODO move this into it's own module...
    @Provides
    public MinePanel provideMinePanel(GameState state, IEventSubscriber subscriber) {
        return new MinePanel(
            state.getCurrentPuzzleHeight(),
            state.getCurrentPuzzleWidth(),
            state.getCurrentPuzzleMineCount(),
            subscriber);
    }

    @Provides
    public BodyLayout provideBodyLayout(
        HeaderLayout header,
        MinePanel mine,
        PausePanel pause,
        IEventSubscriber subscriber
    ) {
        return new BodyLayout(header, mine, pause, subscriber);
    }

    // TODO move this into it's own module?
    @Provides
    public MineButton provideMineButton(
        OptionsService options, 
        GameState gameState,
        ResourceLoader loader,
        IEventPublisher publisher,
        IEventSubscriber subscriber
    ) {
        return new MineButton(options, gameState, loader, publisher, subscriber);
    }
}