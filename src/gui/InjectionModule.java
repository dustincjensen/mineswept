package gui;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import gui.events.EventModule;
import gui.events.IEventPublisher;
import gui.menu.MenuModule;
import gui.menu.Menus;
import gui.panel.header.HeaderModule;
import gui.panel.mines.PausePanel;

public class InjectionModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new EventModule());
        install(new HeaderModule());
        install(new MenuModule());
    }

    // TODO move this into it's own module...
    @Provides
    public PausePanel providePausePanel(IEventPublisher publisher) {
        return new PausePanel(publisher);
    }

    // TODO move this into it's own module?
    @Provides
    public MineSwept provideMineSwept(Menus menus, IEventPublisher publisher) {
        return new MineSwept(menus, publisher);
    }
}