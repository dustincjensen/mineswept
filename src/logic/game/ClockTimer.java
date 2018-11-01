package logic.game;

import gui.panel.header.TimeCount;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClockTimer {

	private static final int delay = 1000; // milliseconds
	private static int time;
	private static Timer timer;

	public static void init() {
		TimerUpdate tu = new TimerUpdate();
		time = 0;
		timer = new Timer(delay, tu);
	}

	public static String getTime() {
		String timeString = "";
		if (time < 10)
			timeString = "00";
		else if (time < 100)
			timeString = "0";
		timeString = timeString + time;
		return timeString;
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

	private static class TimerUpdate implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if (time >= 999)
				timer.stop();
			else
				time++;
			// set GUI
			TimeCount.setClockCount(getTime());
		}
	}
}