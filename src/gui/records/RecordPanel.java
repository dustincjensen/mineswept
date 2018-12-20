package gui.records;

import gui.components.table.Cell;
import gui.components.table.CustomReadonlyTable;
import gui.components.table.TableRow;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import models.records.Record;

public class RecordPanel extends JPanel {
	private CustomReadonlyTable _table;
	
	public RecordPanel() {
		setLayout(new GridLayout(0, 1));
		add(createTable());
	}

	public void setRecords(Record[] records) {
		var listOfRows = new ArrayList<TableRow>();
		for (var record : records) {
			var row = new TableRow();
			row.add((r, c) -> new Cell(String.valueOf(record.time), false));
			row.add((r, c) -> new Cell(record.name, true));
			row.add((r, c) -> new Cell(record.date, false));
			listOfRows.add(row);
		}
		_table.setData(listOfRows.toArray(TableRow[]::new));
	}

	private CustomReadonlyTable createTable() {
		var header = new TableRow();
		header.add((r, c) -> new Cell("Time", false));
		header.add((r, c) -> new Cell("Name", true));
		header.add((r, c) -> new Cell("Date", false));
		_table = new CustomReadonlyTable(header);
		return _table;
	}
}