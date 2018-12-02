package gui.events.handlers;

import gui.events.IEventSubscriber;
import gui.events.ResetGameEvent;
import gui.MineSwept;
import gui.panel.header.*;
import gui.panel.mines.MinePanel;
import logic.game.*;

public class ResetGameEventHandler implements IEventHandler<ResetGameEvent> {
    private GameState gameState;
    private ClockTimer clockTimer;
    private IEventSubscriber eventSubscriber;

    public ResetGameEventHandler(GameState state, ClockTimer timer, IEventSubscriber subscriber) {
        gameState = state;
        clockTimer = timer;
        eventSubscriber = subscriber;
    }

    @Override
    public void execute(ResetGameEvent event) {
        gameState.reset();

        clockTimer.reset();
        
        MinePanel.reset();
        
        // Once this is hooked up... there should be no...
        // MinePanel.reset();
        // MineCount.update();
        // MineSwept.getMainPanel().pausePanel(false);
        // MineSwept.refresh();
        // perhaps no clockTimer.reset();
        eventSubscriber.notify(event);

        MineCount.update();

        MineSwept.getMainPanel().pausePanel(false);
        MineSwept.refresh();
    }
}