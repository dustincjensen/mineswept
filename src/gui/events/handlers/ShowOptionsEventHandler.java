package gui.events.handlers;

import gui.events.ShowOptionsEvent;
import gui.options.OptionWindow;

public class ShowOptionsEventHandler implements IEventHandler<ShowOptionsEvent> {
    private OptionWindow optionWindow;

    public ShowOptionsEventHandler(OptionWindow window) {
        optionWindow = window;
    }

    @Override
    public void execute(ShowOptionsEvent event) {
        optionWindow.show(event.show);
    }
}