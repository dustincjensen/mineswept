package models.statistics;

public class AllStats {
    public LongTermStats easy;
    public LongTermStats medium;
    public LongTermStats hard;

    public AllStats() {
        easy = new LongTermStats();
        medium = new LongTermStats();
        hard = new LongTermStats();
    }
}