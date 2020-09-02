package ui.layout.body;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
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
        OptionsService options,
        IEventSubscriber subscriber
    ) {
        return new BodyLayout(mine, pause, options, subscriber);
    }

    @Provides
    public PausePanel providePausePanel(
        GameState state,
        OptionsService options,
        IEventPublisher publisher,
        IEventSubscriber subscriber
    ) {
        return new PausePanel(
            state,
            options,
            publisher,
            subscriber);
    }

    @Provides
    public MinePanel provideMinePanel(GameState state, OptionsService options, IEventSubscriber subscriber) {
        return new MinePanel(
            state,
            options,
            subscriber);
    }

    @Provides
    public MineButton provideMineButton(
        GameState gameState,
        ResourceLoader loader,
        IEventPublisher publisher
    ) {
        return new MineButton(
            gameState,
            publisher,
            loader.get(Resource.Mine),
            loader.get(Resource.MineWrong),
            loader.get(Resource.Flag));
    }
}