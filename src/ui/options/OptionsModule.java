package ui.options;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import events.IEventSubscriber;
import models.Resource;
import services.OptionsService;
import state.GameState;
import ui.ResourceLoader;
import ui.components.radioButton.RadioButtonFactory;
import ui.window.Window;

public class OptionsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(OptionsLoader.class)
            .toProvider(OptionsLoaderProvider.class)
            .asEagerSingleton();
    }

    @Singleton
    @Provides
    public OptionsWindow provideOptionsWindow(
        GameState gameState,
        ResourceLoader resourceLoader,
        RadioButtonFactory factory,
        OptionsService optionsService,
        IEventSubscriber eventSubscriber,
        Window window
    ) {
        return new OptionsWindow(
            gameState,
            optionsService,
            factory,
            eventSubscriber,
            resourceLoader.get(Resource.SmileyCool),
            window);
    }
}

/**
 * Creates a provider for the options loader so we can eager load it without anyone actually requiring the class.
 * We have to do this because we use event publish and subscribe, no one actually loads the option loader as a
 * dependency. We need the options loader to create the OptionsWindow dependency when it wants to be shown, instead
 * of requiring it, because requiring would cause a circular dependency on the Window.
 */
class OptionsLoaderProvider implements Provider<OptionsLoader> {
    private IEventSubscriber eventSubscriber;

    @Inject
    public OptionsLoaderProvider(IEventSubscriber eventSubscriber) {
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public OptionsLoader get() {
        return new OptionsLoader(eventSubscriber);
    }
}
