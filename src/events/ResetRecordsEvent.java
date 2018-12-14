package events;

import models.Difficulty;
import models.records.Record;

public class ResetRecordsEvent {
    public Difficulty difficulty;
    public Record[] records;

    public ResetRecordsEvent(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}