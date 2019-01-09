package ui.menu;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import events.IEventPublisher;

public class MenuModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    public Menus provideMenus(GameMenu gameMenu, ViewMenu viewMenu, HelpMenu helpMenu) {
        return new Menus(gameMenu, viewMenu, helpMenu);
    }

    @Provides
    public GameMenu provideGameMenu(IEventPublisher publisher) {
        return new GameMenu(publisher);
    }

    @Provides
    public ViewMenu provideViewMenu(IEventPublisher publisher) {
        return new ViewMenu(publisher);
    }

    @Provides
    public HelpMenu provideHelpMenu(IEventPublisher publisher) {
        return new HelpMenu(publisher);
    }
}