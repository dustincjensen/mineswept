package gui.events.handlers;

import gui.events.ResetGameEvent;
import gui.MineSwept;
import gui.panel.header.*;
import gui.panel.mines.MinePanel;
import logic.game.*;

public class ResetGameEventHandler implements IEventHandler<ResetGameEvent> {
    private GameState gameState;
    private ClockTimer clockTimer;
    private ResetButton resetButton;

    public ResetGameEventHandler(GameState state, ClockTimer timer, ResetButton reset) {
        gameState = state;
        clockTimer = timer;
        resetButton = reset;
    }

    @Override
    public void execute(ResetGameEvent event) {
        gameState.reset();

        clockTimer.reset();
        
        MinePanel.reset();
        resetButton.reset();

        MineCount.update();

        MineSwept.getMainPanel().pausePanel(false);
        MineSwept.refresh();
    }
}