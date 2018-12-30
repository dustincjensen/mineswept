package gui.records;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Provides;
import events.IEventPublisher;
import events.IEventSubscriber;

public class RecordModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(RecordWindow.class)
            .toProvider(RecordWindowProvider.class)
            .asEagerSingleton();
    }
}

/**
 * Creates a provider for the record window so we can eager load it without anyone actually requiring the class.
 * We have to do this because we use event publish and subscribe, no one actually loads the record window as a
 * dependency.
 */
class RecordWindowProvider implements Provider<RecordWindow> {
    private IEventPublisher eventPublisher;
    private IEventSubscriber eventSubscriber;

    @Inject
    public RecordWindowProvider(IEventPublisher publisher, IEventSubscriber subscriber) {
        eventPublisher = publisher;
        eventSubscriber = subscriber;
    }

    @Override
    public RecordWindow get() {
        return new RecordWindow(eventPublisher, eventSubscriber);
    }
}