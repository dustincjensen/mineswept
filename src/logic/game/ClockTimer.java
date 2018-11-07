package logic.game;

import gui.panel.header.TimeCount;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class ClockTimer {
	private static final int DELAY_IN_MILLISECONDS = 1000;
	private static final int MAXIMUM_SECONDS_TO_COUNT = 999;

	private static int time;
	private static Timer timer;

	public static void init() {
		time = 0;
		timer = new Timer(DELAY_IN_MILLISECONDS, tickEvent());
	}

	public static ActionListener tickEvent() {
		return evt -> {
			if (time >= MAXIMUM_SECONDS_TO_COUNT) {
				stop();
			} else {
				time++;
			}
			TimeCount.setClockCount(getTime());
		};
	}

	public static String getTime() {
		if (time < 10) {
			return "00" + time;
		} else if (time < 100) {
			return "0" + time;
		} else {
			return "" + time;
		}
	}

	public static void start() {
		timer.start();
	}

	public static void stop() {
		timer.stop();
	}

	public static void reset() {
		timer.stop();
		time = 0;
		TimeCount.setClockCount(getTime());
	}
}