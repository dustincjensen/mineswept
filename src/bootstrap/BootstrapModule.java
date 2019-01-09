package bootstrap;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import events.EventModule;
import events.IEventPublisher;
import events.IEventSubscriber;
import ui.ClockTimer;
import ui.main.MainWindow;
import ui.ResourceLoader;
import ui.UiModule;
import ui.panel.MainPanel;
import ui.panel.header.HeaderPanel;
import ui.panel.mines.MineButton;
import ui.panel.mines.MinePanel;
import ui.panel.mines.PausePanel;
import factories.FactoriesModule;
import services.OptionsService;
import services.ServicesModule;
import state.GameState;
import state.StateModule;

public class BootstrapModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new EventModule());
        install(new FactoriesModule());
        install(new ServicesModule());
        install(new StateModule());
        install(new UiModule());
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

    // TODO move this into it's own module?
    @Provides
    public MineSwept provideMineSwept(MainWindow window) {
        return new MineSwept(window);
    }

    @Provides
    public MainPanel provideMainPanel(
        HeaderPanel header,
        MinePanel mine,
        PausePanel pause,
        IEventSubscriber subscriber
    ) {
        return new MainPanel(header, mine, pause, subscriber);
    }

    // TODO move this into it's own module?
    @Provides
    public MineButton provideMineButton(
        OptionsService options, 
        GameState gameState, 
        ClockTimer clockTimer,
        ResourceLoader loader,
        IEventPublisher publisher,
        IEventSubscriber subscriber
    ) {
        return new MineButton(options, gameState, clockTimer, loader, publisher, subscriber);
    }

    // TODO this shouldn't need to be explicit... revisit this once everything is injected properly.
    @Singleton
    @Provides
    public ClockTimer provideClockTimer(IEventPublisher publisher) {
        return new ClockTimer(publisher);
    }

    @Singleton
    @Provides
    public ResourceLoader provideResourceLoader() {
        return new ResourceLoader();
    }
}