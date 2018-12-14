package factories;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import services.OctoCheckService;

public class FactoriesModule extends AbstractModule {
    @Override
    public void configure() {
    }

    @Provides
    public MinesFactory provideMinesFactory(OctoCheckService octo) {
        return new MinesFactory(octo);
    }
}