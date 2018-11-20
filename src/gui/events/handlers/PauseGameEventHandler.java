package gui.events.handlers;

import gui.events.PauseGameEvent;
import gui.MineSwept;
import gui.Resource;
import gui.panel.header.ResetButton;
import logic.game.*;

public class PauseGameEventHandler implements IEventHandler<PauseGameEvent> {
    private GameState gameState;
    private ClockTimer clockTimer;

    public PauseGameEventHandler(GameState state, ClockTimer timer) {
        gameState = state;
        clockTimer = timer;
    }

    @Override
    public void execute(PauseGameEvent event) {
        if (gameState.isGameStarted() && !gameState.isGameOver()) {
            if (event.pause) {
                clockTimer.stop();
                ResetButton.setSmileyIcon(Resource.SmileyPaused);
            } else {
                clockTimer.start();
                ResetButton.setSmileyIcon(Resource.SmileyHappy);
            }

            gameState.setGamePaused(event.pause);
            MineSwept.getMainPanel().pausePanel(event.pause);
        }
    }
}