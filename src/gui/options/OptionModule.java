package gui.options;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Provides;
import events.IEventSubscriber;
import gui.ResourceLoader;
import services.PreferencesService;
import state.GameState;

public class OptionModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(OptionWindow.class)
            .toProvider(OptionWindowProvider.class)
            .asEagerSingleton();
    }
}

/**
 * Creates a provider for the option window so we can eager load it without anyone actually requiring the class.
 * We have to do this because we use event publish and subscribe, no one actually loads the option window as a
 * dependency.
 */
class OptionWindowProvider implements Provider<OptionWindow> {
    private GameState gameState;
    private ResourceLoader resourceLoader;
    private PreferencesService preferencesService;
    private IEventSubscriber eventSubscriber;

    @Inject
    public OptionWindowProvider(
        GameState state,
        ResourceLoader loader,
        PreferencesService prefs,
		IEventSubscriber subscriber
    ) {
        gameState = state;
        resourceLoader = loader;
        preferencesService = prefs;
        eventSubscriber = subscriber;
    }

    @Override
    public OptionWindow get() {
        return new OptionWindow(
            gameState,
            resourceLoader,
            preferencesService,
            eventSubscriber);
    }
}