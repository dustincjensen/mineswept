package gui;

import gui.events.EventPublisher;
import gui.events.IEventPublisher;
import com.google.inject.*;

public class InjectionModule extends AbstractModule {
    @Override
    protected void configure() {
        // Event publisher should only exist once for the entire application.
        bind(EventPublisher.class).in(Singleton.class);
        bind(IEventPublisher.class).to(EventPublisher.class);
    }
}