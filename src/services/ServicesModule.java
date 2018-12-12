package services;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import logic.game.GameState;

public class ServicesModule extends AbstractModule {
    @Override
    public void configure() {
    }

    @Provides
    public HintService provideHintService(GameState gameState) {
        return new HintService(gameState);
    }

    @Provides
    public MineRevealService provideMineRevealService(OctoCheckService octo) {
        return new MineRevealService(octo);
    }

    @Singleton
    @Provides
    public PreferencesService providePreferences() {
        return new PreferencesService();
    }

    @Singleton
    @Provides
    public RecordsService provideRecordsService() {
        return new RecordsService();
    }
}