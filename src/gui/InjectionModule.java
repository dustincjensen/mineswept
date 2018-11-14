package gui;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import gui.events.EventModule;
import gui.events.IEventPublisher;
import gui.menu.MenuModule;
import gui.menu.Menus;
import gui.panel.header.HeaderModule;
import gui.panel.mines.MineButton;
import gui.panel.mines.PausePanel;
import logic.files.*;

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
    public MineSwept provideMineSwept(Menus menus, IEventPublisher publisher, Preferences prefs, Records records) {
        return new MineSwept(menus, publisher, prefs, records);
    }

    // TODO move this into it's own module?
    @Provides
    public MineButton provideMineButton(Preferences prefs) {
        return new MineButton(prefs);
    }

    // TODO this shouldn't need to be explicit... revisit this once everything is injected properly.
    @Singleton
    @Provides
    public Preferences providePreferences() {
        return new Preferences();
    }
}