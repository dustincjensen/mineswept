package ui.options;

import events.IEventSubscriber;
import events.ShowOptionsEvent;
import utils.ClassFactory;

public class OptionsLoader  {
    public OptionsLoader(IEventSubscriber eventSubscriber) {
        eventSubscriber.subscribe(ShowOptionsEvent.class, event -> {
            ClassFactory.create(OptionsWindow.class).show();
		});
    }
}