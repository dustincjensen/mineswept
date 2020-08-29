package ui;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import events.IEventPublisher;
import events.IEventSubscriber;
import ui.components.ComponentModule;
import ui.layout.LayoutModule;
import ui.window.WindowModule;
import ui.menu.MenuModule;
import ui.options.OptionsModule;
import ui.statistics.StatisticsModule;

public class UiModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ComponentModule());
        install(new LayoutModule());
        install(new WindowModule());
        install(new MenuModule());
        install(new OptionsModule());
        install(new StatisticsModule());
    }

    // TODO this should be moved into a different module?
    @Singleton
    @Provides
    public ClockTimer provideClockTimer(IEventPublisher eventPublisher, IEventSubscriber eventSubscriber) {
        return new ClockTimer(eventPublisher, eventSubscriber);
    }

    // TODO this should be moved into a different module?
    @Singleton
    @Provides
    public ResourceLoader provideResourceLoader() {
        return new ResourceLoader();
    }
}