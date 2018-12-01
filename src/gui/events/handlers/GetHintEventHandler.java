package gui.events.handlers;

import gui.events.GetHintEvent;
import gui.panel.mines.MinePanel;
import logic.game.MineField;
import logic.game.NewMineField;
import logic.game.GameState;

public class GetHintEventHandler implements IEventHandler<GetHintEvent> {
    private GameState gameState;
    private NewMineField mineField;

    public GetHintEventHandler(GameState state, NewMineField newMineField) {
        gameState = state;
        mineField = newMineField;
    }

    @Override
    public void execute(GetHintEvent event) {
        if (!gameState.isGameOver()) {
            mineField.getHint(gameState.getMines());
            MinePanel.update();
        }
    }
}