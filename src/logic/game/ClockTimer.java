package logic.game;

import gui.panel.header.TimeCount;
import java.awt.event.ActionListener;
import javax.swing.Timer;

// TODO investigate this implementation and make sure it is counting seconds properly...
public class ClockTimer {
	private static final int DELAY_IN_MILLISECONDS = 1000;
	private static final int MAXIMUM_SECONDS_TO_COUNT = 999;

	private int time;
	private Timer timer;

	public ClockTimer() {
		System.out.println("Creating a new clock timer.");
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
			// TODO stop referencing TimeCount component statically.
			TimeCount.setClockCount(getTime());
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

	public void start() {
		timer.start();
	}

	public void stop() {
		timer.stop();
	}

	public void reset() {
		timer.stop();
		time = 0;
		// TODO stop referencing TimeCount component statically.
		TimeCount.setClockCount(getTime());
	}
}