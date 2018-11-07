package gui.events;

import gui.menu.StatisticsWindow;

public class ShowStatisticsEvent {
	public boolean show;

	public ShowStatisticsEvent(boolean showStatisticsWindow) {
		show = showStatisticsWindow;
	}
}