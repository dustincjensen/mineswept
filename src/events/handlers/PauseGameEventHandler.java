package events.handlers;

import events.GamePausedEvent;
import events.IEventSubscriber;
import events.PauseGameEvent;
import events.SetResetButtonIconEvent;
import events.StartClockTimerEvent;
import events.StopClockTimerEvent;
import models.Resource;
import state.GameState;

public class PauseGameEventHandler implements IEventHandler<PauseGameEvent> {
    private GameState gameState;
    private IEventSubscriber eventSubscriber;

    public PauseGameEventHandler(GameState state, IEventSubscriber subscriber) {
        gameState = state;
        eventSubscriber = subscriber;
    }

    @Override
    public void execute(PauseGameEvent event) {
        if (gameState.isGameStarted() && !gameState.isGameOver()) {
            var isPaused = gameState.isGamePaused();
            if (isPaused) {
                eventSubscriber.notify(new StartClockTimerEvent());
                eventSubscriber.notify(new SetResetButtonIconEvent(Resource.SmileyHappy));
            } else {
                eventSubscriber.notify(new StopClockTimerEvent());
                eventSubscriber.notify(new SetResetButtonIconEvent(Resource.SmileyPaused));
            }

            gameState.setGamePaused(!isPaused);
            eventSubscriber.notify(new GamePausedEvent(!isPaused));
        }
    }
}