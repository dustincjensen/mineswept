package gui.main;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import events.IEventPublisher;
import events.IEventSubscriber;
import gui.menu.Menus;
import gui.panel.MainPanel;
import gui.ResourceLoader;

public class MainModule extends AbstractModule {
    @Provides
    public MainWindow provideMainWindow(
        MainPanel mainPanel,
        Menus menus,
        MainWindowHandler mainWindowHandler,
        ResourceLoader loader,
        IEventSubscriber eventSubscriber
    ) {
        return new MainWindow(mainPanel, menus, mainWindowHandler, loader, eventSubscriber);
    }

    @Provides
    public MainWindowHandler provideMainWindowHandler(IEventPublisher publisher) {
        return new MainWindowHandler(publisher);
    }
}