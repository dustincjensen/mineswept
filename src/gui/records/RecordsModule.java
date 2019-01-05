package gui.records;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Provides;
import events.IEventPublisher;
import events.IEventSubscriber;

public class RecordsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(RecordsWindow.class)
            .toProvider(RecordsWindowProvider.class)
            .asEagerSingleton();
    }
}

/**
 * Creates a provider for the record window so we can eager load it without anyone actually requiring the class.
 * We have to do this because we use event publish and subscribe, no one actually loads the record window as a
 * dependency.
 */
class RecordsWindowProvider implements Provider<RecordsWindow> {
    private IEventPublisher eventPublisher;
    private IEventSubscriber eventSubscriber;

    @Inject
    public RecordsWindowProvider(IEventPublisher publisher, IEventSubscriber subscriber) {
        eventPublisher = publisher;
        eventSubscriber = subscriber;
    }

    @Override
    public RecordsWindow get() {
        return new RecordsWindow(eventPublisher, eventSubscriber);
    }
}