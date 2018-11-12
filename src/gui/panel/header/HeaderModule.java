package gui.panel.header;

import gui.events.IEventPublisher;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

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
    public TimeCount provideTimeCount(IEventPublisher publisher) {
        return new TimeCount(publisher);
    }
}