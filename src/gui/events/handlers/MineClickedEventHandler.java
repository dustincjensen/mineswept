package gui.events.handlers;

import gui.Resource;
import gui.events.IEventSubscriber;
import gui.events.MineClickedEvent;
import gui.events.SetResetButtonIconEvent;
import gui.events.UpdateMinePanelEvent;
import logic.game.GameState;
import logic.game.Mine;
import logic.game.MineField;

public class MineClickedEventHandler implements IEventHandler<MineClickedEvent> {
    private GameState gameState;
    private IEventSubscriber eventSubscriber;

    public MineClickedEventHandler(
        GameState state,
        IEventSubscriber subscriber
    ) {
        gameState = state;
        eventSubscriber = subscriber;
    }

    @Override
    public void execute(MineClickedEvent event) {
        if (!event.isLeftMouseButton) return;
        if (gameState.isGameOver()) return;

        // Reset to the smiley icon, since we let go of the mouse...
        eventSubscriber.notify(new SetResetButtonIconEvent(Resource.SmileyHappy));

        // We have to be in the mine field to do something...
        if (!event.insideMineField) return;

        int x, y;
        if (event.dragX == -1 && event.dragY == -1) {
            x = event.x;
            y = event.y;
        } else {
            x = event.dragX;
            y = event.dragY;
        }

        Mine mine = gameState.getMine(x, y);
        if (!mine.getAnyProtected()) {
            // TODO replace reference...
            MineField.leftClicked(x, y);
        }

        eventSubscriber.notify(new UpdateMinePanelEvent());
    }
}