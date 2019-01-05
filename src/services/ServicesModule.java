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

    /**
     * Provides the options as a singleton. This is because we cache
     * the options for repeated access. The square color is accessed
     * hundreds of times.
     * 
     * @param fileService utility service for file interactions.
     * @return a reference to the options service.
     */
    @Singleton
    @Provides
    public OptionsService provideOptions(FileService fileService) {
        return new OptionsService(fileService);
    }

    // No cached state, so no need for a singleton.
    @Provides
    public RecordsService provideRecordsService(FileService fileService) {
        return new RecordsService(fileService);
    }
    
    // No cached state, so no need for a singleton.
    @Provides
    public StatisticsService provideStatisticsService(FileService fileService) {
        return new StatisticsService(fileService);
    }
}