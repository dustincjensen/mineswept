package ui.layout.body;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import events.IEventPublisher;
import events.IEventSubscriber;
import models.Resource;
import services.OptionsService;
import state.GameState;
import ui.ResourceLoader;

public class BodyModule extends AbstractModule {
    @Provides
    public BodyLayout provideBodyLayout(
        MinePanel mine,
        PausePanel pause,
        IEventSubscriber subscriber
    ) {
        return new BodyLayout(mine, pause, subscriber);
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
        return new MineButton(
            gameState,
            options,
            publisher,
            subscriber,
            loader.get(Resource.Mine),
            loader.get(Resource.MineWrong),
            loader.get(Resource.MineHint),
            loader.get(Resource.Flag),
            loader.get(Resource.FlagHint));
    }
}