package gui;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import events.EventModule;
import events.IEventPublisher;
import events.IEventSubscriber;
import gui.menu.MenuModule;
import gui.menu.Menus;
import gui.options.OptionsModule;
import gui.options.OptionsWindow;
import gui.panel.MainPanel;
import gui.panel.header.HeaderModule;
import gui.panel.header.HeaderPanel;
import gui.panel.mines.MineButton;
import gui.panel.mines.MinePanel;
import gui.panel.mines.PausePanel;
import gui.records.RecordsModule;
import gui.statistics.StatisticsModule;
import factories.FactoriesModule;
import factories.MinesFactory;
import services.HintService;
import services.MineRevealService;
import services.OctoCheckService;
import services.OptionsService;
import services.ServicesModule;
import state.GameState;

public class InjectionModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new EventModule());
        install(new HeaderModule());
        install(new MenuModule());

        install(new OptionsModule());
        install(new RecordsModule());
        install(new StatisticsModule());

        install(new FactoriesModule());
        install(new ServicesModule());
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
    public MineSwept provideMineSwept(
        Menus menus,
        MainPanel mainPanel,
        IEventSubscriber subscriber,
        MainWindowHandler handler,
        ResourceLoader loader
    ) {
        return new MineSwept(menus, mainPanel, subscriber, handler, loader);
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

    @Provides
    public MainWindowHandler provideMainWindowHandler(IEventPublisher publisher) {
        return new MainWindowHandler(publisher);
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
    public GameState provideGameState(OptionsService options, MinesFactory factory) {
        return new GameState(options, factory);
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