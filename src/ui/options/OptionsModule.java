package ui.options;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Provides;
import events.IEventSubscriber;
import ui.ResourceLoader;
import services.OptionsService;
import state.GameState;

public class OptionsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(OptionsWindow.class)
            .toProvider(OptionsWindowProvider.class)
            .asEagerSingleton();
    }
}

/**
 * Creates a provider for the option window so we can eager load it without anyone actually requiring the class.
 * We have to do this because we use event publish and subscribe, no one actually loads the option window as a
 * dependency.
 */
class OptionsWindowProvider implements Provider<OptionsWindow> {
    private GameState gameState;
    private ResourceLoader resourceLoader;
    private OptionsService optionsService;
    private IEventSubscriber eventSubscriber;

    @Inject
    public OptionsWindowProvider(
        GameState state,
        ResourceLoader loader,
        OptionsService options,
		IEventSubscriber subscriber
    ) {
        gameState = state;
        resourceLoader = loader;
        optionsService = options;
        eventSubscriber = subscriber;
    }

    @Override
    public OptionsWindow get() {
        return new OptionsWindow(
            gameState,
            resourceLoader,
            optionsService,
            eventSubscriber);
    }
}