package events.handlers;

import events.GetHintEvent;
import events.StartClockTimerEvent;
import events.IEventSubscriber;
import events.UpdateMinePanelEvent;
import services.HintService;
import state.GameState;

public class GetHintEventHandler implements IEventHandler<GetHintEvent> {
    private GameState gameState;
    private HintService hintService;
    private IEventSubscriber eventSubscriber;

    public GetHintEventHandler(GameState state, HintService hint, IEventSubscriber subscriber) {
        gameState = state;
        hintService = hint;
        eventSubscriber = subscriber;
    }

    @Override
    public void execute(GetHintEvent event) {
        if (!gameState.isGameOver() && !gameState.isGamePaused()) {
            gameState.setGameStarted(true);
            gameState.setHintUsed(true);

            hintService.useHint();

			eventSubscriber.notify(new StartClockTimerEvent());
            eventSubscriber.notify(new UpdateMinePanelEvent());
        }
    }
}