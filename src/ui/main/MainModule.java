package ui.main;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import events.IEventPublisher;
import events.IEventSubscriber;
import ui.menu.Menus;
import ui.layout.body.BodyLayout;
import ui.ResourceLoader;

public class MainModule extends AbstractModule {
    @Provides
    public MainWindow provideMainWindow(
        BodyLayout bodyLayout,
        Menus menus,
        MainWindowHandler mainWindowHandler,
        ResourceLoader loader,
        IEventSubscriber eventSubscriber
    ) {
        return new MainWindow(bodyLayout, menus, mainWindowHandler, loader, eventSubscriber);
    }

    @Provides
    public MainWindowHandler provideMainWindowHandler(IEventPublisher publisher) {
        return new MainWindowHandler(publisher);
    }
}