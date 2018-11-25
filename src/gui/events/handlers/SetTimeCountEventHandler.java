package gui.events.handlers;

import gui.panel.header.TimeCount;
import gui.events.SetTimeCountEvent;

public class SetTimeCountEventHandler implements IEventHandler<SetTimeCountEvent> {
    private TimeCount timeCount;

    public SetTimeCountEventHandler(TimeCount time) {
        timeCount = time;
    }

    @Override
    public void execute(SetTimeCountEvent event) {
        timeCount.setClockCount(event.time);
    }
}