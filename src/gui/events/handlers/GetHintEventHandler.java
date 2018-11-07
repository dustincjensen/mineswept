package gui.events.handlers;

import gui.events.GetHintEvent;
import gui.panel.mines.MinePanel;
import logic.game.MineField;
import logic.game.GameFeatures;

public class GetHintEventHandler implements IEventHandler<GetHintEvent> {
    @Override
    public void execute(GetHintEvent event) {
        if (!GameFeatures.isGameOver()) {
            MineField.getHint();
            MinePanel.update();
        }
    }
}