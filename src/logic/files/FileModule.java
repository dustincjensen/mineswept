package logic.files;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class FileModule extends AbstractModule {
    @Override
    public void configure() {
    }

    @Singleton
    @Provides
    public Records provideRecords() {
        return new Records();
    }

    @Singleton
    @Provides
    public Preferences providePreferences() {
        return new Preferences();
    }
}