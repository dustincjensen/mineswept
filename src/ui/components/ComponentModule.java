package ui.components;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import models.Resource;
import ui.ResourceLoader;
import ui.components.radioButton.RadioButtonFactory;

public class ComponentModule extends AbstractModule {
    @Provides
    public RadioButtonFactory provideRadioButtonFactory(
        ResourceLoader resourceLoader
    ) {
        return new RadioButtonFactory(
            resourceLoader.get(Resource.RadioButtonDefault),
            resourceLoader.get(Resource.RadioButtonSelected));
    }
}