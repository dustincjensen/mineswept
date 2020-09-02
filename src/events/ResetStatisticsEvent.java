package events;

import models.Difficulty;
import models.records.Record;
import models.statistics.AllStats;

public class ResetStatisticsEvent {
    public AllStats stats;
    public Difficulty difficulty;
    public Record[] records;

    public ResetStatisticsEvent(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}