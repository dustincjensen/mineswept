package events;

import models.Difficulty;
import models.records.AllRecords;
import models.statistics.AllStats;

public class ShowStatisticsEvent {
	public AllRecords records;
	public AllStats stats;
	public Difficulty difficulty;
}