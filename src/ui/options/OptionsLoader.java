package ui.options;

import events.IEventSubscriber;
import events.ShowOptionsEvent;
import utils.ClassFactory;

public class OptionsLoader  {
    private IEventSubscriber eventSubscriber;

    public OptionsLoader(IEventSubscriber eventSubscriber) {
        this.eventSubscriber = eventSubscriber;
        setupSubscriptions();
    }

    private void setupSubscriptions() {
		eventSubscriber.subscribe(ShowOptionsEvent.class, event -> {
            ClassFactory.create(OptionsWindow.class).show();
		});
	}
}