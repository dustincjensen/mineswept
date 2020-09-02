package ui.layout;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import ui.layout.body.BodyLayout;
import ui.layout.body.BodyModule;
import ui.layout.header.HeaderLayout;
import ui.layout.header.HeaderModule;

public class LayoutModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new HeaderModule());
        install(new BodyModule());
    }

    @Provides
    public MainLayout provideMainLayout(HeaderLayout header, BodyLayout body) {
        return new MainLayout(header, body);
    }
}