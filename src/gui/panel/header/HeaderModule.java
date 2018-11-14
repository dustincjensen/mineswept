package gui.panel.header;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
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
    public MineCount provideMineCount(IEventPublisher publisher) {
        return new MineCount(publisher);
    }

    @Provides
    public ResetButton provideResetButton(IEventPublisher publisher) {
        return new ResetButton(publisher);
    }

    @Provides
    public TimeCount provideTimeCount(GameState gameState, IEventPublisher publisher) {
        return new TimeCount(gameState, publisher);
    }
}