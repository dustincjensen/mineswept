package gui;

import events.IEventPublisher;
import events.SetTimeCountEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

// TODO investigate this implementation and make sure it is counting seconds properly...
public class ClockTimer {
	private static final int DELAY_IN_MILLISECONDS = 1000;
	private static final int MAXIMUM_SECONDS_TO_COUNT = 999;

	private IEventPublisher eventPublisher;
	private int time;
	private Timer timer;

	public ClockTimer(IEventPublisher publisher) {
		System.out.println("Creating a new clock timer.");
		eventPublisher = publisher;
		time = 0;
		timer = new Timer(DELAY_IN_MILLISECONDS, tickEvent());
	}

	public ActionListener tickEvent() {
		return evt -> {
			if (time >= MAXIMUM_SECONDS_TO_COUNT) {
				stop();
			} else {
				time++;
			}
			eventPublisher.publish(new SetTimeCountEvent(getTime()));
		};
	}

	public String getTime() {
		if (time < 10) {
			return "00" + time;
		} else if (time < 100) {
			return "0" + time;
		} else {
			return "" + time;
		}
	}

	public int getSeconds() {
		return time;
	}

	public void start() {
		timer.start();
	}

	public void stop() {
		timer.stop();
	}

	public void reset() {
		timer.stop();
		time = 0;
		eventPublisher.publish(new SetTimeCountEvent(getTime()));
	}
}