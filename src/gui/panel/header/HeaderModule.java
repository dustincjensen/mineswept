package gui.panel.header;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import gui.ResourceLoader;
import gui.events.IEventPublisher;
import logic.game.GameState;

public class HeaderModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    public HeaderPanel provideHeaderPanel(MineCount mineCount, ResetButton resetButton, TimeCount timeCount) {
        return new HeaderPanel(mineCount, resetButton, timeCount);
    }

    @Provides
    public MineCount provideMineCount(IEventPublisher publisher, ResourceLoader loader) {
        return new MineCount(publisher, loader);
    }

    // TODO Need to figure out our UI paradigm... otherwise singletons are still "statics".
    // I think it needs to be an event subscription bus
    @Singleton
    @Provides
    public ResetButton provideResetButton(IEventPublisher publisher, ResourceLoader loader) {
        return new ResetButton(publisher, loader);
    }

    // TODO Need to figure out our UI paradigm... otherwise singletons are still "statics".
    // I think it needs to be an event subscription bus
    @Singleton
    @Provides
    public TimeCount provideTimeCount(GameState gameState, IEventPublisher publisher, ResourceLoader loader) {
        return new TimeCount(gameState, publisher, loader);
    }
}