package services;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import state.GameState;

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

    // TODO reevaluate if these need to be singleton's
    @Singleton
    @Provides
    public PreferencesService providePreferences(FileService fileService) {
        return new PreferencesService(fileService);
    }

    @Singleton
    @Provides
    public RecordsService provideRecordsService(FileService fileService) {
        return new RecordsService(fileService);
    }
    
    @Provides
    public StatisticsService provideStatisticsService(FileService fileService) {
        return new StatisticsService(fileService);
    }
}