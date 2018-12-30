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
		
		var header = new TableRow();
		header.add((r, c) -> new Cell("Time", true));
		header.add((r, c) -> new Cell("Date", true));
		_table = new CustomReadonlyTable(header);
		
		add(_table);
	}

	public void setRecords(Record[] records) {
		var listOfRows = new ArrayList<TableRow>();
		for (var record : records) {
			var row = new TableRow();
			row.add((r, c) -> new Cell(String.valueOf(record.time), true));
			row.add((r, c) -> new Cell(record.date, true));
			listOfRows.add(row);
		}
		_table.setData(listOfRows.toArray(TableRow[]::new));
	}
}