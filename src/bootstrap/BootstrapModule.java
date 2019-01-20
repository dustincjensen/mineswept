package bootstrap;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import events.EventModule;
import factories.FactoriesModule;
import services.ServicesModule;
import state.StateModule;
import ui.window.Window;
import ui.UiModule;

public class BootstrapModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new EventModule());
        install(new FactoriesModule());
        install(new ServicesModule());
        install(new StateModule());
        install(new UiModule());
    }

    @Provides
    public MineSwept provideMineSwept(Window window) {
        return new MineSwept(window);
    }
}