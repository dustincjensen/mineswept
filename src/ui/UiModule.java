package ui;

import com.google.inject.AbstractModule;
import ui.main.MainModule;
import ui.menu.MenuModule;
import ui.options.OptionsModule;
import ui.panel.header.HeaderModule;
import ui.records.RecordsModule;
import ui.statistics.StatisticsModule;

public class UiModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new MainModule());
        install(new MenuModule());
        install(new OptionsModule());
        install(new RecordsModule());
        install(new StatisticsModule());
        
        // TODO this one might be redone...
        install(new HeaderModule());
    }
}