package gui.events.handlers;

import gui.Resource;
import gui.events.IEventSubscriber;
import gui.events.MineClickedEvent;
import gui.events.SetResetButtonIconEvent;
import gui.events.UpdateMinePanelEvent;
import gui.ClockTimer;
import exceptions.GameOverException;
import state.GameState;
import models.Mine;
import models.Mines;
import services.MineRevealService;

public class MineClickedEventHandler implements IEventHandler<MineClickedEvent> {
    private GameState gameState;
    private MineRevealService mineRevealService;
    private ClockTimer clockTimer;
    private IEventSubscriber eventSubscriber;

    public MineClickedEventHandler(
        GameState state,
        MineRevealService service,
        ClockTimer timer,
        IEventSubscriber subscriber
    ) {
        gameState = state;
        mineRevealService = service;
        clockTimer = timer;
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

        leftClicked(x, y);

        eventSubscriber.notify(new UpdateMinePanelEvent());
    }

    private void leftClicked(int x, int y) {
        Mines mines = gameState.getMines();
        int index = mines.contains(x, y);
        int puzzleWidth = gameState.getCurrentPuzzleWidth();

		try {
            // These may throw a game over exception.
            if (mines.get(index).uncovered()) {
                // If already uncovered, use a special uncover.
                mineRevealService.specialUncover(index, mines, puzzleWidth);
            } else {
                mineRevealService.uncover(index, mines, puzzleWidth);
            }

            // If no exception is throw, we can update the game condition.
            if (gameState.updateGameCondition()) {
                clockTimer.stop();
                eventSubscriber.notify(new SetResetButtonIconEvent(Resource.SmileyCool));
            };
        } catch (GameOverException ex) {
            gameState.setGameOver(true);
			clockTimer.stop();
			eventSubscriber.notify(new SetResetButtonIconEvent(Resource.SmileySad));
        }
    }
}