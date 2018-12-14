package events.handlers;

import events.IEventSubscriber;
import events.PauseGameEvent;
import events.RefreshMainWindowEvent;
import events.ResetGameEvent;
import events.ResetMinePanelEvent;
import events.SetResetButtonIconEvent;
import events.UpdateMineCountEvent;
import gui.ClockTimer;
import gui.Resource;
import state.GameState;

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

        // TODO this should use the event subscriber system.
        clockTimer.reset();
        
        eventSubscriber.notify(new ResetMinePanelEvent(
            gameState.getCurrentPuzzleHeight(),
            gameState.getCurrentPuzzleWidth()
        ));
        eventSubscriber.notify(new SetResetButtonIconEvent(Resource.SmileyHappy));
        eventSubscriber.notify(new UpdateMineCountEvent());
        eventSubscriber.notify(new PauseGameEvent(false));
        eventSubscriber.notify(new RefreshMainWindowEvent());
    }
}