package gui.events.handlers;

import gui.events.SetResetButtonIconEvent;
import gui.panel.header.ResetButton;

public class SetResetButtonIconEventHandler implements IEventHandler<SetResetButtonIconEvent> {
    private ResetButton resetButton;

    public SetResetButtonIconEventHandler(ResetButton reset) {
        resetButton = reset;
    }

    @Override
    public void execute(SetResetButtonIconEvent event) {
        resetButton.setSmileyIcon(event.resource);   
    }
}