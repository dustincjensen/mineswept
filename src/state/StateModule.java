package state;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import factories.MinesFactory;
import services.OptionsService;

public class StateModule extends AbstractModule {
    @Singleton
    @Provides
    public GameState provideGameState(OptionsService options, MinesFactory factory) {
        return new GameState(options, factory);
    }
}