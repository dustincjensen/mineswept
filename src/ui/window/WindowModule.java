package ui.window;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import events.IEventPublisher;
import events.IEventSubscriber;
import models.Resource;
import ui.menu.Menus;
import ui.layout.MainLayout;
import ui.ResourceLoader;

public class WindowModule extends AbstractModule {
    @Provides
    @Singleton
    public Window provideMainWindow(
        MainLayout mainLayout,
        Menus menus,
        WindowHandler mainWindowHandler,
        IEventSubscriber eventSubscriber,
        ResourceLoader loader
    ) {
        return new Window(
            mainLayout,
            menus,
            mainWindowHandler,
            eventSubscriber,
            loader.get(Resource.SmileyCool).getImage());
    }

    @Provides
    public WindowHandler provideWindowHandler(IEventPublisher publisher) {
        return new WindowHandler(publisher);
    }
}