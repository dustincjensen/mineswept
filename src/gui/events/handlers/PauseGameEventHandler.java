package gui.events.handlers;

import gui.events.IEventSubscriber;
import gui.events.PauseGameEvent;
import gui.events.SetResetButtonIconEvent;
import gui.MineSwept;
import gui.Resource;
import logic.game.*;

public class PauseGameEventHandler implements IEventHandler<PauseGameEvent> {
    private GameState gameState;
    private ClockTimer clockTimer;
    private IEventSubscriber eventSubscriber;

    public PauseGameEventHandler(GameState state, ClockTimer timer, IEventSubscriber subscriber) {
        gameState = state;
        clockTimer = timer;
        eventSubscriber = subscriber;
    }

    @Override
    public void execute(PauseGameEvent event) {
        if (gameState.isGameStarted() && !gameState.isGameOver()) {
            if (event.pause) {
                clockTimer.stop();
                eventSubscriber.notify(new SetResetButtonIconEvent(Resource.SmileyPaused));
            } else {
                clockTimer.start();
                eventSubscriber.notify(new SetResetButtonIconEvent(Resource.SmileyHappy));
            }

            gameState.setGamePaused(event.pause);
            MineSwept.getMainPanel().pausePanel(event.pause);
        }
    }
}