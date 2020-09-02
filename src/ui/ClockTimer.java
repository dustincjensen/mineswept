package ui;

import events.IEventPublisher;
import events.IEventSubscriber;
import events.ResetClockTimerEvent;
import events.SetTimeCountEvent;
import events.StartClockTimerEvent;
import events.StopClockTimerEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class ClockTimer {
	private static final int DELAY_IN_MILLISECONDS = 100;
	private static final int MAXIMUM_TICK_EVENTS = 9999;

	private IEventPublisher eventPublisher;
	private IEventSubscriber eventSubscriber;
	private Timer timer;
	private int occurrences;
	private int seconds;

	public ClockTimer(
		IEventPublisher eventPublisher, 
		IEventSubscriber eventSubscriber
	) {
		this.eventPublisher = eventPublisher;
		this.eventSubscriber = eventSubscriber;
		occurrences = 0;
		seconds = 0;
		timer = new Timer(DELAY_IN_MILLISECONDS, tickEvent());

		setupSubscriptions();
	}

	public int getSeconds() {
		return seconds;
	}

	private ActionListener tickEvent() {
		return evt -> {
			occurrences++;
			
			// Stop the timer if we are greater than the maximum ticks to count.
			if (occurrences >= MAXIMUM_TICK_EVENTS) {
				timer.stop();
			}

			var currentSeconds = seconds;
			seconds = occurrences / 10;
			
			// Only publish when the time in seconds no longer match.
			if (currentSeconds != seconds) {
				eventPublisher.publish(new SetTimeCountEvent(seconds));	
			}
		};
	}

	private void setupSubscriptions() {
		eventSubscriber.subscribe(StartClockTimerEvent.class, event -> timer.start());
		eventSubscriber.subscribe(StopClockTimerEvent.class, event -> timer.stop());
		eventSubscriber.subscribe(ResetClockTimerEvent.class, event -> {
			timer.stop();
			occurrences = 0;
			seconds = 0;
		});
	}
}