package events.handlers;

import events.GetHintEvent;
import events.IEventSubscriber;
import events.UpdateMineCountEvent;
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
        if (!gameState.isGameOver()) {
            hintService.useHint();
            eventSubscriber.notify(new UpdateMineCountEvent());
            eventSubscriber.notify(new UpdateMinePanelEvent());
        }
    }
}