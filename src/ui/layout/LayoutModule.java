package ui.layout;

import com.google.inject.AbstractModule;
import ui.layout.body.BodyModule;
import ui.layout.header.HeaderModule;

public class LayoutModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new BodyModule());
        install(new HeaderModule());
    }
}