package ui.statistics;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import events.IEventPublisher;
import events.IEventSubscriber;
import models.Resource;
import ui.ResourceLoader;
import ui.window.Window;

public class StatisticsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(StatisticsLoader.class)
            .toProvider(StatisticsLoaderProvider.class)
            .asEagerSingleton();
    }

    @Singleton
    @Provides
    public StatisticsWindow provideStatisticsWindow(
        IEventPublisher eventPublisher,
        IEventSubscriber eventSubscriber,
        ResourceLoader resourceLoader,
        Window window
    ) {
        return new StatisticsWindow(
            eventPublisher,
            eventSubscriber,
            resourceLoader.get(Resource.SmileyCool).getImage(),
            window);
    }
}

/**
 * Creates a provider for the statistics loader so we can eager load it without anyone actually requiring the class.
 * We have to do this because we use event publish and subscribe, no one actually loads the option loader as a
 * dependency. We need the statistics loader to create the StatisticsWindow dependency when it wants to be shown, instead
 * of requiring it, because requiring would cause a circular dependency on the Window.
 */
class StatisticsLoaderProvider implements Provider<StatisticsLoader> {
    private IEventSubscriber eventSubscriber;

    @Inject
    public StatisticsLoaderProvider(IEventSubscriber eventSubscriber) {
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public StatisticsLoader get() {
        return new StatisticsLoader(eventSubscriber);
    }
}