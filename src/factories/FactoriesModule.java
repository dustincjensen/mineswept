package factories;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import services.OctoCheckService;

public class FactoriesModule extends AbstractModule {
    @Provides
    public MinesFactory provideMinesFactory(OctoCheckService octo) {
        return new MinesFactory(octo);
    }
}