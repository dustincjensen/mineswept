package gui.events;

import gui.events.IEventPublisher;
import gui.options.OptionWindow;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class EventModule extends AbstractModule {
    @Override
    public void configure() {
    }

    // TODO this should not have to ask for an OptionWindow... This is a dependency
    // for one of the event handlers.
    @Singleton
    @Provides
    public IEventPublisher provideEventPublisher(OptionWindow optionWindow) {
        return new EventPublisher(optionWindow);
    }
}