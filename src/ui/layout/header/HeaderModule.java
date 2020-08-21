package ui.layout.header;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import events.IEventPublisher;
import events.IEventSubscriber;
import models.Resource;
import state.GameState;
import ui.ResourceLoader;

public class HeaderModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    public HeaderLayout provideHeaderLayout(MineCount mineCount, ResetButton resetButton, TimeCount timeCount) {
        return new HeaderLayout(mineCount, resetButton, timeCount);
    }

    @Provides
    public MineCount provideMineCount(
        GameState gameState,
        IEventPublisher publisher,
        IEventSubscriber subscriber,
        ResourceLoader loader
    ) {
        return new MineCount(gameState, publisher, subscriber, loader.get(Resource.BombHint));
    }

    // TODO remove singleton when event subscribers are fully fleshed out
    // These would probably work now, but what if there were a second requirement of ResetButton!?
    @Singleton
    @Provides
    public ResetButton provideResetButton(IEventPublisher publisher, IEventSubscriber subscriber, ResourceLoader loader) {
        return new ResetButton(publisher, subscriber, loader);
    }

    // TODO remove singleton when event subscribers are fully fleshed out
    @Singleton
    @Provides
    public TimeCount provideTimeCount(GameState gameState, IEventPublisher publisher, IEventSubscriber subscriber, ResourceLoader loader) {
        return new TimeCount(gameState, publisher, subscriber, loader.get(Resource.Clock));
    }
}