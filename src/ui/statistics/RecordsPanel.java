package ui.statistics;

import ui.components.table.Cell;
import ui.components.table.CustomReadonlyTable;
import ui.components.table.TableRow;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import models.records.Record;

@SuppressWarnings("serial")
public class RecordsPanel extends JPanel {
	private CustomReadonlyTable _table;
	
	public RecordsPanel() {
		setLayout(new GridLayout(0, 1));
		setOpaque(false);
		
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