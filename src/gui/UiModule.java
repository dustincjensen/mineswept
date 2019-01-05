package gui;

import com.google.inject.AbstractModule;
import gui.main.MainModule;
import gui.menu.MenuModule;
import gui.options.OptionsModule;
import gui.panel.header.HeaderModule;
import gui.records.RecordsModule;
import gui.statistics.StatisticsModule;

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