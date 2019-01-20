package ui.window;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import events.IEventPublisher;
import events.IEventSubscriber;
import ui.menu.Menus;
import ui.layout.MainLayout;
import ui.ResourceLoader;

public class WindowModule extends AbstractModule {
    @Provides
    public Window provideMainWindow(
        MainLayout mainLayout,
        Menus menus,
        WindowHandler mainWindowHandler,
        ResourceLoader loader,
        IEventSubscriber eventSubscriber
    ) {
        return new Window(mainLayout, menus, mainWindowHandler, loader, eventSubscriber);
    }

    @Provides
    public WindowHandler provideWindowHandler(IEventPublisher publisher) {
        return new WindowHandler(publisher);
    }
}