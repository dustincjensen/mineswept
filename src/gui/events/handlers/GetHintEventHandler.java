package gui.events.handlers;

import gui.events.GetHintEvent;
import gui.panel.header.MineCount;
import gui.panel.mines.MinePanel;
import logic.game.MineField;
import logic.game.GameState;
import logic.game.HintService;

public class GetHintEventHandler implements IEventHandler<GetHintEvent> {
    private GameState gameState;
    private HintService hintService;

    public GetHintEventHandler(GameState state, HintService hint) {
        gameState = state;
        hintService = hint;
    }

    @Override
    public void execute(GetHintEvent event) {
        if (!gameState.isGameOver()) {
            hintService.useHint();
            
            // TODO non-static references...
            MineCount.update();
            MinePanel.update();
        }
    }
}