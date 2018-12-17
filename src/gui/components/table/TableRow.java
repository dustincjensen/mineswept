package gui.components.table;

import java.util.ArrayList;

public class TableRow {
    public ArrayList<ICellHandler> cellHandlers;

    public TableRow() {
        cellHandlers = new ArrayList<ICellHandler>();
    }

    public void add(ICellHandler handler) {
        cellHandlers.add(handler);
    }
}
