package ui.statistics;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import events.IEventPublisher;
import events.IEventSubscriber;
import models.Resource;
import ui.ResourceLoader;

public class StatisticsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(StatisticsWindow.class)
            .toProvider(StatisticsWindowProvider.class)
            .asEagerSingleton();
    }
}

/**
 * Creates a provider for the statistics window so we can eager load it without anyone actually requiring the class.
 * We have to do this because we use event publish and subscribe, no one actually loads the statistics window as a
 * dependency.
 */
class StatisticsWindowProvider implements Provider<StatisticsWindow> {
    private IEventPublisher eventPublisher;
    private IEventSubscriber eventSubscriber;
    private ResourceLoader resourceLoader;

    @Inject
    public StatisticsWindowProvider(
        IEventPublisher eventPublisher,
        IEventSubscriber eventSubscriber,
        ResourceLoader resourceLoader
    ) {
        this.eventPublisher = eventPublisher;
        this.eventSubscriber = eventSubscriber;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public StatisticsWindow get() {
        return new StatisticsWindow(
            eventPublisher,
            eventSubscriber,
            resourceLoader.get(Resource.SmileyCool).getImage());
    }
}