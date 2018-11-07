package gui.events.handlers;

import gui.events.ShowOptionsEvent;
import gui.menu.OptionWindow;

public class ShowOptionsEventHandler implements IEventHandler<ShowOptionsEvent> {
    @Override
    public void execute(ShowOptionsEvent event) {
        OptionWindow.show(event.show);
    }
}