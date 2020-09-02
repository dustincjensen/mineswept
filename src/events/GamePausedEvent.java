package events;

/**
 * Notifies subscribers that the game is paused or not.
 * {@link PauseGameEvent} is the action.
 * {@link GamePausedEvent} is the state.
 */
public class GamePausedEvent {
	public boolean gamePaused;

	public GamePausedEvent(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}
}