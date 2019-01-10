package events.handlers;

import events.IEventSubscriber;
import events.PauseGameEvent;
import events.RefreshMainWindowEvent;
import events.ResetClockTimerEvent;
import events.ResetGameEvent;
import events.ResetMinePanelEvent;
import events.SetResetButtonIconEvent;
import events.SetTimeCountEvent;
import events.UpdateMineCountEvent;
import models.Resource;
import state.GameState;

public class ResetGameEventHandler implements IEventHandler<ResetGameEvent> {
    private GameState gameState;
    private IEventSubscriber eventSubscriber;

    public ResetGameEventHandler(GameState gameState, IEventSubscriber eventSubscriber) {
        this.gameState = gameState;
        this.eventSubscriber = eventSubscriber;
    }

    @Override
    public void execute(ResetGameEvent event) {
        gameState.reset();

        eventSubscriber.notify(new ResetMinePanelEvent(
            gameState.getCurrentPuzzleHeight(),
            gameState.getCurrentPuzzleWidth()
        ));
        eventSubscriber.notify(new ResetClockTimerEvent());
        eventSubscriber.notify(new SetTimeCountEvent(0));
        eventSubscriber.notify(new SetResetButtonIconEvent(Resource.SmileyHappy));
        eventSubscriber.notify(new UpdateMineCountEvent());
        eventSubscriber.notify(new PauseGameEvent(false));
        eventSubscriber.notify(new RefreshMainWindowEvent());
    }
}