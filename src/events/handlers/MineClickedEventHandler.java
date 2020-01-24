package events.handlers;

import events.IEventSubscriber;
import events.MineClickedEvent;
import events.SetResetButtonIconEvent;
import events.ShowRecordsEvent;
import events.StopClockTimerEvent;
import events.UpdateMinePanelEvent;
import exceptions.GameOverException;
import ui.ClockTimer;
import models.Resource;
import services.MineRevealService;
import services.RecordsService;
import services.StatisticsService;
import state.GameState;

public class MineClickedEventHandler implements IEventHandler<MineClickedEvent> {
    private GameState gameState;
    private MineRevealService mineRevealService;
    private RecordsService recordsService;
    private StatisticsService statisticsService;
    private ClockTimer clockTimer;
    private IEventSubscriber eventSubscriber;

    public MineClickedEventHandler(
        GameState gameState,
        MineRevealService mineRevealService,
        RecordsService recordsService,
        StatisticsService statisticsService,
        ClockTimer clockTimer,
        IEventSubscriber eventSubscriber
    ) {
        this.gameState = gameState;
        this.mineRevealService = mineRevealService;
        this.recordsService = recordsService;
        this.statisticsService = statisticsService;
        // TODO this should not require the clock timer component directly.
        this.clockTimer = clockTimer;
        this.eventSubscriber = eventSubscriber;
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
        var mines = gameState.getMines();
        var index = mines.contains(x, y);
        var puzzleWidth = gameState.getCurrentPuzzleWidth();

		try {
            // These may throw a game over exception.
            if (mines.get(index).uncovered()) {
                // If already uncovered, use a special uncover.
                mineRevealService.specialUncover(index, mines, puzzleWidth);
            } else {
                mineRevealService.uncover(index, mines, puzzleWidth);
            }

            // If no exception is thrown, we can update the game condition.
            if (gameState.updateGameCondition()) {
                eventSubscriber.notify(new StopClockTimerEvent());

                // Record a record if need be.
                var recordSet = recordsService.checkAndSaveNewRecord(
                    clockTimer.getSeconds(), gameState.getCurrentPuzzleDifficulty());

                // Add game played and won.
                statisticsService.gameWon(gameState.getCurrentPuzzleDifficulty());

                // Show the records window if a record was set.
                if (recordSet) {
                    var showRecords = new ShowRecordsEvent();
                    showRecords.records = recordsService.getAllRecords();
                    showRecords.difficulty = gameState.getCurrentPuzzleDifficulty();
                    eventSubscriber.notify(showRecords);
                }

                eventSubscriber.notify(new SetResetButtonIconEvent(
                    recordSet ? Resource.SmileyRecord : Resource.SmileyCool));
            };
        } catch (GameOverException ex) {
            eventSubscriber.notify(new StopClockTimerEvent());
			eventSubscriber.notify(new SetResetButtonIconEvent(Resource.SmileySad));
            
            gameState.setGameOver(true);
            statisticsService.gameLost(gameState.getCurrentPuzzleDifficulty());
        }
    }
}
