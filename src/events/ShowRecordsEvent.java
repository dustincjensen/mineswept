package events;

import models.Difficulty;
import models.records.All;

public class ShowRecordsEvent {
	public All records;
	public Difficulty difficulty;

	public ShowRecordsEvent() {
	}
}