package events;

import models.Difficulty;
import models.records.AllRecords;

public class ShowRecordsEvent {
	public AllRecords records;
	public Difficulty difficulty;

	public ShowRecordsEvent() {
	}
}