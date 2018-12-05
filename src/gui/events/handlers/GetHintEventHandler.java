package gui.events.handlers;

import gui.events.GetHintEvent;
import gui.events.IEventSubscriber;
import gui.events.UpdateMineCountEvent;
import gui.events.UpdateMinePanelEvent;
import logic.game.GameState;
import logic.game.HintService;

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