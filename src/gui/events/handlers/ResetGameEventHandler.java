package gui.events.handlers;

import gui.events.ResetGameEvent;
import gui.MineSwept;
import gui.panel.header.*;
import gui.panel.mines.MinePanel;
import logic.game.*;

public class ResetGameEventHandler implements IEventHandler<ResetGameEvent> {
    private GameState gameState;
    private ClockTimer clockTimer;

    public ResetGameEventHandler(GameState state, ClockTimer timer) {
        gameState = state;
        clockTimer = timer;
    }

    @Override
    public void execute(ResetGameEvent event) {
        gameState.reset();

        MineField.reset();
        
        clockTimer.reset();
        
        MinePanel.reset();
        ResetButton.reset();
        MineCount.reset();
        MineSwept.getMainPanel().pausePanel(false);
        MineSwept.refresh();
    }
}