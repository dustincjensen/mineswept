package gui.records;

import gui.components.tabbedPane.CustomTabbedPane;

public class RecordTabs extends CustomTabbedPane {
    public RecordTabs(RecordPanel... panels) {
        for (RecordPanel panel : panels) {
            add(panel.getPanelTitle(), panel);
        }
    }
}