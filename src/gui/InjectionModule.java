package gui;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import gui.events.EventModule;
import gui.events.IEventPublisher;
import gui.events.IEventSubscriber;
import gui.menu.MenuModule;
import gui.menu.Menus;
import gui.options.OptionModule;
import gui.options.OptionWindow;
import gui.panel.MainPanel;
import gui.panel.header.HeaderModule;
import gui.panel.header.HeaderPanel;
import gui.panel.mines.MineButton;
import gui.panel.mines.MinePanel;
import gui.panel.mines.PausePanel;
import gui.records.RecordModule;
import gui.statistics.StatisticsModule;
import logic.files.*;
import logic.game.*;
import factories.MinesFactory;
import services.HintService;
import services.MineRevealService;
import services.OctoCheckService;

public class InjectionModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new FileModule());
        install(new EventModule());
        install(new HeaderModule());
        install(new MenuModule());

        install(new OptionModule());
        install(new RecordModule());
        install(new StatisticsModule());
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
        Preferences prefs, 
        GameState gameState, 
        ClockTimer clockTimer,
        ResourceLoader loader,
        IEventPublisher publisher,
        IEventSubscriber subscriber
    ) {
        return new MineButton(prefs, gameState, clockTimer, loader, publisher, subscriber);
    }

    // TODO this shouldn't need to be explicit... revisit this once everything is injected properly.
    @Singleton
    @Provides
    public GameState provideGameState(Preferences prefs, MinesFactory factory) {
        return new GameState(prefs, factory);
    }

    // TODO this shouldn't need to be explicit... revisit this once everything is injected properly.
    @Singleton
    @Provides
    public ClockTimer provideClockTimer(IEventPublisher publisher) {
        return new ClockTimer(publisher);
    }
    
    @Provides
    public HintService provideHintService(GameState gameState) {
        return new HintService(gameState);
    }

    @Singleton
    @Provides
    public ResourceLoader provideResourceLoader() {
        return new ResourceLoader();
    }

    @Provides
    public MinesFactory provideMinesFactory(OctoCheckService octo) {
        return new MinesFactory(octo);
    }

    @Provides
    public MineRevealService provideMineRevealService(OctoCheckService octo) {
        return new MineRevealService(octo);
    }
}