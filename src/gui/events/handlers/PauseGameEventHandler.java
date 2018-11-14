package gui.events.handlers;

import gui.events.PauseGameEvent;
import gui.MineSwept;
import gui.panel.header.ResetButton;
import gui.panel.header.SmileyEnum;
import logic.game.*;

public class PauseGameEventHandler implements IEventHandler<PauseGameEvent> {
    private GameState gameState;

    public PauseGameEventHandler(GameState state) {
        gameState = state;
    }

    @Override
    public void execute(PauseGameEvent event) {
        if (gameState.isGameStarted() && !gameState.isGameOver()) {
            if (event.pause) {
                ClockTimer.stop();
                ResetButton.setSmileyIcon(SmileyEnum.paused);
            } else {
                ClockTimer.start();
                ResetButton.setSmileyIcon(SmileyEnum.happy);
            }

            gameState.setGamePaused(event.pause);
            MineSwept.getMainPanel().pausePanel(event.pause);
        }
    }
}