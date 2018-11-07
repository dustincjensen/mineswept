package gui.events.handlers;

import gui.events.PauseGameEvent;
import gui.MineSwept;
import gui.panel.header.ResetButton;
import logic.game.*;

public class PauseGameEventHandler implements IEventHandler<PauseGameEvent> {
    @Override
    public void execute(PauseGameEvent event) {
        if (GameFeatures.isGameStarted() && !GameFeatures.isGameOver()) {
            if (event.pause) {
                ClockTimer.stop();
                ResetButton.setSmileyIcon(6);
            } else {
                ClockTimer.start();
                ResetButton.setSmileyIcon(1);
            }

            GameFeatures.setGamePaused(event.pause);
            MineSwept.getMainPanel().pausePanel(event.pause);
        }
    }
}