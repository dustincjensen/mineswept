package gui.events.handlers;

import gui.events.GetHintEvent;
import gui.panel.mines.MinePanel;
import logic.game.MineField;
import logic.game.GameState;

public class GetHintEventHandler implements IEventHandler<GetHintEvent> {
    private GameState gameState;

    public GetHintEventHandler(GameState state) {
        gameState = state;
    }

    @Override
    public void execute(GetHintEvent event) {
        if (!gameState.isGameOver()) {
            MineField.getHint();
            MinePanel.update();
        }
    }
}