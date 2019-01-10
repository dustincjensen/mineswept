package ui.layout.body;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import events.IEventPublisher;
import events.IEventSubscriber;
import services.OptionsService;
import state.GameState;
import ui.layout.header.HeaderLayout;
import ui.ResourceLoader;

public class BodyModule extends AbstractModule {
    @Override
    protected void configure() {
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

    @Provides
    public PausePanel providePausePanel(IEventPublisher publisher) {
        return new PausePanel(publisher);
    }

    @Provides
    public MinePanel provideMinePanel(GameState state, IEventSubscriber subscriber) {
        return new MinePanel(
            state.getCurrentPuzzleHeight(),
            state.getCurrentPuzzleWidth(),
            state.getCurrentPuzzleMineCount(),
            subscriber);
    }

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